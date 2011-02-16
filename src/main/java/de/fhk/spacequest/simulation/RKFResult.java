package de.fhk.spacequest.simulation;

/**
 * Diese Klasse dient als Ergebnisvektor, in den die Ergebnisse der Runga-Kutte-Integration
 * gespeichert und zur weiteren Benutzung weitergereicht werden können.
 *
 * @author Robert Giacinto
 */
public class RKFResult {

    /**
     * Die Ergebnisse der Runge-Kutta-Integration zweiten Grades.
     */
    private ResultVector y2;
    /**
     * Die Ergebnisse der Runge-Kutta-Integration dritten Grades.
     */
    private ResultVector y3;

    /**
     * Erstellt einen neuen Ergebnisvektor.
     *
     * @param y2 die Ergebnisse zweiten Grades
     * @param y3 die Ergebnisse dritten Grades
     */
    public RKFResult(ResultVector y2, ResultVector y3) {
        this.y2 = y2;
        this.y3 = y3;
    }

    /**
     * Gibt die Ergebnisse zweiten Grades zurück.
     *
     * @return die Ergebnisse zweiten Grades
     */
    public ResultVector getY2() {
        return y2;
    }

    /**
     * Ändert die Ergebnisse zweiten Grades.
     *
     * @param y2 die neuen Ergebnisse zweiten Grades.
     */
    public void setY2(ResultVector y2) {
        this.y2 = y2;
    }

    /**
     * Gibt die Ergebnisse dritten Grades zurück.
     *
     * @return die Ergebnisse dritten Grades
     */
    public ResultVector getY3() {
        return y3;
    }

    /**
     * Ändert die Ergebnisse dritten Grades.
     *
     * @param y3W die neuen Ergebnisse dritten Grades.
     */
    public void setY3(ResultVector y3) {
        this.y3 = y3;
    }
}
