package de.fhk.spacequest.controlphases;

/**
 * @author Robert Giacinto
 */
public class Phase0 extends ControlPhase {

    public Phase0() {
        super(0, new PhaseInfo("Dummy Phase", "Diese Phase macht nichts"));
    }

    @Override
    public void control() {
    }

    @Override
    public int checkConditions() {
        return 1;
    }
}
