package de.fhk.spacequest.controlphases;

import de.fhk.spacequest.simulation.*;

import javax.vecmath.Vector2d;

/**
 * @author Robert Giacinto
 */
public class Phase6 extends ControlPhase {

    public Phase6() {
        super(6, new PhaseInfo("Einschwenken in den Mondorbit - Korrektur", "Die Rakete hat leider keine ausreichende "
                + "\nMondnähe erreicht, um in den Mondorbit einzuschwenken. "
                + "\nPhase 5 muss darum übersprungen werden. Stattdessen wird "
                + "\nversucht, wieder in eine Erdumlaufbahn zu gelangen. Ob "
                + "\ndies gelingt, hängt unter anderem von der vorhandenen "
                + "\nTreibstoffmenge ab."));
    }

    @Override
    public void control() {
        AuxVars auxVars = getRocketControl().getAuxVars();
        Simulation simulation = getRocketControl().getSimulation();
        ResultVector f = getRocketControl().getF();
        ResultVector y = getRocketControl().getY();

        if (y.getRocket().getM() > simulation.getMN()) {
            double v_orbit = Math.sqrt((Constants.G * Earth.M) / auxVars.getBrre());
            Vector2d v_soll = (Vector2d) auxVars.getEvte().clone();
            v_soll.scale(-v_orbit);
            v_soll.sub(auxVars.getVve());
            v_soll.scale(-0.005);
            f.getRocket().setEt((Vector2d) v_soll.clone());
            f.getRocket().setM(-v_soll.length() * y.getRocket().getM() / Constants.D_W);
        }
    }

    @Override
    public int checkConditions() {
        AuxVars auxVars = getRocketControl().getAuxVars();

        if (((auxVars.getErrm().dot(auxVars.getVrm()) > 0.0
                && auxVars.getBrrm() - Moon.R < 50.0 * Moon.R)
                || (auxVars.getBrrm() - Moon.R < 0.5 * Moon.R))) {
            return 5;
        } else {
            return 6;
        }
    }
}
