package de.fhk.spacequest.controlphases;

import de.fhk.spacequest.simulation.AuxVars;
import de.fhk.spacequest.simulation.Constants;
import de.fhk.spacequest.simulation.Earth;
import de.fhk.spacequest.simulation.ResultVector;

import javax.vecmath.Vector2d;

/**
 * @author Robert Giacinto
 */
public class Phase2 extends ControlPhase {

    public Phase2() {
        super(2, new PhaseInfo("Parken im Erdorbit", "Die Rakete befindet sich jetzt "
                + "\nim Erdorbit. Bei größeren Abweichungen von "
                + "\nder Orbithöhe oder der Orbitgeschwindigkeit "
                + "\nwird nachgesteuert. Ansonsten befindet "
                + "\nsich die Rakete im antriebslosen Freiflug."));
    }

    @Override
    public void control() {
        ResultVector f = getRocketControl().getF();
        ResultVector y = getRocketControl().getY();
        AuxVars auxVars = getRocketControl().getAuxVars();

        double v_orbit = Math.sqrt((Constants.G * Earth.M) / auxVars.getBrre());
        Vector2d v_soll = (Vector2d) auxVars.getEvte().clone();
        v_soll.scale(v_orbit);
        v_soll.sub(auxVars.getVve());
        v_soll.scale(-0.05);
        f.getRocket().setEt(v_soll);
        f.getRocket().setM(-v_soll.length() * (y.getRocket().getM() / Constants.D_W));
    }

    @Override
    public int checkConditions() {
        ResultVector y = getRocketControl().getY();
        if (y.getT() > 2500.0) {
            return 3;
        }
        return 2;
    }
}
