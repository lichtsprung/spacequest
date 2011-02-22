package de.fhk.spacequest.vis;

import de.fhk.spacequest.simulation.Earth;
import de.fhk.spacequest.simulation.Rocket;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import javax.vecmath.Vector2d;

/**
 * Die View der Rakete, die in der Visualisierung dargestellt wird.
 *
 * @author Robert Giacinto
 */
public final class RocketView extends VisObject {

    private Rocket rocketModel;

    public RocketView(Rocket rocketModel, String s_ufo) throws SlickException {
        texture = new Image(getClass().getResourceAsStream(s_ufo), "rocket", false);
        setRocketModel(rocketModel);
    }

    public Rocket getRocketModel() {
        return rocketModel;
    }

    public void setRocketModel(Rocket rocketModel) {
        this.rocketModel = rocketModel;

        x = (float) rocketModel.getR().x;
        y = (float) rocketModel.getR().y;

        poly = new Polygon();
        poly.addPoint((float) ((double) x - Earth.R / 6.0), (float) ((double) y - Earth.R / 6.0));
        poly.addPoint((float) ((double) x + Earth.R / 6.0), (float) ((double) y - Earth.R / 6.0));
        poly.addPoint((float) ((double) x + Earth.R / 6.0), (float) ((double) y + Earth.R / 6.0));
        poly.addPoint((float) ((double) x - Earth.R / 6.0), (float) ((double) y + Earth.R / 6.0));
    }

    @Override
    public void draw(Camera cam) {
        Polygon temp = new Polygon();
        temp.setClosed(false);
        temp.addPoint(x, y);
        //TODO Brauchen eine vernünftige Lösung für die Skalierung des Vektors.
        //TODO Brauchen eigentlich eine elegante Lösung, wie man an die Daten kommen kann.
        temp.addPoint(x + (float) (rocketModel.getEtv().x * 2000000.0), y + (float) (rocketModel.getEtv().y * 2000000.0));
        Polygon sp = cam.getScreenPolygon(temp);
        cam.g.setColor(Color.red);
        cam.g.draw(sp);

        double angle = new Vector2d(0.0, 1.0).angle(rocketModel.getV());
        scaledTexture.setRotation(180.0F - (float) Math.toDegrees(angle));
        Polygon screenPolygon = cam.getScreenPolygon(poly);
        cam.getG().drawImage(scaledTexture, screenPolygon.getMinX(), screenPolygon.getMinY());
    }
}
