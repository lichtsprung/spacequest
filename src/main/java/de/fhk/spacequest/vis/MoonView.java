package de.fhk.spacequest.vis;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

/**
 * @author Robert Giacinto
 */
public final class MoonView extends VisObject {

    private Moon moonModel;

    public MoonView(Moon moonModel, String s_moon) throws SlickException {
        texture = new Image(getClass().getResourceAsStream(s_moon), "moon", false);

        setMoonModel(moonModel);
    }

    public Moon getMoonModel() {
        return moonModel;
    }

    public void setMoonModel(Moon moonModel) {
        this.moonModel = moonModel;

        x = (float) moonModel.getR().x;
        y = (float) moonModel.getR().y;

        poly = new Polygon();
        poly.addPoint((float) ((double) x - Moon.R), (float) ((double) y - Moon.R));
        poly.addPoint((float) ((double) x + Moon.R), (float) ((double) y - Moon.R));
        poly.addPoint((float) ((double) x + Moon.R), (float) ((double) y + Moon.R));
        poly.addPoint((float) ((double) x - Moon.R), (float) ((double) y + Moon.R));
    }

    @Override
    public void draw(Camera cam) {
        cam.draw(this);
    }
}
