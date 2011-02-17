package de.fhk.spacequest.vis.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Adrian Wagner
 */
public class SliderButton extends Button {

    private Image texture;
    private Slider slider;
    public float value;

    public SliderButton(float x, float y, Slider slider, GUI gui, String s_slider) throws SlickException {
        super(x, y, 35, 35, false, gui);

        this.slider = slider;
        value = slider.width / 2;

        texture = new Image(SliderButton.class.getResource(s_slider).getFile());
        texture.setAlpha(0.7f);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        float disX = oldx - x;
        float disY = oldy - y;

        if (disX > 0 && disX < width) {
            if (disY > 0 && disY < height) {
                gui.setFocus(true);
                value = value + (newx - oldx);

                if (value > slider.width) {
                    value = slider.width;
                }

                if (value <= 0) {
                    value = 1;
                }
            }
        }
    }

    @Override
    public void update() {
        x = slider.x + value - 35 / 2;
        y = slider.y;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, x, y);
    }

    protected void executeAction() {
    }
}
