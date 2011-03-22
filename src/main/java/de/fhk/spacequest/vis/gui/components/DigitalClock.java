package de.fhk.spacequest.vis.gui.components;

import de.fhk.spacequest.vis.gui.GUI;
import de.fhk.spacequest.vis.gui.GUIComponent;
import org.joda.time.Period;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Digitaluhr, die die Laufzeit der Simulation anzeigt.
 *
 * @author Robert Giacinto
 */
public class DigitalClock extends GUIComponent {

    private Image clockBG;
    private long time = 0L;
    private Period duration;

    public DigitalClock(float x, float y, GUI gui, String s_clockBG) throws SlickException {
        super(x, y, 375.0F, 64.0F, gui);
        clockBG = new Image(getClass().getResourceAsStream(s_clockBG), "clockbg", false);
        duration = new Period();
    }

    /**
     * Gibt die Tage der Simulationszeitspanne zurück.
     *
     * @return die vergangenen Tage
     */
    public int getDays() {
        return duration.getHours() / 24;
    }

    /**
     * Gibt die Stunden der Simulationszeitspanne zurück.
     *
     * @return die vergangenen Stunden
     */
    public int getHours() {
        return duration.getHours() % 24;
    }

    /**
     * Gibt die Minuten der Simulationszeitspanne zurück.
     *
     * @return die vergangenen Minuten
     */
    public int getMinutes() {
        return duration.getMinutes();
    }

    /**
     * Gibt die Sekunden der Simulationszeitspanne zurück.
     *
     * @return die vergangenen Sekunden
     */
    public int getSeconds() {
        return duration.getSeconds();
    }

    /**
     * Gibt die Millisekunden der Simulationszeitspanne zurück.
     *
     * @return die vergangenen Millisekunden
     */
    public int getMilliSeconds() {
        return duration.getMillis() % 1000;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(clockBG, x, y);

        String days = Integer.toString(getDays()),
                hours = Integer.toString(getHours()),
                minutes = Integer.toString(getMinutes()),
                seconds = Integer.toString(getSeconds()),
                milliseconds = Integer.toString(getMilliSeconds());

        if (days.length() == 1) {
            g.drawString("0", x + 21.0F, y + 25.0F);
            g.drawString("0", x + 43.0F, y + 25.0F);
            g.drawString(days, x + 63.0F, y + 25.0F);
        } else if (days.length() == 2) {
            g.drawString("0", x + 21.0F, y + 25.0F);
            g.drawString(Character.toString(days.charAt(0)), x + 43.0F, y + 25.0F);
            g.drawString(Character.toString(days.charAt(1)), x + 63.0F, y + 25.0F);
        } else if (days.length() == 3) {
            g.drawString(Character.toString(days.charAt(0)), x + 21.0F, y + 25.0F);
            g.drawString(Character.toString(days.charAt(1)), x + 43.0F, y + 25.0F);
            g.drawString(Character.toString(days.charAt(2)), x + 63.0F, y + 25.0F);
        }

        if (hours.length() == 1) {
            g.drawString("0", x + 91.0F, y + 25.0F);
            g.drawString("0", x + 111.0F, y + 25.0F);
            g.drawString(hours, x + 132.0F, y + 25.0F);
        } else if (hours.length() == 2) {
            g.drawString("0", x + 91.0F, y + 25.0F);
            g.drawString(Character.toString(hours.charAt(0)), x + 111.0F, y + 25.0F);
            g.drawString(Character.toString(hours.charAt(1)), x + 132.0F, y + 25.0F);
        } else if (hours.length() == 3) {
            g.drawString(Character.toString(hours.charAt(0)), x + 91.0F, y + 25.0F);
            g.drawString(Character.toString(hours.charAt(1)), x + 111.0F, y + 25.0F);
            g.drawString(Character.toString(hours.charAt(2)), x + 132.0F, y + 25.0F);
        }

        if (minutes.length() == 1) {
            g.drawString("0", x + 162.0F, y + 25.0F);
            g.drawString("0", x + 183.0F, y + 25.0F);
            g.drawString(minutes, x + 203.0F, y + 25.0F);
        } else if (minutes.length() == 2) {
            g.drawString("0", x + 162.0F, y + 25.0F);
            g.drawString(Character.toString(minutes.charAt(0)), x + 183.0F, y + 25.0F);
            g.drawString(Character.toString(minutes.charAt(1)), x + 203.0F, y + 25.0F);
        } else if (minutes.length() == 3) {
            g.drawString(Character.toString(minutes.charAt(0)), x + 162.0F, y + 25.0F);
            g.drawString(Character.toString(minutes.charAt(1)), x + 183.0F, y + 25.0F);
            g.drawString(Character.toString(minutes.charAt(2)), x + 203.0F, y + 25.0F);
        }

        if (seconds.length() == 1) {
            g.drawString("0", x + 232.0F, y + 25.0F);
            g.drawString("0", x + 253.0F, y + 25.0F);
            g.drawString(seconds, x + 273.0F, y + 25.0F);
        } else if (seconds.length() == 2) {
            g.drawString("0", x + 232.0F, y + 25.0F);
            g.drawString(Character.toString(seconds.charAt(0)), x + 253.0F, y + 25.0F);
            g.drawString(Character.toString(seconds.charAt(1)), x + 273.0F, y + 25.0F);
        } else if (seconds.length() == 3) {
            g.drawString(Character.toString(seconds.charAt(0)), x + 232.0F, y + 25.0F);
            g.drawString(Character.toString(seconds.charAt(1)), x + 253.0F, y + 25.0F);
            g.drawString(Character.toString(seconds.charAt(2)), x + 273.0F, y + 25.0F);
        }


        if (milliseconds.length() == 1) {
            g.drawString("0", x + 301.0F, y + 25.0F);
            g.drawString("0", x + 322.0F, y + 25.0F);
            g.drawString(milliseconds, x + 343.0F, y + 25.0F);
        } else if (milliseconds.length() == 2) {
            g.drawString("0", x + 301.0F, y + 25.0F);
            g.drawString(Character.toString(milliseconds.charAt(0)), x + 322.0F, y + 25.0F);
            g.drawString(Character.toString(milliseconds.charAt(1)), x + 343.0F, y + 25.0F);
        } else if (milliseconds.length() == 3) {
            g.drawString(Character.toString(milliseconds.charAt(0)), x + 301.0F, y + 25.0F);
            g.drawString(Character.toString(milliseconds.charAt(1)), x + 322.0F, y + 25.0F);
            g.drawString(Character.toString(milliseconds.charAt(2)), x + 343.0F, y + 25.0F);
        }


    }

    @Override
    public void update() {
        if (!gui.getSpacequest().getSimulation().isPaused()) {
            double dt = gui.getSpacequest().getSimulation().getDT();
            time += (long) dt;
            duration = new Period(time * 1000L);
        }
    }
}
