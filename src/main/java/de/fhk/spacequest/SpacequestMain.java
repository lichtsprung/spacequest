package de.fhk.spacequest;

import de.fhk.spacequest.vis.Spacequest;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mainklasse der Visualisierung
 *
 * @author Robert Giacinto
 */
public class SpacequestMain {

    public static void main(String[] args) {
//        System.out.println("java.library.path = " + System.getProperty("java.library.path"));
//        System.out.println("java.ext.dirs = " + System.getProperty("java.ext.dirs"));
//        System.out.println("java.class.path = " + System.getProperty("java.class.path"));
        Logger.getLogger(SpacequestMain.class.getName()).log(Level.INFO, "Version: neue GUI Elemente");
        Spacequest sq = new Spacequest(1024, 768);
//        sq.addPhase(new Phase0());
//        sq.addPhase(new Phase1());
//        sq.addPhase(new Phase2());
//        sq.addPhase(new Phase3());
//        sq.addPhase(new Phase4());
//        sq.addPhase(new Phase5());
//        sq.addPhase(new Phase6());

    }
}
