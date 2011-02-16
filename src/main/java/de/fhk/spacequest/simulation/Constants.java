package de.fhk.spacequest.simulation;

/**
 *
 * @author Robert Giacinto
 */
public interface Constants {

    /**
     * Abstand Erde - Mond
     */
    double D = 0.384E9;
    /**
     * Gravitationskonstante
     */
    double G = 6.673E-11;
    /**
     * Abstand Schwerpunkt - Erde
     */
    double LE = Moon.M / (Earth.M + Moon.M) * D;
    /**
     * Abstand Schwerpunkt - Mond
     */
    double LM = Earth.M / (Earth.M + Moon.M) * D;
    /**
     * Rotationsgeschwindigkeit Erde - Mond
     */
    double PHI10V = (Math.PI * 2) / (27.3 * 24 * 3600);
    /**
     * Erdrotationsgeschwindigkeit
     */
    double PHI20V = (Math.PI * 2) / (24 * 3600);
    double PHI20 = .25 * Math.PI;
    /**
     * Startwinkel Erde - Mond
     */
    double PHI10 = 0;

    /*
     * Start: Konstanten für RK
     */
    double ALPHA1 = 0.25;
    double ALPHA2 = 27.0 / 40.0;
    double ALPHA3 = 1.0;
    double BETA10 = 0.25;
    double BETA20 = -189.0 / 800;
    double BETA30 = 214.0 / 891.0;
    double BETA21 = 739.0 / 800.0;
    double BETA31 = 1.0 / 33.0;
    double BETA32 = 650.0 / 891.0;
    double C0 = 214.0 / 891.0;
    double C1 = 1.0 / 33.0;
    double C2 = 650.0 / 891.0;
    double D0 = 533.0 / 2106.0;
    double D1 = 0.0;
    double D2 = 800.0 / 1053.0;
    double D3 = -1.0 / 78.0;
    /*
     * Ende: Konstanten für RK
     */
    /**
     * Epsilon
     */
    double D_EPS = 1E-7;
    /**
     * Standardwert für die maximale Zeitschrittweite
     */
    double DT_MAX = 600;
    /**
     * Zeitschrittstartwert
     */
    double DT_START = 1E-8;
    /**
     * Standardnutzlast der Rakete
     */
    double D_MN = 8000;
    /**
     * Standardtreibstoffmenge
     */
    double D_MT0 = 15 * D_MN;
    /**
     * Standarderdorbit
     */
    double D_R_ORBIT = Earth.R + 500000;
    /**
     * Abbrandrate
     */
    double D_W = 6000;
    /**
     * Startwinkel der Rakete
     */
    double START_ANGLE = .96 * Math.PI;
    double EARTH_ORBIT = 1.02;
}
