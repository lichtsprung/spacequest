package de.fhk.spacequest.controlphases;

import de.fhk.spacequest.simulation.AuxVars;
import de.fhk.spacequest.simulation.Constants;
import de.fhk.spacequest.simulation.Moon;
import de.fhk.spacequest.simulation.ResultVector;
import de.fhk.spacequest.simulation.Simulation;
import javax.vecmath.Vector2d;

/**
 *
 * @author Robert Giacinto
 */
public class Phase5 extends ControlPhase {

    public Phase5() {
        super(5, new PhaseInfo("Einschwenken in den Mondorbit", "Die Rakete hat nun die Mondnähe erreicht, "
                + "\nsodass ein Einschwenken in den Mondorbit möglich "
                + "\nist."));
    }

    @Override
    public void control() {
        ResultVector y = getRocketControl().getY();
        ResultVector f = getRocketControl().getF();
        Simulation simulation = getRocketControl().getSimulation();
        AuxVars auxVars = getRocketControl().getAuxVars();

        if (y.getRocket().getM() > simulation.getMN()) {
            double v_orbit = Math.sqrt((Constants.G * Moon.M) / auxVars.getBrrm());
            Vector2d v_soll = (Vector2d) auxVars.getVtm().clone();
            v_soll.scale(v_orbit / (1.0 + auxVars.getVtm().length()));
            v_soll.sub(auxVars.getVvm());
            v_soll.scale(-0.005);
            f.getRocket().setEt((Vector2d) v_soll.clone());
            f.getRocket().setM(-v_soll.length() * y.getRocket().getM() / Constants.D_W);
        }
    }

    @Override
    public int checkConditions() {
        return 5;
    }
}
