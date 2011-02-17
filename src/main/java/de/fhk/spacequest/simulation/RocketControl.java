package de.fhk.spacequest.simulation;

import de.fhk.spacequest.controlphases.ControlPhase;

import javax.vecmath.Vector2d;
import java.util.Collection;
import java.util.HashMap;

/**
 * Die Klasse RocketControl übernimmt die automatische Steuerung der Rakete. Sie
 * wechselt je nach Situation zwischen den Kontrollphasen, um so die Rakete sicher
 * zum Mond zu bringen.
 * Hierfür müssen fünf Phasen vorhanden sein:
 * <ol>
 * <li> Senkrechte Startphase </li>
 * <li> Orbitale Flugphase </li>
 * <li> Start des Mondfluges </li>
 * <li> Einschwenken in Mondorbit </li>
 * <li> Korrektive Mondannäherung </li>
 * </ol>
 *
 * @author Robert Giacinto
 */
public class RocketControl {

    /**
     * Ergebnisvektor der Simulation
     */
    private ResultVector y = null, f = null;
    /**
     * Die Hilfsvariablen, die von der Simulation zur Verfügung gestellt werden
     */
    private AuxVars auxVars;
    /**
     * Referenz auf die Simulation selbst
     */
    private Simulation simulation;
    /**
     * Die aktiven Kontrollphasen der Raketensteuerung
     */
    private HashMap<Integer, ControlPhase> controlPhases;

    /**
     * Erstellt eine neue Kontrollsteuerung mit den übergebenen Phasen.
     *
     * @param simulation Referenz auf die laufende Simulation
     */
    public RocketControl(Simulation simulation) {
        this.auxVars = simulation.getAuxVars();
        this.simulation = simulation;
        controlPhases = new HashMap<Integer, ControlPhase>(10);
    }

    /**
     * Fügt eine neue Phase der Raketensteuerung hinzu.
     *
     * @param phase die neue Phase, die hinzugefügt werden soll
     */
    public void addPhase(ControlPhase phase) {
        controlPhases.put(phase.getPhase(), phase);
    }

    /**
     * Gibt die Instanz einer Kontrollphase zurück.
     *
     * @param phase die Nummer der Phase
     * @return die Kontrollphase der Rakete
     */
    public ControlPhase getPhase(int phase) {
        return controlPhases.get(phase);
    }


    public Collection<ControlPhase> getControlPhases() {
        return controlPhases.values();
    }

    /**
     * Wird von der Simulation verwendet, um die Raketensteuerung zu aktivieren.
     *
     * @param phase die Phase, in der sich die Simulation aktuell befindet
     * @param yn    der Ergebnisvektor der Simulation
     * @param fn    die Rechenergebnisse der Raketensteuerung werden in diesem Vektor gespeichert
     */
    public void controlRocket(int phase, ResultVector yn, ResultVector fn) {
        y = yn;
        f = fn;
        auxVars.updateAuxVars(y.getRocket(), y.getT());

        f.getRocket().setM(0.0);
        f.getRocket().setEt((Vector2d) auxVars.getErre().clone());
        f.getRocket().getEt().scale(-1.0);

        if (y.getRocket().getM() <= simulation.getMN()) {
            f.getRocket().setM(0.0);
        } else {
            controlPhases.get(phase).control();
        }
        y.getRocket().setEtv((Vector2d) f.getRocket().getEt().clone());
        f.getRocket().setEtv((Vector2d) f.getRocket().getEt().clone());
        f.getRocket().getEt().normalize();


        Vector2d propulsion = (Vector2d) f.getRocket().getEt().clone();
        propulsion.scale(Constants.D_W * (f.getRocket().getM() / y.getRocket().getM()));

        Vector2d gravitation = simulation.calcGravitation(y, auxVars);

        Vector2d acceleration = (Vector2d) propulsion.clone();
        acceleration.add(gravitation);


        f.getRocket().setV(acceleration);
        f.getRocket().setR(y.getRocket().getV());
        simulation.setCurentSimulationState(f);
    }

    /**
     * Wird von der Simulation aufgerufen, um zu überprüfen, welche Phase aktuell
     * von der Raketenkontrolle durchgeführt wird.
     *
     * @param phase die Phase, in der sich die Simulation befindet
     * @return die Phase für den nächsten Rechenschritt
     */
    public int check(int phase) {
        return controlPhases.get(phase).checkConditions();
    }

    /**
     * Gibt die Hilfsvariablen der Simulation zurück.
     *
     * @return die Hilfsvariablen der Simulation
     */
    public AuxVars getAuxVars() {
        return auxVars;
    }

    /**
     * Gibt den Vektor für die Rechenergebnisse zurück.
     *
     * @return die Rechenergebnisse der Raketensteuerung
     */
    public ResultVector getF() {
        return f;
    }

    /**
     * Gibt die aktuelle Instanz der laufenden Simulation zurück.
     *
     * @return die laufende Simulation
     */
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     * Gibt den Ergebnisvektor der Simulation zurück.
     *
     * @return der Ergebnisvektor der Simulation
     */
    public ResultVector getY() {
        return y;
    }
}
