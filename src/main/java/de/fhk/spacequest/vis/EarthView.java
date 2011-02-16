package de.fhk.spacequest.vis;

import de.fhk.spacequest.simulation.Earth;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author Robert Giacinto
 */
public final class EarthView extends VisObject {

    private Earth earthModel;

    public EarthView(Earth earthModel, String s_earth) throws SlickException {
        texture = new Image(s_earth);

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
        poly.addPoint((float) (x - Earth.R ), (float) (y - Earth.R ));
        poly.addPoint((float) (x + Earth.R ), (float) (y - Earth.R ));
        poly.addPoint((float) (x + Earth.R ), (float) (y + Earth.R ));
        poly.addPoint((float) (x - Earth.R ), (float) (y + Earth.R ));
    }

    @Override
    public void draw(Camera cam) {
        cam.draw(this);
    }
}
