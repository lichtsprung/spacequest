package de.fhk.spacequest.vis;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

/**
 * @author Robert Giacinto
 */
public class Camera {

    public static final Camera presetEarth = new Camera(-4631092.0f, 0.0f, Spacequest.PWIDTH, Spacequest.PHEIGHT, 2.6053895E-5f, null),
            presetEarthMoon = new Camera(1.88085408E8f, 1.19217056E8f, Spacequest.PWIDTH, Spacequest.PHEIGHT, 1.9292545E-6f, null),
            presetMoon = new Camera(2.66339712E8f, 2.71510944E8f, Spacequest.PWIDTH, Spacequest.PHEIGHT, 3.2829426E-5f, null);
    protected float x = 0.0F, y = 0.0F;
    protected int width = 0, height = 0;
    protected float zoom = 0.0F;
    protected Graphics g = null;
    private Camera currentPreset = null;

    private Camera() {
    }

    public Camera(float x, float y, int width, int height, float zoom, Graphics g) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.g = g;
    }

    public void loadPreset(Camera preset) {
        x = preset.x;
        y = preset.y;
        zoom = preset.zoom;
        currentPreset = preset;
    }

    public Camera getCurrentPreset() {
        return currentPreset;
    }

    public Polygon getScreenPolygon(Polygon poly) {
        return (Polygon) poly.transform(Transform.createTranslateTransform(-x, -y)).transform(Transform.createScaleTransform(zoom, zoom)).transform(Transform.createTranslateTransform((float) (width / 2), (float) (height / 2)));
    }

    public void draw(VisObject visobject) {
        if (visobject.texture == null) {
            g.fill(getScreenPolygon(visobject.poly));
        } else {
            Polygon screenPolygon = getScreenPolygon(visobject.poly);
            g.drawImage(visobject.scaledTexture, screenPolygon.getMinX(), screenPolygon.getMinY());
        }
    }

    public Graphics getG() {
        return g;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    @Override
    public String toString() {
        return "Position: " + x + ", " + y + "\nZoom: " + zoom;
    }
}
