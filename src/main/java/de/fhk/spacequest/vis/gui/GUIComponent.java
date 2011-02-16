package de.fhk.spacequest.vis.gui;
 
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

/**
 * Grundklasse aller Komponenten, die innerhalb der GUI gezeichnet werden können.
 * TODO Die Hintergrundbilder-URIs sollten nicht fix im Code stehen. -> Property-File?
 * @author Robert Giacinto
 */
public abstract class GUIComponent implements MouseListener {

    protected float width;
    protected float height;
    protected float x;
    protected float y;
    protected GUI gui;

    public GUIComponent(float x, float y, float width, float height, GUI gui){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.gui = gui;
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        float disX = oldx - x;
        float disY = oldy - y;

        if (disX > 0 && disX < width) {
            if (disY > 0 && disY < height) {
                gui.setFocus(true);
                x = x + (newx - oldx);
                y = y + (newy - oldy);
            }
        }
    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
    }

    public void mousePressed(int button, int x, int y) {
    }

    public void mouseReleased(int button, int x, int y) {
        gui.setFocus(false);
    }

    public void mouseWheelMoved(int change) {
    }

    public boolean isAcceptingInput() {
        return true;
    }

    public void inputEnded() {
    }

    public void inputStarted() {
    }

    public void setInput(Input input) {
    }

    public abstract void draw(Graphics g);

    public abstract void update();
}
