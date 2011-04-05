package de.fhk.spacequest.simulation;

import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderDifferentialEquations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 * Default Universe.
 *
 * @author Robert Giacinto
 */
public class DefaultUniverse implements Universe {

  private Collection<CelestialBody> celestialBodies;
  private Collection<FlyingObject> flyingObjects;
  private Hashtable<String, Double> constants;


  public DefaultUniverse() {
    celestialBodies = new ArrayList<CelestialBody>();
    flyingObjects = new ArrayList<FlyingObject>();
    constants = new Hashtable<String, Double>(15);
    initUniverse();
  }

  private void initUniverse(){
    constants.put("gravity", 6.673E-11);
    
  }

  @Override
  public void addCelestialBody(CelestialBody celestialBody) {
    celestialBodies.add(celestialBody);
  }

  @Override
  public Collection<CelestialBody> getAllCelestialBodies() {
    return celestialBodies;
  }

  @Override
  public void addFlyingObject(FlyingObject flyingObject) {
    flyingObjects.add(flyingObject);
  }

  @Override
  public Collection<FlyingObject> getAllFlyingObjects() {
    return flyingObjects;
  }

  @Override
  public FirstOrderDifferentialEquations getODE() {
    return null;
  }
}
