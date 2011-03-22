package de.fhk.spacequest.vis.gui;

import de.fhk.spacequest.vis.Spacequest;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * GUI der Visualisierung.
 * Dies ist die Klasse, die alles verwaltet, was mit der grafischen Oberfläche zu tun hat.
 *
 * @author Robert Giacinto
 */
public class GUI extends AbstractGUI {

    private Spacequest spacequest;
    protected IniFile iniFile;

    public GUI(Spacequest spacequest, Input input, IniFile iniFile) throws SlickException {
        super(input);
        this.spacequest = spacequest;
        this.iniFile = iniFile;
        initGUI();
    }

    public Spacequest getSpacequest() {
        return spacequest;
    }

    /**
     * Initialisiert die Standardkomponenten der GUI.
     *
     * @throws SlickException
     */
    private void initGUI() throws SlickException {
        PropertyFile p = new PropertyFile();

        try {
            for (GUIComponent gc : p.createGUIElements("/data/default.xml", this)) {
                addCompoment(gc);
            }
        } catch (Exception error) {

        }
    }
}
