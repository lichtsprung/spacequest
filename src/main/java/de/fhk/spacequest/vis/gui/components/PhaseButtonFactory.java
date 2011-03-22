package de.fhk.spacequest.vis.gui.components;

import de.fhk.spacequest.controlphases.ControlPhase;
import de.fhk.spacequest.vis.gui.GUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory-Klasse, die InfoButtons für alle Kontrollphasen der Simulation erzeugt.
 *
 * @author Robert Giacinto
 */
public abstract class PhaseButtonFactory {

    public static List<PhaseButton> createPhaseButtons(float x, float y, boolean vertical, GUI gui, String s_active, String s_inactive) {
        List<PhaseButton> phaseButtons = new ArrayList<PhaseButton>();

        if (vertical) {
            for (ControlPhase cp : gui.getSpacequest().getSimulation().getControlPhases()) {
                phaseButtons.add(new PhaseButton(cp.getPhase(), x, y + (float) (cp.getPhase() * 39), gui, s_active, s_inactive));
            }
        } else {
            for (ControlPhase cp : gui.getSpacequest().getSimulation().getControlPhases()) {
                phaseButtons.add(new PhaseButton(cp.getPhase(), x + (float) (cp.getPhase() * 38), y, gui, s_active, s_inactive));
            }
        }
        return phaseButtons;
    }
}
