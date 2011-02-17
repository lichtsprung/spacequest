package de.fhk.spacequest.vis.gui;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Alexander
 */
public class IniFile {

    Properties p;
    String defaultIni = "default.ini";
    String active, inactive, clockBG, clock, infopanel, text_bgd, slider, ufo, earth, moon, starfield;

    public IniFile() {
        p = new Properties();
        try {
            InputStream in = IniFile.class.getResourceAsStream(defaultIni);
            p.load(in);
            clockBG = p.getProperty("clockBG");
            clock = p.getProperty("clock");

            active = p.getProperty("active");
            inactive = p.getProperty("inactive");
            infopanel = p.getProperty("infopanel");
            text_bgd = p.getProperty("text_bgd");
            slider = p.getProperty("slider");

            ufo = p.getProperty("ufo");
            earth = p.getProperty("earth");
            moon = p.getProperty("moon");
            starfield = p.getProperty("starfield");

            p.list(System.out);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String get(int file) {
        //TODO Es sollte die vorläufige Integer Variable durch ein String ersetzt werden, sobald ab dem 28.07.11 die neue JDK7 erlaubt Strings in Switch-Abfragen zu verwenden
        switch (file) {
            case (1):
                return clockBG;
            case (2):
                return clockBG;

            case (3):
                return active;
            case (4):
                return inactive;
            case (5):
                return infopanel;
            case (6):
                return text_bgd;
            case (7):
                return slider;

            case (8):
                return ufo;
            case (9):
                return earth;
            case (10):
                return moon;
            case (11):
                return starfield;
        }

        return ("error");
    }
    /**
     *
     * Beispiel INI-File:
     * [clock]
     * clockBG=pics/digitalClock.png
     * clock=pics/clock.png
     *
     * [button]
     * active=pics/Button_active.png
     * inactive=pics/Button_inactive.png
     * infopanel=pics/infopanel.png
     * text_bgd=pics/text_bgd.png
     * slider=pics/Button_slider.png
     *
     * [view]
     * ufo =/pics/ufo.png
     * earth=/pics/earth.png
     * moon=/pics/moon.png
     * starfield=pics/starfield.gif
     */
}
