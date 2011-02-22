package de.fhk.spacequest.vis;

import de.fhk.spacequest.controlphases.ControlPhase;
import de.fhk.spacequest.simulation.Constants;
import de.fhk.spacequest.simulation.ResultVector;
import de.fhk.spacequest.simulation.Simulation;
import de.fhk.spacequest.vis.gui.GUI;
import de.fhk.spacequest.vis.gui.IniFile;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Die Hauptklasse der Visualisierung.
 * Hier wird die Simulation gestartet und die Aktualisierung der Simulation
 * getriggert.
 *
 * @author Robert Giacinto
 */
public class Spacequest extends BasicGame {

    /**
     * Die Standardgröße des Visualisierungsfensters.
     */
    public static final int PWIDTH = 800, PHEIGHT = 400;
    /**
     * Das Input-Objekt, das die Maus- und Keyboardeingaben verarbeitet.
     */
    private Input input = null;
    /**
     * Die Instanz der laufenden Simulation.
     */
    private Simulation simulation = null;
    /**
     * Die View der Erde.
     */
    private EarthView earth = null;
    /**
     * Die View der Rakete.
     */
    private RocketView rocket = null;
    /**
     * Die View des Monds.
     */
    private MoonView moon = null;
    /**
     * Das Kameraobjekt der Visualisierung.
     */
    private Camera cam = null;
    /**
     * Der aktuelle Zustand der Simulation.
     */
    private ResultVector currentState = null;
    /**
     * Der Hintergrund der Simulation.
     */
    private Image starfield = null;
    /**
     * Die Bahnspuren von Erde, Mond und Rakete.
     */
    private Polygon earthTrail = null, moonTrail = null, rocketTrail = null;
    /**
     * <code>true</code>, wenn Rakete in der Nähe vom Mond.
     */
    private boolean moonPhase = false;
    private boolean useCamPresets = false;
    private GUI gui = null;
    IniFile iniFile;
    private int screenWidth, screenHeight;

    /**
     * Erstellt eine neue Instanz der Visualisierung.
     */
    public Spacequest() {
        this(PWIDTH, PHEIGHT);
    }

