package de.fhk.spacequest.vis.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Adrian Wagner
 */
public class SliderButton extends Button {

    private Image texture;
    private Slider slider;
    public float value;

    public SliderButton(float x, float y, Slider slider, GUI gui, String s_slider) throws SlickException {
        super(x, y, 35.0F, 35.0F, false, gui);

        this.slider = slider;
        value = slider.width / 2.0F;

        texture = new Image(SliderButton.class.getResource(s_slider).getFile());
        texture.setAlpha(0.7f);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        float disX = (float) oldx - x;
        float disY = (float) oldy - y;

        if (disX > 0.0F && disX < width) {
            if (disY > 0.0F && disY < height) {
                gui.setFocus(true);
                value += (float) (newx - oldx);

                if (value > slider.width) {
                    value = slider.width;
                }

                if (value <= 0.0F) {
                    value = 1.0F;
                }
            }
        }
    }

    @Override
    public void update() {
        x = slider.x + value - (float) (35 / 2);
        y = slider.y;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, x, y);
    }

    protected void executeAction() {
    }
}
