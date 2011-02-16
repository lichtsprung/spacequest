package de.fhk.spacequest.simulation;

import de.fhk.spacequest.controlphases.ControlPhase;
import de.fhk.spacequest.controlphases.Phase0;
import de.fhk.spacequest.controlphases.Phase1;
import de.fhk.spacequest.controlphases.Phase2;
import de.fhk.spacequest.controlphases.Phase3;
import de.fhk.spacequest.controlphases.Phase4;
import de.fhk.spacequest.controlphases.Phase5;
import de.fhk.spacequest.controlphases.Phase6;
import java.util.Collection;
import javax.vecmath.Vector2d;

/**
 * Diese Klasse übernimmt die Berechnungen der Mondlandesimulation.
 *
 * @author Robert Giacinto
 * @author Hartmut Westenberger
 * @author Beate Breiderhoff
 */
public class Simulation {

    /**
     * Die Hilfsvariablen.
     */
    private AuxVars auxVars;
    /**
     * Die Ergebnisvektoren, die während der Berechnungen verwendet werden.
     */
    private ResultVector yn, fn, currentState;
    /**
     * Das aktuelle Delta t
     */
    private DT dt;
    /**
     * Der maximale Zeitschritt.
     */
    private double dtmax;
    /**
     * <code>true</code> wenn die Rakete manuell gesteuert werden soll.
     */
    private boolean manualControl;
    /**
     * <code>true</code> wenn Simulation in eine neue Phase gewechselt ist.
     */
    private boolean phaseChanged;
    /**
     * <code>true</code> wenn Simulation pausiert.
     */
    private boolean paused;
    /**
     * Die aktuelle Phase des Mondflugs.
     */
    private int phase;
    /**
     * Die Nutzlast der Rakete.
     */
    private double mn;
    /**
     * Die Masse des Treibstoffs.
     */
    private double mt;
    /**
     * <code>true</code> wenn manuelle Phasenkontrolle aktiv.
     */
    private boolean manualStepControl;
    /**
     * Die Raktenkontrolleinheit der Rakete.
     */
    private RocketControl rocketControl;

    /**
     * Erstellt eine neue Simulation.
     *
     * @param mn die Nutzlast der Rakete
     * @param mt die Treibstoffmenge der Rakete
     * @param manualControl <code>true</code> wenn man Rakete manuell steuern möchte
     * @param manualStepControl <code>true</code> wenn man die Steuerphasen manuell wechseln möchte
     */
    public Simulation(double mn, double mt, boolean manualControl, boolean manualStepControl) {
        this.mt = mt;
        this.mn = mn;
        this.manualControl = manualControl;
        this.manualStepControl = manualStepControl;

        initSimulation();
    }

    /**
     * Initialisiert die Simulation.
     */
    private void initSimulation() {
        dt = new DT();
        dt.setDtn(Constants.DT_START);
        dtmax = Constants.DT_MAX;

        Earth earth = new Earth();
        earth.setR(calcOrbitPosition(-Constants.LE, Constants.PHI10V, 0, Constants.PHI10));

        Moon moon = new Moon();
        moon.setR(calcOrbitPosition(Constants.LM, Constants.PHI10V, 0, Constants.PHI10));

        Rocket rocket = new Rocket();
        Vector2d rs = calcOrbitPosition(Earth.R, Constants.PHI20V, 0, Constants.PHI20);
        rocket.setR((Vector2d) earth.getR().clone());
        rocket.getR().add(rs);
        rocket.setV(new Vector2d(Constants.LE * Constants.PHI10V * Math.sin(Constants.PHI10) - Earth.R * Constants.PHI20V * Math.sin(Constants.PHI20), -Constants.LE * Constants.PHI10V * Math.cos(Constants.PHI10) + Earth.R * Constants.PHI20V * Math.cos(Constants.PHI20)));
        rocket.setEt(new Vector2d());
        rocket.setMp(0);
        rocket.setM(mn + mt);
        rocket.setMt(mt);
        rocket.setMn(mn);

        auxVars = new AuxVars(rocket, 0);
        yn = new ResultVector(rocket, earth, moon, 0);

        rocketControl = new RocketControl(this);
        this.addControlPhase(new Phase0());
        this.addControlPhase(new Phase1());
        this.addControlPhase(new Phase2());
        this.addControlPhase(new Phase3());
        this.addControlPhase(new Phase4());
        this.addControlPhase(new Phase5());
        this.addControlPhase(new Phase6());



        fn = new ResultVector(new Rocket(), new Earth(), new Moon(), 0);
    }

