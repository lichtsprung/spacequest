package de.fhk.spacequest;

import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math.ode.FirstOrderIntegrator;
import org.apache.commons.math.ode.IntegratorException;
import org.apache.commons.math.ode.nonstiff.*;

/**
 * Mainklasse der Visualisierung
 */
public class SpacequestMain {

  public static void main(String[] args) {
    FirstOrderDifferentialEquations test = new FirstOrderDifferentialEquations() {
      @Override
      public int getDimension() {
        return 1;
      }

      @Override
      public void computeDerivatives(double t, double[] y, double[] yDot) throws DerivativeException {
        yDot[0] = 1;
      }
    };

    FirstOrderIntegrator integrator = new AdamsMoultonIntegrator(1,1E-10,0.5,0.1,0.05);
    double[] y = new double[1];
    y[0] = 0;
    try {
      integrator.integrate(test, 0.0, y, 4, y);
      System.out.println(y[0]);
    } catch (DerivativeException e) {
      System.out.println(e);
    } catch (IntegratorException e) {
      System.out.println(e);
    }
  }

}
