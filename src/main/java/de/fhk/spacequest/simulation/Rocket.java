package de.fhk.spacequest.simulation;

import javax.vecmath.Vector2d;

/**
 * Die Rakete in der Simulation.
 *
 * @author Robert Giacinto
 */
public class Rocket extends CBody {

    /**
     * Die Geschwindigkeit der Rakete.
     */
    private Vector2d v;
    /**
     * Die Richtung des Raketenstrahls, normiert.
     */
    private Vector2d et;
    /**
     * Die Richtung des Raketenstrahls, nicht normiert.
     */
    private Vector2d etv;
    /**
     * Die Masse der Rakete.
     */
    private double m = 0.0;
    /**
     * Die Abbrandrate des Triebwerks.
     */
    private double mp = 0.0;
    /**
     * Die Nutzlast der Rakete.
     */
    private double mn = 0.0;
    /**
     * Die Masse des Treibstoffs der Rakete.
     */
    private double mt = 0.0;

    /**
     * Erstellt eine neue Rakete.
     */
    public Rocket() {
        v = new Vector2d();
        et = new Vector2d();
        etv = new Vector2d();
    }

    @Override
    protected Object clone() {
        Rocket ro = new Rocket();
        ro.setEt((Vector2d) et.clone());
        ro.setEtv((Vector2d) etv.clone());
        ro.setV((Vector2d) v.clone());
        ro.setR((Vector2d) getR().clone());
        ro.setM(m);
        ro.setMp(mp);
        return ro;
    }

    /**
     * Die Richtung des Raketenstrahls.
     *
     * @return die Richtung des Raketenstrahls
     */
    public Vector2d getEt() {
        return et;
    }

    /**
     * Die Masse der Rakete.
     *
     * @return die Masse der Rakete
     */
    public double getM() {
        return m;
    }

    /**
     * Die Nutzlast der Rakete.
     *
     * @return die Nutzlast der Rakete
     */
    public double getMn() {
        return mn;
    }

    /**
     * Die Abbrandrate des Triebwerks der Rakete.
     *
     * @return die Abbrandrate
     */
    public double getMp() {
        return mp;
    }

    /**
     * Die Treibstoffmenge der Rakete.
     *
     * @return Treibstoffmenge der Rakete
     */
    public double getMt() {
        return mt;
    }

    /**
     * Die Geschwindigkeit der Rakete.
     *
     * @return Geschwindigkeit der Rakete
     */
    public Vector2d getV() {
        return v;
    }

    @Override
    public String toString() {
        String output = "\t\tOrtsvektor: " + getR() + "\n"
                + "\t\tGeschwindigkeit: " + v + "\n"
                + "\t\tRaketenstrahl: " + et + "\n"
                + "\t\tMasse: " + m + "\n"
                + "\t\tNutzlast: " + mn + "\n"
                + "\t\tTreibstofmenge: " + mt + "\n"
                + "\t\tAbbrandrate: " + mp;
        return output;
    }

    /**
     * Ändert die Geschwindigkeit der Rakete.
     *
     * @param v die neue Geschwindigkeit der Rakete
     */
    public void setV(Vector2d v) {
        this.v = v;
    }

    /**
     * Ändert die Richtung des Raketenstrahls.
     *
     * @param et die neue Richtung des Raketenstrahls
     */
    public void setEt(Vector2d et) {
        this.et = et;
    }

    /**
     * Ändert die Masse der Rakete.
     *
     * @param m die neue Masse der Rakete
     */
    public void setM(double m) {
        this.m = m;
    }

    /**
     * Ändert die Abbrandrate der Rakete.
     *
     * @param mp die neue Abbrandrate
     */
    public void setMp(double mp) {
        this.mp = mp;
    }

    /**
     * Ändert die Nutzlast der Rakete.
     *
     * @param mn die neue Nutzlast der Rakete
     */
    public void setMn(double mn) {
        this.mn = mn;
    }

    /**
     * Ändert die Treibstoffmenge der Rakete.
     *
     * @param mt die neue Treibstoffmenge der Rakete
     */
    public void setMt(double mt) {
        this.mt = mt;
    }

    /**
     * Gibt die Richtung des Raketenstrahls zurück.
     * Dies ist der nichtnormierte Vektor.
     *
     * @return die Richtung des Raketenstrahls
     */
    public Vector2d getEtv() {
        return etv;
    }

    /**
     * Ändert die Richtung des Raketenstrahls.
     *
     * @param etv die neue Richtung des Raketenstrahls.
     */
    public void setEtv(Vector2d etv) {
        this.etv = etv;
    }
}
