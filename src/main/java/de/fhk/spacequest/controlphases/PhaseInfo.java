package de.fhk.spacequest.controlphases;

/**
 * Speichert die Informationen, die während der Simulation angezeigt werden können.
 * @author Robert Giacinto
 */
public class PhaseInfo {

    private String header;
    private String description;

    public PhaseInfo(String header, String description) {
        this.header = header;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
