package de.fhk.spacequest;

import de.fhk.spacequest.vis.Spacequest;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mainklasse der Visualisierung
 *
 * @author Robert Giacinto
 */
public class SpacequestMain {

    public static void main(String[] args) {

        Logger.getLogger(SpacequestMain.class.getName()).log(Level.INFO, "Loading dependencies...");

        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");


        if (os.equalsIgnoreCase("Linux")) {
            File f = new File("lib/natives/linux");
            System.setProperty("org.lwjgl.librarypath", f.getAbsolutePath());
            System.setProperty("net.java.games.input.librarypath", f.getAbsolutePath());
        } else if (os.contains("Win")) {
            File f = new File("lib/natives/windows");
            System.setProperty("org.lwjgl.librarypath", f.getAbsolutePath());
            System.setProperty("net.java.games.input.librarypath", f.getAbsolutePath());
        } else if (os.contains("mac")) {
            File f = new File("lib/natives/macosx");
            System.setProperty("org.lwjgl.librarypath", f.getAbsolutePath());
            System.setProperty("net.java.games.input.librarypath", f.getAbsolutePath());
        }


        Spacequest sq = new Spacequest(1024, 768);
    }

}