    /**
     * Berechhnet die Gravitation, die auf die Rakete wirkt.
     *
     * @param yn der aktuelle Zustand der Simulation
     * @param auxVars die Hilfsvariablen, die für die Rechnung benutzt werden sollen
     * @return die Wirkung der Gravitation auf die Rakete
     */
    protected Vector2d calcGravitation(ResultVector yn, AuxVars auxVars) {
        Vector2d rn_re = (Vector2d) yn.getRocket().getR().clone();
        rn_re.sub(auxVars.getRe());

        Vector2d rn_rm = (Vector2d) yn.getRocket().getR().clone();
        rn_rm.sub(auxVars.getRm());

        double l_rn_re = rn_re.length();
        double l_rn_rm = rn_rm.length();

        double gFactor_e = -yn.getRocket().getM() * Constants.G * Earth.M / (l_rn_re * l_rn_re * l_rn_re);
        double gFactor_m = -yn.getRocket().getM() * Constants.G * Moon.M / (l_rn_rm * l_rn_rm * l_rn_rm);

        rn_re.scale(gFactor_e);
        rn_rm.scale(gFactor_m);
        rn_re.add(rn_rm);
        rn_re.scale(1 / yn.getRocket().getM());

        return rn_re;
    }

    /**
     * Gibt die Nutzlast der Rakete zurück.
     * @return die Nutzlast der Rakete
     * @deprecated Ist ein Wert der Rakete. Sollte von dort auch geholt werden.
     */
    public double getMN() {
        return mn;
    }

