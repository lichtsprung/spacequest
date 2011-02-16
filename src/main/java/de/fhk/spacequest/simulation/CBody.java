package de.fhk.spacequest.simulation;

import javax.vecmath.Vector2d;

/**
 * Grundklasse aller Himmelskörper.
 * 
 * @author Robert Giacinto
 */
public abstract class CBody {

    /**
     * Aktuelle Position des Himmelskörpers
     */
    private Vector2d r;

    /**
     * Erstellt einen neuen Himmelskörper.
     */
    public CBody() {
        r = new Vector2d();
    }



    /**
     * Gibt die aktuelle Position des Himmelskörpers zurück.
     * @return die aktuelle Position des Himmelskörpers
     */
    public Vector2d getR() {
        return r;
    }

    /**
     * Ändert die aktuelle Position des Himmelskörpers.
     *
     * @param r die neue Position des Himmelskörpers
     */
    public void setR(Vector2d r) {
        this.r = r;
    }
}
