package de.fhk.spacequest.vis.gui.components;

import de.fhk.spacequest.vis.gui.GUI;
import de.fhk.spacequest.vis.gui.GUIComponent;
import org.joda.time.Period;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

/**
 * Die Stoppuhr, die in der Visualisierung die vergangene Zeit seit Start der Rakete anzeigt.
 *
 * @author Robert Giacinto
 */
public class AnalogClock extends GUIComponent {

    private Image clock;
    private long time;
    private Period duration;
    private Line[] smLines, hourLines;
    private float radius1, radius2;

    /**
     * Erstellt eine Stoppuhr an der Position (x,y).
     *
     * @param x         X-Koordinate der Uhr
     * @param y         Y-Koordinate der Uhr
     * @param clockSize der Radius der Uhr
     * @param gui       Referenz auf die GUI
     * @throws SlickException
     */
    public AnalogClock(float x, float y, float clockSize, GUI gui, String s_clock) throws SlickException {
        super(x, y, 2.0F * clockSize, 2.0F * clockSize, gui);
        radius1 = clockSize;
        radius2 = 0.7f * clockSize;

        smLines = new Line[60];
        hourLines = new Line[12];

        float mx = x + (width / 2.0F) + 2.0F;
        float my = y + (height / 2.0F) + 2.0F;

        for (int i = 0; i < 60; i++) {
            float cx = (float) ((double) mx + (double) radius1 * StrictMath.cos(Math.toRadians((double) (i * 6))));
            float cy = (float) ((double) my + (double) radius1 * StrictMath.sin(Math.toRadians((double) (i * 6))));
            smLines[(i + 15) % 60] = new Line(mx, my, cx, cy);
        }

        for (int i = 0; i < 12; i++) {
            float cx = (float) ((double) mx + (double) radius2 * StrictMath.cos(Math.toRadians((double) (i * 30))));
            float cy = (float) ((double) my + (double) radius2 * StrictMath.sin(Math.toRadians((double) (i * 30))));
            hourLines[(i + 3) % 12] = new Line(mx, my, cx, cy);
        }


        clock = new Image(AnalogClock.class.getResource(s_clock).getFile()).getScaledCopy((int) width, (int) height);
        clock.setAlpha(0.8f);
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
        return duration.getMillis();
    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {
        super.mouseDragged(i, i1, i2, i3);

        float mx = x + (width / 2.0F) + 2.0F;
        float my = y + (height / 2.0F) + 2.0F;

        for (int k = 0; k < 60; k++) {
            float cx = (float) ((double) mx + (double) radius1 * StrictMath.cos(Math.toRadians((double) (k * 6))));
            float cy = (float) ((double) my + (double) radius1 * StrictMath.sin(Math.toRadians((double) (k * 6))));
            smLines[(k + 15) % 60] = new Line(mx, my, cx, cy);
        }

        for (int k = 0; k < 12; k++) {
            float cx = (float) ((double) mx + (double) radius2 * StrictMath.cos(Math.toRadians((double) (k * 30))));
            float cy = (float) ((double) my + (double) radius2 * StrictMath.sin(Math.toRadians((double) (k * 30))));
            hourLines[(k + 3) % 12] = new Line(mx, my, cx, cy);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.gray);
        g.drawImage(clock, x, y);
        g.draw(smLines[getSeconds()]);
        g.setLineWidth(3.0F);
        g.draw(smLines[getMinutes()]);
        int h = (getHours() > 11) ? getHours() - 12 : getHours();
        g.setLineWidth(4.0F);
        g.draw(hourLines[h]);
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
