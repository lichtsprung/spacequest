package de.fhk.spacequest.vis.gui.components;

import de.fhk.spacequest.vis.gui.GUI;

/**
 * Grundklasse aller ToggleButtons, die durch ein Klick (de)aktiviert werden können.
 *
 * @author Robert Giacinto
 */
public abstract class ToggleButton extends Button {

    /**
     * Erzeugt einen neuen ToggleButton.
     *
     * @param x      x-Position des Buttons
     * @param y      y-Position des Buttons
     * @param width  Breite des Buttons
     * @param height Höhe des Buttons
     */
    protected ToggleButton(float x, float y, float width, float height, GUI gui) {
        super(x, y, width, height, true, gui);
    }
}