package de.fhk.spacequest.controlphases;

import de.fhk.spacequest.simulation.AuxVars;
import de.fhk.spacequest.simulation.Constants;
import de.fhk.spacequest.simulation.ResultVector;
import de.fhk.spacequest.simulation.Simulation;

import javax.vecmath.Vector2d;

/**
 * @author Robert Giacinto
 */
public class Phase3 extends ControlPhase {

    public Phase3() {
        super(3, new PhaseInfo("Verlassen des Erdorbits", "Die Rakete verlässt jetzt den "
                + "\nErdorbit und macht sich auf die "
                + "\nReise zum Mond. Mit einem dosierten"
                + "\nSchub in Flugrichtung schleudert"
                + "\nsie sich aus dem Erdobit heraus."));
    }

    @Override
    public void control() {
        ResultVector f = getRocketControl().getF();
        ResultVector y = getRocketControl().getY();

        Vector2d tmp = (Vector2d) y.getRocket().getV().clone();
        tmp.scale(-1.0);
        f.getRocket().setEt(tmp);
        f.getRocket().setM(-1.9 * y.getRocket().getM() / Constants.D_W);
    }

    @Override
    public int checkConditions() {
        ResultVector y = getRocketControl().getY();
        Simulation simulation = getRocketControl().getSimulation();
        AuxVars auxVars = getRocketControl().getAuxVars();

        Vector2d temp = (Vector2d) auxVars.getRm().clone();
        temp.scale(-1.0);

        double tmp = simulation.calcGravityPotential(temp, auxVars.getRe(), auxVars.getRm()) - simulation.calcGravityPotential(y.getRocket().getR(), auxVars.getRe(), auxVars.getRm());
        if ((y.getRocket().getV().length() * y.getRocket().getV().length()) / 2.0 > 0.999 * tmp) {
            return 4;
        }

        return 3;
    }
}