    /**
     * Implementierung der Runge-Kutta-Integration zweiter und dritter Ordnung.
     *
     * @param y0 Ergebnisvektor zum Zeitpunkt n
     * @param st0
     * @param dt die aktuelle Zeitschrittgröße
     * @return das Ergebnis der Integration zweiter und dritter Ordnung
     */
    private RKFResult rkf23(ResultVector y0, ResultVector st0, DT dt) {
        Vector2d phi2_r = new Vector2d(),
                phi2_v = new Vector2d(),
                phi3_r = new Vector2d(),
                phi3_v = new Vector2d();

        double phi2_m, phi3_m;

        ResultVector st1 = new ResultVector(new Rocket(), new Earth(), new Moon(), y0.getT()),
                st2 = new ResultVector(new Rocket(), new Earth(), new Moon(), y0.getT()),
                st3 = new ResultVector(new Rocket(), new Earth(), new Moon(), y0.getT()),
                y1 = new ResultVector(new Rocket(), new Earth(), new Moon(), y0.getT()),
                y2 = new ResultVector(new Rocket(), new Earth(), new Moon(), y0.getT()),
                y3 = new ResultVector(new Rocket(), new Earth(), new Moon(), y0.getT());

        rocketControl.controlRocket(phase, y0, st0);

        y1.getRocket().getR().x = y0.getRocket().getR().x + dt.getDtn() * (Constants.ALPHA1 * st0.getRocket().getR().x);
        y1.getRocket().getR().y = y0.getRocket().getR().y + dt.getDtn() * (Constants.ALPHA1 * st0.getRocket().getR().y);

        y1.getRocket().getV().x = y0.getRocket().getV().x + dt.getDtn() * (Constants.BETA10 * st0.getRocket().getV().x);
        y1.getRocket().getV().y = y0.getRocket().getV().y + dt.getDtn() * (Constants.BETA10 * st0.getRocket().getV().y);

        y1.getRocket().setEt((Vector2d) st0.getRocket().getEt().clone());

        y1.getRocket().setM(y0.getRocket().getM() + dt.getDtn() * (Constants.ALPHA1 * st0.getRocket().getM()));

        y1.setT(y0.getT() + dt.getDtn() * Constants.ALPHA1);

        y1.getRocket().setMp(st0.getRocket().getM());

        y1.getEarth().setR(calcOrbitPosition(-Constants.LE, Constants.PHI10V, y1.getT(), Constants.PHI10));
        y1.getMoon().setR(calcOrbitPosition(Constants.LM, Constants.PHI10V, y1.getT(), Constants.PHI10));

        rocketControl.controlRocket(phase, y1, st1);

        y2.getRocket().getR().x = y0.getRocket().getR().x + dt.getDtn() * (Constants.BETA20 * st0.getRocket().getR().x + Constants.BETA21 * st1.getRocket().getR().x);
        y2.getRocket().getR().y = y0.getRocket().getR().y + dt.getDtn() * (Constants.BETA20 * st0.getRocket().getR().y + Constants.BETA21 * st1.getRocket().getR().y);

        y2.getRocket().getV().x = y0.getRocket().getV().x + dt.getDtn() * (Constants.BETA20 * st0.getRocket().getV().x + Constants.BETA21 * st1.getRocket().getV().x);
        y2.getRocket().getV().y = y0.getRocket().getV().y + dt.getDtn() * (Constants.BETA20 * st0.getRocket().getV().y + Constants.BETA21 * st1.getRocket().getV().y);

        y2.getRocket().setEt((Vector2d) st1.getRocket().getEt().clone());
        y2.getRocket().setM(y0.getRocket().getM() + dt.getDtn() * (Constants.BETA20 * st0.getRocket().getM() + Constants.BETA21 * st1.getRocket().getM()));

        y2.setT(y0.getT() + dt.getDtn() * Constants.ALPHA2);
        y2.getRocket().setMp(st1.getRocket().getM());


        y2.getEarth().setR(calcOrbitPosition(-Constants.LE, Constants.PHI10V, y2.getT(), Constants.PHI10));
        y2.getMoon().setR(calcOrbitPosition(Constants.LM, Constants.PHI10V, y2.getT(), Constants.PHI10));

        rocketControl.controlRocket(phase, y2, st2);

        y3.getRocket().getR().x = y0.getRocket().getR().x + dt.getDtn() * (Constants.BETA30 * st0.getRocket().getR().x + Constants.BETA31 * st1.getRocket().getR().x + Constants.BETA32 * st2.getRocket().getR().x);
        y3.getRocket().getR().y = y0.getRocket().getR().y + dt.getDtn() * (Constants.BETA30 * st0.getRocket().getR().y + Constants.BETA31 * st1.getRocket().getR().y + Constants.BETA32 * st2.getRocket().getR().y);

        y3.getRocket().getV().x = y0.getRocket().getV().x + dt.getDtn() * (Constants.BETA30 * st0.getRocket().getV().x + Constants.BETA31 * st1.getRocket().getV().x + Constants.BETA32 * st2.getRocket().getV().x);
        y3.getRocket().getV().y = y0.getRocket().getV().y + dt.getDtn() * (Constants.BETA30 * st0.getRocket().getV().y + Constants.BETA31 * st1.getRocket().getV().y + Constants.BETA32 * st2.getRocket().getV().y);

        y3.getRocket().setEt((Vector2d) st2.getRocket().getEt().clone());

        y3.getRocket().setM(y0.getRocket().getM() + dt.getDtn() * (Constants.BETA30 * st0.getRocket().getM() + Constants.BETA31 * st1.getRocket().getM() + Constants.BETA32 * st2.getRocket().getM()));
        y3.setT(y0.getT() + dt.getDtn());
        y3.getRocket().setMp(st2.getRocket().getM());

        y3.getEarth().setR(calcOrbitPosition(-Constants.LE, Constants.PHI10V, y3.getT(), Constants.PHI10));
        y3.getMoon().setR(calcOrbitPosition(Constants.LM, Constants.PHI10V, y3.getT(), Constants.PHI10));

        rocketControl.controlRocket(phase, y3, st3);


        phi2_r.x = Constants.C0 * st0.getRocket().getR().x + Constants.C1 * st1.getRocket().getR().x + Constants.C2 * st2.getRocket().getR().x;
        phi2_r.y = Constants.C0 * st0.getRocket().getR().y + Constants.C1 * st1.getRocket().getR().y + Constants.C2 * st2.getRocket().getR().y;

        phi2_v.x = Constants.C0 * st0.getRocket().getV().x + Constants.C1 * st1.getRocket().getV().x + Constants.C2 * st2.getRocket().getV().x;
        phi2_v.y = Constants.C0 * st0.getRocket().getV().y + Constants.C1 * st1.getRocket().getV().y + Constants.C2 * st2.getRocket().getV().y;

        phi2_m = Constants.C0 * st0.getRocket().getM() + Constants.C1 * st1.getRocket().getM() + Constants.C2 * st2.getRocket().getM();


        phi3_r.x = Constants.D0 * st0.getRocket().getR().x + Constants.D2 * st2.getRocket().getR().x + Constants.D3 * st3.getRocket().getR().x;
        phi3_r.y = Constants.D0 * st0.getRocket().getR().y + Constants.D2 * st2.getRocket().getR().y + Constants.D3 * st3.getRocket().getR().y;

        phi3_v.x = Constants.D0 * st0.getRocket().getV().x + Constants.D2 * st2.getRocket().getV().x + Constants.D3 * st3.getRocket().getV().x;
        phi3_v.y = Constants.D0 * st0.getRocket().getV().y + Constants.D2 * st2.getRocket().getV().y + Constants.D3 * st3.getRocket().getV().y;

        phi3_m = Constants.D0 * st0.getRocket().getM() + Constants.D2 * st2.getRocket().getM() + Constants.D3 * st3.getRocket().getM();




        ResultVector yneu2 = new ResultVector(new Rocket(), new Earth(), new Moon(), y0.getT() + dt.getDtn());
        yneu2.getRocket().getR().x = y0.getRocket().getR().x + dt.getDtn() * phi2_r.x;
        yneu2.getRocket().getR().y = y0.getRocket().getR().y + dt.getDtn() * phi2_r.y;

        yneu2.getRocket().getV().x = y0.getRocket().getV().x + dt.getDtn() * phi2_v.x;
        yneu2.getRocket().getV().y = y0.getRocket().getV().y + dt.getDtn() * phi2_v.y;


        yneu2.getRocket().setEt((Vector2d) st2.getRocket().getEt().clone());

        yneu2.getRocket().setM(y0.getRocket().getM() + dt.getDtn() * phi2_m);
        yneu2.getRocket().setMp(st2.getRocket().getM());

        yneu2.getEarth().setR(calcOrbitPosition(-Constants.LE, Constants.PHI10V, yneu2.getT(), Constants.PHI10));
        yneu2.getMoon().setR(calcOrbitPosition(Constants.LM, Constants.PHI10V, yneu2.getT(), Constants.PHI10));

        ResultVector yneu3 = new ResultVector(new Rocket(), new Earth(), new Moon(), y0.getT() + dt.getDtn());
        yneu3.getRocket().getR().x = y0.getRocket().getR().x + dt.getDtn() * phi3_r.x;
        yneu3.getRocket().getR().y = y0.getRocket().getR().y + dt.getDtn() * phi3_r.y;

        yneu3.getRocket().getV().x = y0.getRocket().getV().x + dt.getDtn() * phi3_v.x;
        yneu3.getRocket().getV().y = y0.getRocket().getV().y + dt.getDtn() * phi3_v.y;

        yneu3.getRocket().setEt((Vector2d) st2.getRocket().getEt().clone());

        yneu3.getRocket().setM(y0.getRocket().getM() + dt.getDtn() * phi3_m);
        yneu3.getRocket().setMp(st3.getRocket().getM());

        yneu3.getEarth().setR(calcOrbitPosition(-Constants.LE, Constants.PHI10V, yneu3.getT(), Constants.PHI10));
        yneu3.getMoon().setR(calcOrbitPosition(Constants.LM, Constants.PHI10V, yneu3.getT(), Constants.PHI10));



        return new RKFResult(yneu2, yneu3);
    }

