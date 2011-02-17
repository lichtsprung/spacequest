package de.fhk.spacequest.vis.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * ToggleButton, der die Kameravoreinstellungen während der Visualisierung (de)aktiviert.
 *
 * @author Robert Giacinto
 */
public class PresetToggleButton extends ToggleButton {

    private Image active;
    private Image inactive;

    public PresetToggleButton(float x, float y, GUI gui, String s_active, String s_inactive) throws SlickException {
        super(x, y, 35.0F, 35.0F, gui);

        active = new Image(PresetToggleButton.class.getResource(s_active).getFile());
        active.setAlpha(0.7f);
        inactive = new Image(PresetToggleButton.class.getResource(s_inactive).getFile());
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
        g.drawString("CP", x + 10.0F, y + 10.0F);
    }
}
