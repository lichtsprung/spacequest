package de.fhk.spacequest.simulation;

/**
 * Interface aller Flugkörper innerhalb der Simulation, die keine Himmelskörper sind.
 *
 * @author Robert Giacinto
 */
public interface FlyingObject {
  public double getMass();

  public double getPayload();

  public double getOrientation();

  public double getPosition();

  public double getMassBurningRate();
}
