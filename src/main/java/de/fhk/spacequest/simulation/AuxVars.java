package de.fhk.spacequest.simulation;

import javax.vecmath.Vector2d;

/**
 * Diese Klasse berechnet alle Hilfsvariablen, die innerhalb der Simulation
 * benötigt werden.
 *
 * @author Robert Giacinto
 */
public final class AuxVars {

    /**
     * Vektor zum Erdmittelpunkt.
     */
    private Vector2d re;
    /**
     * Vektor zum Mondmittelpunkt.
     */
    private Vector2d rm;
    /**
     * Richtungsvektor von der Erde zur Rakete.
     */
    private Vector2d rre;
    /**
     * Richtungsvektor vom Mond zur Rakete.
     */
    private Vector2d rrm;
    /**.
     * Einheitsvektor Erdmittelpunkt zur Rakete
     */
    private Vector2d erre;
    /**
     * Einheitsvektor vom Mondmittelpunkt zur Rakete.
     */
    private Vector2d errm;
    /**
     * Auf die Erde bezogener Einheitstangentialvektor.
     */
    private Vector2d evte;
    /**
     * Auf den Mond bezogener Einheitstangentialvektor.
     */
    private Vector2d evtm;
    /**
     * Geschwindigkeit der Erde.
     */
    private Vector2d ve;
    /**
     * Geschwindigkeit des Monds.
     */
    private Vector2d vm;
    private Vector2d vve;
    private Vector2d vvm;
    private Vector2d vre;
    private Vector2d vte;
    private Vector2d vrm;
    private Vector2d vtm;
    private double brre;
    private double brrm;

    /**
     * Konstruktor für den Hilfsvariablenvektor.
     * 
     * @param rocket die Rakete
     * @param tn der aktuelle Zeitpunkt
     */
    public AuxVars(Rocket rocket, double tn) {
        updateAuxVars(rocket, tn);
    }

    /**
     * Aktualisiert die Hilfsvariablen.
     *
     * @param rocket die Rakete
     * @param tn der aktuelle Zeitpunkt
     */
    public void updateAuxVars(Rocket rocket, double tn) {

        re = Simulation.calcOrbitPosition(-Constants.LE, Constants.PHI10V, tn, Constants.PHI10);
        rm = Simulation.calcOrbitPosition(Constants.LM, Constants.PHI10V, tn, Constants.PHI10);

        rre = (Vector2d) rocket.getR().clone();
        rre.sub(re);

        rrm = (Vector2d) rocket.getR().clone();
        rrm.sub(rm);

        erre = new Vector2d();
        erre.normalize(rre);

        errm = new Vector2d();
        errm.normalize(rrm);



        brre = rre.length();
        brrm = rrm.length();

        ve = new Vector2d(
                Constants.LE * Constants.PHI10V * Math.sin(Constants.PHI10V * tn + Constants.PHI10),
                -Constants.LE * Constants.PHI10V * Math.cos(Constants.PHI10V * tn + Constants.PHI10));
        vm = new Vector2d(
                -Constants.LM * Constants.PHI10V * Math.sin(Constants.PHI10V * tn + Constants.PHI10),
                Constants.LM * Constants.PHI10V * Math.cos(Constants.PHI10V * tn + Constants.PHI10));


        vve = (Vector2d) rocket.getV().clone();
        vve.sub(ve);

        vvm = (Vector2d) rocket.getV().clone();
        vvm.sub(vm);

        evte = new Vector2d(-erre.y, erre.x);
        evtm = new Vector2d(-errm.y, errm.x);

        vre = (Vector2d) erre.clone();
        vre.scale(vve.dot(erre));

        vte = (Vector2d) evte.clone();
        vte.scale(vve.dot(evte));

        vrm = (Vector2d) errm.clone();
        vrm.scale(vve.dot(errm));

        vtm = (Vector2d) evtm.clone();
        vtm.scale(vvm.dot(evtm));
    }

    /**
     * Der Abstand der Rakete zum Erdmittelpunkt.
     *
     * @return der Abstand der Rakete zum Erdmittelpunkt
     */
    public double getBrre() {
        return brre;
    }

    /**
     * Der Abstand der Rakete zum Mondmittelpunkt.
     *
     * @return der Abstand der Rakete zum Mondmittelpunkt
     */
    public double getBrrm() {
        return brrm;
    }