    /**
     * Anpassung des Zeitschritts.
     *
     * @param y aktueller Zustand der Simulation
     * @param dt aktuelle Zeitschrittgröße
     * @param f
     * @return <code>true</code> wenn Iteration zu neuem Ergebnis geführt hat
     */
    private boolean iterate(ResultVector y, DT dt, ResultVector f) {

        double r_weite, v_weite;

        double min_weite;

        RKFResult rkfResult = rkf23(y, f, dt);



        if (rkfResult.getY2().getRocket().getR().length() == rkfResult.getY3().getRocket().getR().length()) {
            r_weite = dtmax;
        } else {
            Vector2d diff = (Vector2d) rkfResult.getY2().getRocket().getR().clone();
            diff.sub(rkfResult.getY3().getRocket().getR());

            if (rkfResult.getY2().getRocket().getR().length() > rkfResult.getY3().getRocket().getR().length()) {
                r_weite = Math.sqrt((rkfResult.getY2().getRocket().getR().length() / diff.length()) * Constants.D_EPS) * dt.getDtn() * 0.9;
            } else {
                r_weite = Math.sqrt((rkfResult.getY3().getRocket().getR().length() / diff.length()) * Constants.D_EPS) * dt.getDtn() * 0.9;
            }
        }

        if (rkfResult.getY2().getRocket().getV().length() == rkfResult.getY3().getRocket().getV().length()) {
            v_weite = dtmax;
        } else {
            Vector2d diff = (Vector2d) rkfResult.getY2().getRocket().getV().clone();
            diff.sub(rkfResult.getY3().getRocket().getV());

            if (rkfResult.getY2().getRocket().getV().length() > rkfResult.getY3().getRocket().getV().length()) {
                v_weite = Math.sqrt((rkfResult.getY2().getRocket().getV().length() / diff.length()) * Constants.D_EPS) * dt.getDtn() * 0.9;
            } else {
                v_weite = Math.sqrt((rkfResult.getY3().getRocket().getV().length() / diff.length()) * Constants.D_EPS) * dt.getDtn() * 0.9;
            }
        }


        // kleinste Weite suchen
        if (r_weite < v_weite) {
            min_weite = r_weite;
        } else {
            min_weite = v_weite;
        }


        // Fehlerüberprüfung
        if (min_weite > dt.getDtn()) {
            yn = rkfResult.getY3();

            if (min_weite > 1.02 * dt.getDtn()) {
                dt.setDtn(1.02 * dt.getDtn());
            } else {
                dt.setDtn(0.9 * min_weite);
            }


            if (dt.getDtn() > dtmax) {
                dt.setDtn(dtmax);
            }
            return true;
        } else {
            dt.setDtn(0.9 * min_weite);
            return false;
        }

    }

