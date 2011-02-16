package de.fhk.spacequest.simulation;

/**
 * Diese Klasse repräsentiert das Delta t innerhalb der Berechnungen der Simulation.
 *
 * @author Robert Giacinto
 */
public class DT {
    /**
     * Das aktuelle delta t der Simulation.
     */
    private double dtn;

    /**
     * Gibt das aktuelle Delta t zurück.
     *
     * @return das aktuelle Delta
     */
    public double getDtn() {
        return dtn;
    }

    /**
     * Ändert den Wert des Delta t.
     *
     * @param dtn das neue Delta t
     */
    public void setDtn(double dtn) {
        this.dtn = dtn;
    }

    

}
