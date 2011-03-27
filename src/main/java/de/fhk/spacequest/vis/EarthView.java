package de.fhk.spacequest.vis;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

/**
 * @author Robert Giacinto
 */
public final class EarthView extends VisObject {

    private Earth earthModel;

    public EarthView(Earth earthModel, String s_earth) throws SlickException {
        texture = new Image(getClass().getResourceAsStream(s_earth), "earth", false);

        setEarthModel(earthModel);
    }

    public Earth getEarthModel() {
        return earthModel;
    }

    public void setEarthModel(Earth earthModel) {
        this.earthModel = earthModel;

        x = (float) earthModel.getR().x;
        y = (float) earthModel.getR().y;

        poly = new Polygon();
        poly.addPoint((float) ((double) x - Earth.R), (float) ((double) y - Earth.R));
        poly.addPoint((float) ((double) x + Earth.R), (float) ((double) y - Earth.R));
        poly.addPoint((float) ((double) x + Earth.R), (float) ((double) y + Earth.R));
        poly.addPoint((float) ((double) x - Earth.R), (float) ((double) y + Earth.R));
    }

    @Override
    public void draw(Camera cam) {
        cam.draw(this);
    }
}
