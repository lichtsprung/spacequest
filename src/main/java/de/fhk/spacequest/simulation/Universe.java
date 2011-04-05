package de.fhk.spacequest.simulation;

import org.apache.commons.math.ode.FirstOrderDifferentialEquations;

import java.util.Collection;

/**
 * Universe Interface, das von allen möglichen Universen implementiert werden muss, um in
 * der Simulation verwendet werden zu können.
 *
 * @author Robert Giacinto
 */
public interface Universe{
  public void addCelestialBody(CelestialBody celestialBody);

  public Collection<CelestialBody> getAllCelestialBodies();

  public void addFlyingObject(FlyingObject flyingObject);

  public Collection<FlyingObject> getAllFlyingObjects();

  public FirstOrderDifferentialEquations getODE();
}
