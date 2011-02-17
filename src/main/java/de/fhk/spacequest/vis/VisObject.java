package de.fhk.spacequest.vis;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

/**
 * @author Robert Giacinto
 */
public abstract class VisObject {

    protected Polygon poly = null;
    protected float x = 0.0F, y = 0.0F;
    protected Image texture = null;
    protected Image scaledTexture = null;

    public abstract void draw(Camera cam);

    public void updateScaledTexture(Camera cam) {
        Polygon p = cam.getScreenPolygon(poly);
        int width = (int) (p.getMaxX() - p.getMinX());
        int height = (int) (p.getMaxY() - p.getMinY());
        scaledTexture = texture.getScaledCopy(width, height);
    }
}
