package de.fhk.spacequest.controlphases;

import de.fhk.spacequest.simulation.RocketControl;

/**
 * Diese Klasse wird von allen Phasen der Raketensteuerung erweitert. Sie beinhaltet
 * die jeweilige Steuerkomponente, die eine Flugphase der Rakete übernimmt.
 *
 * @author Robert Giacinto
 */
public abstract class ControlPhase {

    private int phase;
    private RocketControl rocketControl;
    private PhaseInfo phaseInfo;

    public ControlPhase(int phase, PhaseInfo phaseInfo) {
        this.phaseInfo = phaseInfo;
        this.phase = phase;
    }

    /**
     * Gibt die Phasennummer dieser Kontrollphase zurück.
     *
     * @return die Phasennummer
     */
    public int getPhase() {
        return phase;
    }

    public RocketControl getRocketControl() {
        return rocketControl;
    }

    public void setRocketControl(RocketControl rocketControl) {
        this.rocketControl = rocketControl;
    }

    /**
     * Heavy-Side Methode.
     *
     * @param val zu evaluierender Wert
     * @return 1, wenn val > 0, sonst 0
     */
    protected double heavy(double val) {
        return (val <= 0) ? 0 : 1;
    }

    /**
     * Aktiviert die Kontrollphase der Raketensteuerung.
     * Diese Methode implementiert das Verhalten der jeweiligen Phase.
     */
    public abstract void control();

    /**
     * Überprüft die Randbedingungen einer Phase und gibt die jeweilige Phase zurück,
     * die auf die Randbedingung zutrifft.
     *
     * @return die Nummer der Kontrollphase, die als nächstes ausgeführt werden soll
     */
    public abstract int checkConditions();

    public PhaseInfo getPhaseInfo() {
        return phaseInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ControlPhase other = (ControlPhase) obj;
        if (this.phase != other.phase) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.phase;
        hash = 89 * hash + (this.rocketControl != null ? this.rocketControl.hashCode() : 0);
        return hash;
    }
}
