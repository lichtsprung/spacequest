package de.fhk.spacequest.vis.gui;
 
import de.fhk.spacequest.vis.Spacequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * ToggleButton, der die Kameravoreinstellungen während der Visualisierung (de)aktiviert.
 * @author Robert Giacinto
 */
public class PresetToggleButton extends ToggleButton {

    private Image active;
    private Image inactive;

    public PresetToggleButton(float x, float y, GUI gui, String s_active, String s_inactive) throws SlickException {
        super(x, y, 35, 35, gui);

        active = new Image(s_active);
        active.setAlpha(0.7f);
        inactive = new Image(s_inactive);
        inactive.setAlpha(0.7f);
    }

    @Override
    protected void executeAction() {
        gui.getSpacequest().setUseCamPresets(!gui.getSpacequest().isUseCamPresets());
    }

    @Override
    public void draw(Graphics g) {
        if (gui.getSpacequest().isUseCamPresets()) {
            g.drawImage(active, x, y);
        } else {
            g.drawImage(inactive, x, y);
        }

        g.setColor(Color.white);
        g.drawString("CP", x + 10, y + 10);
    }
}
