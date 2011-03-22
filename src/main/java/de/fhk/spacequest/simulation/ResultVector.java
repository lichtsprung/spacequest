package de.fhk.spacequest.simulation;

/**
 * Diese Klasse wird zur Speicherung des Simulationszustandes zu einem Zeitpunkt t
 * verwendet.
 *
 * @author Robert Giacinto
 */
public class ResultVector {
    /**
     * Der Zustand der Rakete zum Zeitpunkt t.
     */
    private Rocket rocket;
    /**
     * Der Zustand der Erde zum Zeitpunkt t.
     */
    private Earth earth;
    /**
     * Der Zustand des Monds zum Zeitpunkt t.
     */
    private Moon moon;
    /**
     * Der Zeitpunkt t.
     */
    private double t;

    /**
     * Erstellt einen neuen Ergebnisvektor für den Zeitpunkt t.
     *
     * @param rocket der Zustand der Rakete
     * @param earth  der Zustand der Erde
     * @param moon   der Zustand des Monds
     * @param t      der Zeitpunkt
     */
    public ResultVector(Rocket rocket, Earth earth, Moon moon, double t) {
        this.rocket = rocket;
        this.earth = earth;
        this.moon = moon;
        this.t = t;
    }

    @Override
    protected Object clone() {
        ResultVector rv = new ResultVector((Rocket) rocket.clone(), (Earth) earth.clone(), (Moon) moon.clone(), t);
        return rv;
    }

    @Override
    public String toString() {
        String output = "ResultVector: " + t + "\n"
                + "\t Rakete: \n"
                + rocket.toString() + "\n";
        return output;
    }

    /**
     * Gibt die Rakete zurück.
     *
     * @return die Rakete
     */
    public Rocket getRocket() {
        return rocket;
    }


    /**
     * Gibt die Erde zurück.
     *
     * @return die Erde
     */
    public Earth getEarth() {
        return earth;
    }


    /**
     * Gibt den Mond zurück.
     *
     * @return der Mond
     */
    public Moon getMoon() {
        return moon;
    }


    /**
     * Gibt des Zeitpunkt zurück.
     *
     * @return der Zeitpunkt t
     */
    public double getT() {
        return t;
    }

    /**
     * Ändert den Zeitpunkt.
     *
     * @param t der neue Zeitpunkt
     */
    public void setT(double t) {
        this.t = t;
    }


}
