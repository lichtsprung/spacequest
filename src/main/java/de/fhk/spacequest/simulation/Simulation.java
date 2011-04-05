package de.fhk.spacequest.simulation;


import org.apache.commons.math.ode.FirstOrderDifferentialEquations;

/**
 * Diese Klasse übernimmt die Berechnungen der Mondlandesimulation.
 *
 * @author Robert Giacinto
 */
public abstract class Simulation {
  private Universe universe;
  private FirstOrderDifferentialEquations ode;

  public Simulation(Universe universe) {
    this.universe = universe;
  }

  private void init() {
    
  }
}
