package de.fhk.spacequest.controlphases;

import de.fhk.spacequest.simulation.AuxVars;
import de.fhk.spacequest.simulation.Constants;
import de.fhk.spacequest.simulation.Moon;

/**
 *
 * @author Robert Giacinto
 */
public class Phase4 extends ControlPhase {

    public Phase4() {
        super(4, new PhaseInfo("Freiflug zum Mond", "Die Rakete hat nun den Erdorbit "
                + "\nverlassen. Bei einer guten Steuerung "
                + "\nhat sie nun eine Geschwindigkeit "
                + "\nerreicht, sodass sie im freien Flug "
                + "\nden Mond mit einer nicht zu hohen"
                + "\nGeschwindigkeit erreicht. Außerdem "
                + "\nsollte noch eine ausreichende "
                + "\nTreibstoffmenge übrig sein, um "
                + "\ndie Rakete dann in einen Mondorbit "
                + "\neinzusteuern."));
    }

    @Override
    public void control() {
    }

    @Override
    public int checkConditions() {
        AuxVars auxVars = getRocketControl().getAuxVars();

        if (((auxVars.getErrm().dot(auxVars.getVrm()) > 0
                && auxVars.getBrrm() - Moon.R < 50 * Moon.R)
                || (auxVars.getBrrm() - Moon.R < 0.5 * Moon.R))) {
            return 5;
        } else if ((auxVars.getBrre() > Constants.EARTH_ORBIT * Constants.D)) {
            return 6;
        } else {
            return 4;
        }
    }
}
