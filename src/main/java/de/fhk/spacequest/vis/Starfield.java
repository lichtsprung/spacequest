package de.fhk.spacequest.vis;

import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Prozedural erzeugter Sternenhintergrund.
 *
 * @author Robert Giacinto
 */
public class Starfield {

    private int starCount;
    private float width;
    private float height;

    /**
     * Erzeugt einen neuen Sternenhintergrund.
     *
     * @param width     die Startbreite des Sternenhimmels
     * @param height    die Starthöhe des Sternenhimmels
     * @param starCount die Anzahl der Sterne
     */
    public Starfield(float width, float height, int starCount) {
        this.width = width;
        this.height = height;
        this.starCount = starCount;
        initStarfield();
    }

    private void initStarfield() {
        Collection<Star> stars = new ArrayList<Star>(starCount);
        for (int i = 0; i < starCount; i++) {
            stars.add(new Star());
        }
    }

    private class Star extends VisObject {

        private float x;
        private float y;
        private float baseLight;
        private float frequency;
        private float amplitude;
        private Color color = null;

        public Star() {
            Random rand = new Random(System.nanoTime());
            x = (float) (rand.nextGaussian() * (double) width);
            y = (float) (rand.nextGaussian() * (double) height);
            baseLight = (float) rand.nextGaussian();
            frequency = (float) (rand.nextGaussian() * 5.0);
            amplitude = (float) ((double) baseLight - rand.nextGaussian());

        }

        @Override
        public void draw(Camera cam) {
        }
    }
}
