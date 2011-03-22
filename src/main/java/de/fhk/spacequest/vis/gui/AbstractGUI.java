package de.fhk.spacequest.vis.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: lichtsprung
 * Date: 3/22/11
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractGUI {
    protected Collection<GUIComponent> guiComponents;
    protected Input input;
    private boolean focus = false;
    private boolean paused = false;

    public AbstractGUI(Input input) {
        this.input = input;
        this.guiComponents = new ArrayList<GUIComponent>();
    }

    public void addCompoment(GUIComponent newComponent) {
        guiComponents.add(newComponent);
        input.addMouseListener(newComponent);
    }

    public boolean hasFocus() {
        return focus;
    }

    /**
     * @param focus
     */
    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    /**
     * Aktualisiert den Zustand der GUI-Komponenten.
     */
    public void updateComponents() {
        for (GUIComponent c : guiComponents) {
            c.update();
        }
    }

    /**
     * Zeichnet die Komponenten der GUI.
     *
     * @param g das Graphics-Object
     */
    public void draw(Graphics g) {
        for (GUIComponent c : guiComponents) {
            c.draw(g);
        }
    }

    /**
     * Entfernt Fokus von GUI.
     */
    public void unfocus() {
        for (GUIComponent c : guiComponents) {
            input.removeMouseListener(c);
        }
        paused = true;
    }

    /**
     * Aktiviert den Fokus der GUI.
     */
    public void focus() {
        for (GUIComponent c : guiComponents) {
            input.addMouseListener(c);
        }
        paused = false;
    }

    /**
     * Gibt zurück, ob GUI MouseEvents bearbeitet oder nicht.
     *
     * @return <code>true</code> wenn GUI keinen Fokus besitzt.
     */
    public boolean isPaused() {
        return paused;
    }
}
