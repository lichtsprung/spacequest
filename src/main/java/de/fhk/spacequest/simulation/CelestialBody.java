package de.fhk.spacequest.simulation;

/**
 * Interface aller Himmelskörper in der Simulation.
 */
public interface CelestialBody {
  public double getMass();

  public double getRadius();

  public double getPosition();

  public double getRotation();
}
