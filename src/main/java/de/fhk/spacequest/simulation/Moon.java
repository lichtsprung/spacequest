package de.fhk.spacequest.simulation;

/**
 * Diese Klasse repräsentiert den Mond innerhalb der Simulation.
 *
 * @author Robert Giacinto
 */
public class Moon extends CBody {

    /**
     * Die Masse des Monds in kg
     */
    public static final double M = 0.73E23;
    /**
     * Der Radius des Monds in m
     */
    public static final double R = 1.738E6;


    @Override
    protected Object clone() {
        Moon moon = new Moon();
        moon.setR(getR());
        return moon;
    }

    @Override
    public String toString() {
        String output = "(Moon): \n"
                + "\tMass: " + M + "\n"
                + "\tRadius: " + R + "\n"
                + "\tOrtsvektor: " + getR();
        return output;
    }
}
