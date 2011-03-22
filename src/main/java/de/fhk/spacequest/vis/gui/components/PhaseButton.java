package de.fhk.spacequest.vis.gui.components;

import de.fhk.spacequest.vis.gui.GUI;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Robert Giacinto
 */
public class PhaseButton extends ToggleButton {

    private Image active = null;
    private Image inactive = null;
    private int n;

    public PhaseButton(int n, float x, float y, GUI gui, String s_active, String s_inactive) {
        super(x, y, 35.0F, 35.0F, gui);
        this.n = n;
        try {
            active = new Image(getClass().getResourceAsStream(s_active), "button_active", false);
            active.setAlpha(0.7f);
            inactive = new Image(getClass().getResourceAsStream(s_inactive), "button_inactive", false);
            inactive.setAlpha(0.7f);
        } catch (SlickException ex) {
            Logger.getLogger(PresetToggleButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void executeAction() {
    }

    @Override
    public void draw(Graphics g) {
        if (gui.getSpacequest().getSimulation().getCurrentControlPhase().getPhase() == n) {
            g.drawImage(active, x, y);
        } else {
            g.drawImage(inactive, x, y);
        }
        g.setColor(Color.white);
        g.drawString(Integer.toString(n), x + 14.0F, y + 10.0F);
    }
}
