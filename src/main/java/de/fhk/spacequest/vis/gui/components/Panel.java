package de.fhk.spacequest.vis.gui.components;

import de.fhk.spacequest.vis.gui.GUI;
import de.fhk.spacequest.vis.gui.GUIComponent;

/**
 * @author Robert Giacinto
 */
public abstract class Panel extends GUIComponent {

    protected Panel(float x, float y, float width, float height, GUI gui) {
        super(x, y, width, height, gui);
    }
}
