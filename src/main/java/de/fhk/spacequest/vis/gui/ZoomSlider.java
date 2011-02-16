package de.fhk.spacequest.vis.gui;

import org.newdawn.slick.SlickException;
 
/**
 *
 * @author Robert Giacinto / Adrian Wagner
 */
public class ZoomSlider extends Slider {

    public ZoomSlider(float x, float y, float min, float max, float length, GUI gui, String s_active) throws SlickException{
        super(x, y, min, max, length, gui, s_active);
    }

    @Override
    public void executeAction(float sliderValue) {
        gui.getSpacequest().getCam().setZoom(sliderValue);
        gui.getSpacequest().rescaleTextures();
    }
}
