package de.fhk.spacequest.vis.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * @author Robert Giacinto
 */
public abstract class Button extends GUIComponent {

    private boolean pressed = false;
    private boolean toggleButton;

    protected Button(float x, float y, float width, float height, boolean toggleButton, GUI gui) {
        super(x, y, width, height, gui);
        this.toggleButton = toggleButton;
    }

    @Override
    public void mousePressed(int i, int i1, int i2) {
        if (!toggleButton) {
            float disX = (float) i1 - x;
            float disY = (float) i2 - y;

            if (disX > 0.0F && disX < width) {
                if (disY > 0.0F && disY < height) {
                    pressed = true;
                }
            }
        }
    }

    @Override
    public void mouseReleased(int i, int i1, int i2) {
        if (!toggleButton) {
            float disX = (float) i1 - x;
            float disY = (float) i2 - y;

            if (disX > 0.0F && disX < width) {
                if (disY > 0.0F && disY < height) {
                    pressed = false;
                }
            }
        }
    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {
        if (toggleButton) {
            float disX = (float) i1 - x;
            float disY = (float) i2 - y;

            if (disX > 0.0F && disX < width) {
                if (disY > 0.0F && disY < height) {
                    executeAction();
                }
            }
        }
    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void update() {
        if (pressed) {
            executeAction();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
    }


    protected abstract void executeAction();
}
