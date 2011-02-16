package de.fhk.spacequest.simulation;

/**
 * Die Klasse repräsentiert die Erde innerhalb der Simulation.
 *
 * @author Robert Giacinto
 */
public class Earth extends CBody {

    /**
     * Die Masse der Erde in kg.
     */
    public static final double M = 5.98E24;
    /**
     * Der Radius der Erde in m.
     */
    public static final double R = 6.378E6;

    @Override
    protected Object clone() {
        Earth earth = new Earth();
        earth.setR(getR());
        return earth;
    }

    @Override
    public String toString() {
        String output = "(Earth): \n"
                + "\tMass: " + M + "\n"
                + "\tRadius: " + R + "\n"
                + "\tOrtsvektor: " + getR();
        return output;
    }
}
