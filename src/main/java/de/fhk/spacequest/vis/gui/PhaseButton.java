package de.fhk.spacequest.vis.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Robert Giacinto
 */
public class PhaseButton extends ToggleButton {

    private Image active;
    private Image inactive;
    private int n;

    public PhaseButton(int n, float x, float y, GUI gui, String s_active, String s_inactive) {
        super(x, y, 35, 35, gui);
        this.n = n;
        try {
            active = new Image(PhaseButton.class.getResource(s_active).getFile());
            active.setAlpha(0.7f);
            inactive = new Image(PhaseButton.class.getResource(s_inactive).getFile());
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
        g.drawString(Integer.toString(n), x + 14, y + 10);
    }
}
