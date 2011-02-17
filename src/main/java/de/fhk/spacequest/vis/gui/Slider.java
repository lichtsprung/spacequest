package de.fhk.spacequest.vis.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author Adrian Wagner
 */
public abstract class Slider extends GUIComponent {

    private SliderButton button;
    private float initialValue;
    private float valuePerPixel;

    protected Slider(float x, float y, float min, float max, float length, GUI gui, String s_active) throws SlickException {
        super(x, y, length, 35.0F, gui);
        button = new SliderButton(x + length / 2.0F, y, this, gui, s_active);

        this.initialValue = min;
        this.valuePerPixel = (max - min) / length;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(x, y + (height - 5.0F) / 2.0F, width, 5.0F);
        g.setColor(Color.black);

        button.draw(g);
    }

    @Override
    public void update() {
        button.update();

        executeAction(initialValue + button.value * valuePerPixel);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        if ((float) oldx > button.x && (float) oldx < button.x + 35.0F) {
            button.mouseDragged(oldx, oldy, newx, newy);
        } else {
            super.mouseDragged(oldx, oldy, newx, newy);
        }
    }

    protected abstract void executeAction(float sliderValue);
}