    /**
     * Gibt den aktuellen Zustande der Simulation zurück.
     *
     * @return der aktuelle Zustand der Simulation
     */
    public ResultVector getCurrentSimulationState() {
        yn.getRocket().setEtv((Vector2d) currentState.getRocket().getEtv().clone());
        return (ResultVector) yn.clone();
    }

    /**
     * Gibt die Hilfsvariablen zurück.
     *
     * @return die Hilfsvariablen, die von der Simulation benutzt werden
     */
    public AuxVars getAuxVars() {
        return auxVars;
    }

    /**
     * Erhöht die Maximalgeschwindigkeit der Simulation.
     */
    public void faster() {
        dtmax = 1.02 * dtmax;
    }

    /**
     * Verringert die Maximalgeschwindigkeit der Simulation.
     */
    public void slower() {
        dtmax = 0.98 * dtmax;
    }

    /**
     * Pausiert die Simulation.
     */
    public void togglePause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    /**
     * (De)aktiviert die manuelle Kontrolle über die Phasensteuerung der Rakete.
     */
    public void toggleStepControl() {
        manualStepControl = !manualStepControl;
    }

    /**
     * Lässt die Raketensteuerung in die nächste Phase wechseln, wenn die manuelle Schrittkontrolle aktiviert ist.
     */
    public void nextStep() {
        if (manualStepControl) {
            if (phase < 6) {
                phase++;
                System.out.println("Phase: " + phase);
            } else {
                System.out.println("Letzte Phase erreicht.");
            }
        }
    }

    /**
     * Lässt die Raketensteuerung in die vorherige Phase wechseln, wenn die manuelle Schrittkontrolle aktiviert ist.
     */
    public void previousStep() {
        if (manualStepControl) {
            if (phase > 0) {
                phase--;
                System.out.println("Phase: " + phase);
            } else {
                System.out.println("Erste Phase erreicht.");
            }
        }
    }

    /**
     * Aktualisiert den Zustand der Simulation.
     */
    public void updateSimulationState() {
        if (!paused) {
            while (!iterate(yn, dt, fn)) {
            }

            /*
             * Kollisionserkennung
             */
            if (yn.getT() > 10 && (auxVars.getBrre() < Earth.R)) {
                System.out.println("Kollision mit Erde bei t = " + yn.getT());
                System.exit(0);
            }

            if (yn.getT() > 10 && (auxVars.getBrrm() < Moon.R)) {
                System.out.println("Kollision mit Mond bei t = " + yn.getT());
                System.exit(0);
            }

            int tmp = rocketControl.check(phase);
            if (phase - tmp != 0) {
                phaseChanged = true;
                phase = tmp;
            }

        }
    }

    /**
     * Gibt die aktuelle Phase der Raketensteuerung zurück.
     *
     * @return die aktuelle Phase der Raketensteuerung
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Gibt die aktuelle Zeitschrittgröße zurück.
     *
     * @return die aktuelle Zeitschrittgröße
     */
    public double getDT() {
        return dt.getDtn();
    }