    public Spacequest(int width, int height) {
        super("Spacequest");
        iniFile = new IniFile();
        screenWidth = width;
        screenHeight = height;

        try {
            AppGameContainer vis = new AppGameContainer(this);
            vis.setDisplayMode(width, height, false);
            vis.setVSync(true);
            vis.setShowFPS(false);
            vis.start();
        } catch (SlickException ex) {
            Logger.getLogger(Spacequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        input = container.getInput();

        simulation = new Simulation((double) (10 * 1000), (double) (300 * 1000), false, false);
        simulation.updateSimulationState();
        gui = new GUI(this, input, iniFile);

        cam = new Camera((float) (-Constants.LE), 0.0F, screenWidth, screenHeight, 2.6053895E-5f, container.getGraphics());
        cam.loadPreset(Camera.presetEarth);

        currentState = simulation.getCurrentSimulationState();

        earthTrail = new Polygon();
        earthTrail.setClosed(false);
        earthTrail.addPoint((float) currentState.getEarth().getR().x, (float) currentState.getEarth().getR().y);

        moonTrail = new Polygon();
        moonTrail.setClosed(false);
        moonTrail.addPoint((float) currentState.getMoon().getR().x, (float) currentState.getMoon().getR().y);

        rocketTrail = new Polygon();
        rocketTrail.setClosed(false);
        rocketTrail.addPoint((float) currentState.getRocket().getR().x, (float) currentState.getRocket().getR().y);

        earth = new EarthView(currentState.getEarth(), iniFile.get(9));
        rocket = new RocketView(currentState.getRocket(), iniFile.get(8));
        moon = new MoonView(currentState.getMoon(), iniFile.get(10));
        starfield = new Image(getClass().getResourceAsStream(iniFile.get(11)), "stars", false).getScaledCopy(screenWidth, screenHeight);
        starfield.setAlpha(0.3f);
        updateTextureScaling();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        simulation.updateSimulationState();
        currentState = simulation.getCurrentSimulationState();

        earth.setEarthModel(currentState.getEarth());
        rocket.setRocketModel(currentState.getRocket());
        moon.setMoonModel(currentState.getMoon());

        gui.updateComponents();


        //TODO Das ist hässlich, die Presetüberpüfungen müssen nochmal neu gemacht werden.
        if (useCamPresets) {
            if (simulation.getAuxVars().getBrre() < 1.5707643944434617E7 && cam.getCurrentPreset() != Camera.presetEarth) {
                cam.loadPreset(Camera.presetEarth);
                updateTextureScaling();
            } else if (simulation.getAuxVars().getBrre() >= 1.5707643944434617E7
                    && simulation.getAuxVars().getBrrm() > 3.015359653786095E7
                    && cam.getCurrentPreset() != Camera.presetEarthMoon) {
                cam.loadPreset(Camera.presetEarthMoon);
                updateTextureScaling();
            } else if (simulation.getAuxVars().getBrrm() < 1.5051861793844106E7 && cam.getCurrentPreset() != Camera.presetMoon) {
                cam.loadPreset(Camera.presetMoon);
                updateTextureScaling();
                moonPhase = true;
            }
        } else {
            if (input.isKeyDown(Input.KEY_DOWN)) {
                cam.y += 10.0f / cam.zoom;
            }
            if (input.isKeyDown(Input.KEY_UP)) {
                cam.y -= 10.0f / cam.zoom;
            }

            if (input.isKeyDown(Input.KEY_RIGHT)) {
                cam.x += 10.0f / cam.zoom;
            }
            if (input.isKeyDown(Input.KEY_LEFT)) {
                cam.x -= 10.0f / cam.zoom;
            }

            if (input.isKeyDown(Input.KEY_1)) {
                cam.zoom *= 0.99f;
                rescaleTextures();
            }
            if (input.isKeyDown(Input.KEY_2)) {
                cam.zoom /= 0.99f;
                rescaleTextures();
            }
        }

        //TODO muss inaktiv sein, wenn User manuelle Kontrolle über Keyboard benutzt
        if (moonPhase) {
            cam.x = (float) currentState.getMoon().getR().x;
            cam.y = (float) currentState.getMoon().getR().y;
        }


        earthTrail.addPoint((float) currentState.getEarth().getR().x, (float) currentState.getEarth().getR().y);
        rocketTrail.addPoint((float) currentState.getRocket().getR().x, (float) currentState.getRocket().getR().y);
        moonTrail.addPoint((float) currentState.getMoon().getR().x, (float) currentState.getMoon().getR().y);


        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            container.exit();
        }
    }

    private void updateTextureScaling() {
        earth.updateScaledTexture(cam);
        moon.updateScaledTexture(cam);
        rocket.updateScaledTexture(cam);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        if (!gui.hasFocus()) {
            cam.x += ((float) (newx - oldx) / cam.zoom) * -1.0F;
            cam.y += ((float) (newy - oldy) / cam.zoom) * -1.0F;
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
    }

    /**
     * Zeichnet die Visualisierung der Simulation-
     *
     * @param container der Container, in dem die Simulation abläuft
     * @param g         das Grafikobjekt
     * @throws SlickException wirft Exception, wenn etwas nicht funktionieren sollte
     */
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setLineWidth(2.0F);
        g.setAntiAlias(true);
        g.drawImage(starfield, 0.0F, 0.0F);

        g.setColor(Color.blue);
        g.draw(cam.getScreenPolygon(earthTrail));

        earth.draw(cam);

        g.setColor(Color.yellow);
        g.draw(cam.getScreenPolygon(rocketTrail));

        rocket.draw(cam);

        g.setColor(Color.gray);
        g.draw(cam.getScreenPolygon(moonTrail));

        moon.draw(cam);

        gui.draw(g);
    }

    public void addPhase(ControlPhase newPhase) {
        simulation.addControlPhase(newPhase);
    }

    public void rescaleTextures() {
        updateTextureScaling();
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    public boolean isUseCamPresets() {
        return useCamPresets;
    }

    public void setUseCamPresets(boolean useCamPresets) {
        this.useCamPresets = useCamPresets;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
