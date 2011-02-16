package de.fhk.spacequest.vis.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
 
/**
 *
 * @author Robert Giacinto
 */
public class StartToggleButton extends ToggleButton {

    private Image active;
    private Image inactive;

    public StartToggleButton(float x, float y, GUI gui, String s_active, String s_inactive) throws SlickException {
        super(x, y, 35, 35, gui);

        active = new Image(s_active);
        active.setAlpha(0.7f);
        inactive = new Image(s_inactive);
        inactive.setAlpha(0.7f);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);

        if (gui.getSpacequest().getSimulation().isPaused()) {
            g.drawImage(inactive, x, y);
            g.fillRect(x + 15, y + 15, 4, 10);
            g.fillRect(x + 20, y + 15, 4, 10);

        } else {
            g.drawImage(active, x, y);
            Polygon poly = new Polygon();
            poly.addPoint(x + 15, y + 15);
            poly.addPoint(x + 15, y + 25);
            poly.addPoint(x + 25, y + 20);
            poly.setClosed(true);
            g.fill(poly);
        }


    }

    @Override
    protected void executeAction() {
        gui.getSpacequest().getSimulation().togglePause();
    }
}
