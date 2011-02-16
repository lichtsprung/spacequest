package de.fhk.spacequest.vis;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author Robert Giacinto
 */
public abstract class VisObject {

    protected Polygon poly;
    protected float x, y;
    protected Image texture;
    protected Image scaledTexture;

    public abstract void draw(Camera cam);

    public void updateScaledTexture(Camera cam) {
        Polygon p = cam.getScreenPolygon(poly);
        int width = (int) (p.getMaxX() - p.getMinX());
        int height = (int) (p.getMaxY() - p.getMinY());
        scaledTexture = texture.getScaledCopy(width, height);
    }
}
