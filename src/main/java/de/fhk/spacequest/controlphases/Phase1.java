package de.fhk.spacequest.controlphases;

import de.fhk.spacequest.simulation.AuxVars;
import de.fhk.spacequest.simulation.Constants;
import de.fhk.spacequest.simulation.Earth;
import de.fhk.spacequest.simulation.ResultVector;

import javax.vecmath.Vector2d;

/**
 * Kontrollklasse der Flugphase 1.
 *
 * @author Robert Giacinto
 */
public class Phase1 extends ControlPhase {

    public Phase1() {
        super(1, new PhaseInfo("Start", "Die Rakete wird zunächst senkrecht gestartet "
                + "\nund nähert sich während der Annäherung der "
                + "\ngeplanten Orbithöhe der entsprechenden "
                + "\nOrbitgeschwindigkeit an."));
    }

    @Override
    public void control() {
        ResultVector f = getRocketControl().getF();
        ResultVector y = getRocketControl().getY();
        AuxVars auxVars = getRocketControl().getAuxVars();

        double t1 = Constants.G * Earth.M;
        double v_orbit = Math.sqrt(t1 / Constants.D_R_ORBIT);


        double xn = 0.02 + 2.0 / Constants.D_W * 9.81 * y.getRocket().getM()
                * heavy((v_orbit - auxVars.getVve().length()) / v_orbit)
                * ((v_orbit - auxVars.getVve().length()) / v_orbit)
                * (StrictMath.pow((1.0 + Constants.D_R_ORBIT - auxVars.getBrre()) / (Constants.D_R_ORBIT - Earth.R), 1.0))
                * heavy(Constants.D_R_ORBIT - auxVars.getBrre());

        double xt = 5.0 / Constants.D_W * 9.81 * y.getRocket().getM() * (v_orbit - auxVars.getVte().length()) / v_orbit
                * (1.0 - ((Constants.D_R_ORBIT - auxVars.getBrre()) / (Constants.D_R_ORBIT - Earth.R)))
                * heavy(Constants.D_R_ORBIT - auxVars.getBrre());

        Vector2d tmp1 = (Vector2d) auxVars.getErre().clone();
        tmp1.scale(-xn);

        Vector2d tmp2 = (Vector2d) auxVars.getVte().clone();
        tmp2.scale(-xt / (1.0 + auxVars.getVte().length()));
        f.getRocket().setEt(tmp1);
        f.getRocket().getEt().add(tmp2);
        f.getRocket().setM(-f.getRocket().getEt().length());
    }

    @Override
    public int checkConditions() {
        AuxVars auxVars = getRocketControl().getAuxVars();
        if (auxVars.getBrre() > 0.99 * Constants.D_R_ORBIT) {
            return 2;
        }
        return 1;
    }
}