    /**
     * Gibt die aktuellen Werte der Rakete zurück.
     *
     * @return die aktuelle Raketeninstanz
     */
    public Rocket getRocket() {
        return yn.getRocket();
    }

    public boolean isPhaseChanged() {
        if (phaseChanged) {
            phaseChanged = false;
            return true;
        }
        return false;
    }

    /**
     * Animiert den automatischen Flug der Rakete zum Mond.
     */
    private void animation() {
        auxVars.updateAuxVars(yn.getRocket(), yn.getT());


        if (!manualStepControl) {
            if (phase == 0) {
                phase = 1;
                phaseChanged = true;
            }

            while (!iterate(yn, dt, fn)) {
            }



            if (auxVars.getBrre() > .99 * Constants.D_R_ORBIT && phase == 1) {
                phase = 2;
                phaseChanged = true;
            }

            /* PHASE 3*/
            /* Weg zum Mond aufnehmen, wenn nach 10h der Winkel zwischen
            dem Vektor Erde - Mond und dem Vektor Rakete - Erde groesser als x PI wird*/

            if (yn.getT() > 2500 && phase == 2) {
                phase = 3;
                phaseChanged = true;
            }


            /* PHASE 4*/
            /* Wenn noch x t Treibstoff uebrig sind: Beschleunigung wegnehmen,
            Einleiten der Phase 4 */
            Vector2d temp = (Vector2d) yn.getMoon().getR().clone();
            temp.scale(-1);
            double tmp = calcGravityPotential(temp, yn.getEarth().getR(), yn.getMoon().getR()) - calcGravityPotential(yn.getRocket().getR(), yn.getEarth().getR(), yn.getMoon().getR());

            if (yn.getRocket().getV().length() * yn.getRocket().getV().length() / 2 > 0.999 * tmp && phase == 3) {
                phase = 4;
                phaseChanged = true;
            }

            /* PHASE 5*/
            /* Feststellen der kleinsten Entfernung zum Mond - Einleiten der Phase 5 */

            if (((auxVars.getErrm().dot(auxVars.getVrm()) > 0
                    && auxVars.getBrrm() - Moon.R < 50 * Moon.R)
                    || (auxVars.getBrrm() - Moon.R < 0.5 * Moon.R))
                    && (phase == 4 || phase == 6)) {
                phase = 5;
                phaseChanged = true;
            }
            /*  PHASE 6*/
            /* Falls der Mond verpasst wird, soll in Mondorbit eingeschwenkt werden */
            if ((auxVars.getBrre() > Constants.EARTH_ORBIT * Constants.D) && (phase == 4)) {
                phase = 6;
                phaseChanged = true;
            }


        } else {
            while (!iterate(yn, dt, fn)) {
            }
        }

    }

    /**
     * Berechnet das Gravitationpotenzial, das von Erde und Mond auf die Rakete wirkt.
     *
     * @param r die Position der Rakete
     * @param re die Position der Erde
     * @param rm die Position des Monds
     * @return das Gravitationspotenzial auf die Rakete
     */
    public double calcGravityPotential(Vector2d r, Vector2d re, Vector2d rm) {

        Vector2d r_re = (Vector2d) r.clone();
        r_re.sub(re);

        Vector2d r_rm = (Vector2d) r.clone();
        r_rm.sub(rm);

        return (-Constants.G * (Earth.M / r_re.length() + Moon.M / r_rm.length()));
    }

    /**
     * Berechnet die aktuelle Position eines Himmelskörpers.
     *
     * @param l Entfernung zum Erde-Mond-Schwerpunkt
     * @param phis Winkelgeschwindigkeit
     * @param t Zeitpunkt
     * @param phi0 Startwinkel
     * @return neue Position des Himmelskörpers
     */
    protected static Vector2d calcOrbitPosition(double l, double phis, double t, double phi0) {
        return new Vector2d(
                l * Math.cos(phis * t + phi0),
                l * Math.sin(phis * t + phi0));
    }

    public void addControlPhase(ControlPhase newPhase) {
        newPhase.setRocketControl(rocketControl);
        rocketControl.addPhase(newPhase);
    }

    public ControlPhase getCurrentControlPhase() {
        return rocketControl.getPhase(phase);
    }

    protected void setCurentSimulationState(ResultVector newState) {
        currentState = newState;
    }

    public Collection<ControlPhase> getControlPhases() {
        return rocketControl.getControlPhases();
    }
}
