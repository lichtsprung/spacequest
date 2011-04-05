package de.fhk.spacequest.simulation.maths;

import de.fhk.spacequest.simulation.Universe;
import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderDifferentialEquations;


public class NBodyODE implements FirstOrderDifferentialEquations {
  private Universe universe;

  public NBodyODE(Universe universe) {
    this.universe = universe;
  }

  @Override
  public int getDimension() {
    return 3;
  }

  @Override
  public void computeDerivatives(double t, double[] y, double[] yDot) throws DerivativeException {
    
  }
}
