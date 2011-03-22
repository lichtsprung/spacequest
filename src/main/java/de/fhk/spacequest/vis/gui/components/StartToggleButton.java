package de.fhk.spacequest.vis.gui.components;

import de.fhk.spacequest.vis.gui.GUI;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

/**
 * @author Robert Giacinto
 */
public class StartToggleButton extends ToggleButton {

    private Image active;
    private Image inactive;

    public StartToggleButton(float x, float y, GUI gui, String s_active, String s_inactive) throws SlickException {
        super(x, y, 35.0F, 35.0F, gui);

        active = new Image(getClass().getResourceAsStream(s_active), "button_active", false);
        active.setAlpha(0.7f);
        inactive = new Image(getClass().getResourceAsStream(s_inactive), "button_inactive", false);
        inactive.setAlpha(0.7f);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);

        if (gui.getSpacequest().getSimulation().isPaused()) {
            g.drawImage(inactive, x, y);
            g.fillRect(x + 15.0F, y + 15.0F, 4.0F, 10.0F);
            g.fillRect(x + 20.0F, y + 15.0F, 4.0F, 10.0F);

        } else {
            g.drawImage(active, x, y);
            Polygon poly = new Polygon();
            poly.addPoint(x + 15.0F, y + 15.0F);
            poly.addPoint(x + 15.0F, y + 25.0F);
            poly.addPoint(x + 25.0F, y + 20.0F);
            poly.setClosed(true);
            g.fill(poly);
        }


    }

    @Override
    protected void executeAction() {
        gui.getSpacequest().getSimulation().togglePause();
    }
}