    /**
     * Der Einheitsvektor vom Erdmittelpunkt zur Rakete.
     *
     * @return der Einheitsvektor vom Erdmittelpunkt zur Rakete
     */
    public Vector2d getErre() {
        return erre;
    }

    /**
     * Der Einheitsvektor vom Mondmittelpunkt zur Rakete.
     *
     * @return der Einheitsvektor vom Mondmittelpunkt zur Rakete
     */
    public Vector2d getErrm() {
        return errm;
    }

    /**
     * Auf die Erde bezogener Einheitstangentialvektor.
     *
     * @return auf Erde bezogener Einheitstangentialvektor
     */
    public Vector2d getEvte() {
        return evte;
    }

    /**
     * Auf die Mond bezogener Einheitstangentialvektor.
     *
     * @return auf Mond bezogener Einheitstangentialvektor
     */
    public Vector2d getEvtm() {
        return evtm;
    }

    /**
     * Die Koordinaten des Erdmittelpunkts.
     *
     * @return Koordinaten des Erdmittelpunkts
     */
    public Vector2d getRe() {
        return re;
    }

    /**
     * Die Koordinaten des Mondmittelpunkts.
     *
     * @return Koordinaten des Mondmittelpunkts.
     */
    public Vector2d getRm() {
        return rm;
    }

    /**
     * Der Vektor vom Erdmittelpunkt zur Rakete.
     *
     * @return Vektor vom Erdmittelpunkt zur Rakete
     */
    public Vector2d getRre() {
        return rre;
    }

    /**
     * Der Vektor vom Mondmittelpunkt zur Rakete.
     *
     * @return Vektor vom Mondmittelpunkt zur Rakete
     */
    public Vector2d getRrm() {
        return rrm;
    }

    /**
     * Die Geschwindigkeit der Erde.
     *
     * @return Erdgeschwindigkeit
     */
    public Vector2d getVe() {
        return ve;
    }

    /**
     * Die Geschwindigkeit des Monds.
     *
     * @return Mondgeschwindigkeit
     */
    public Vector2d getVm() {
        return vm;
    }

    /**
     * Auf die Erde bezogene Radialgeschwindigkeit der Rakete.
     *
     * @return auf Erde bezogene Radialgeschwindigkeit der Rakete
     */
    public Vector2d getVre() {
        return vre;
    }

    /**
     * Auf den Mond bezogene Radialgeschwindigkeit der Rakete.
     *
     * @return auf Mond bezogene Radialgeschwindigkeit der Rakete
     */
    public Vector2d getVrm() {
        return vrm;
    }

    /**
     * Auf die Erde bezogene Tangentialgeschwindigkeit der Rakete.
     *
     * @return auf Erde bezogene Tangentialgeschwindigkeit der Rakete
     */
    public Vector2d getVte() {
        return vte;
    }

    /**
     * Auf den Mond bezogene Tangentialgeschwindigkeit der Rakete.
     *
     * @return auf Mond bezogene Tangentialgeschwindigkeit der Rakete
     */
    public Vector2d getVtm() {
        return vtm;
    }

    /**
     * Auf die Erde bezogene Geschwindigkeit der Rakete.
     *
     * @return auf Erde bezogene Geschwindigkeit der Rakete
     */
    public Vector2d getVve() {
        return vve;
    }

    /**
     * Auf den Mond bezogene Geschwindigkeit der Rakete.
     *
     * @return auf Mond bezogene Geschwindigkeit der Rakete
     */
    public Vector2d getVvm() {
        return vvm;
    }

    @Override
    public String toString() {
        String output =
                "brre " + brre + "\n"
                + "brrm " + brrm + "\n"
                + "erre " + erre + "\n"
                + "errm " + errm + "\n"
                + "evte " + evte + "\n"
                + "evtm " + evtm + "\n"
                + "re " + re + "\n"
                + "rm " + rm + "\n"
                + "rre " + rre + "\n"
                + "rrm " + rrm + "\n"
                + "ve " + ve + "\n"
                + "vm " + vm + "\n"
                + "vre " + vre + "\n"
                + "vrm " + vrm + "\n"
                + "vte " + vte + "\n"
                + "vtm " + vtm + "\n"
                + "vve " + vve + "\n"
                + "vvm " + vvm + "\n";
        return output;
    }
}
