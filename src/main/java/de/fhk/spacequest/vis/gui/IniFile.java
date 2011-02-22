package de.fhk.spacequest.vis.gui;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Alexander
 */
public class IniFile {

    Properties p;
    String defaultIni = "/data/default.ini";
    String active = null, inactive = null, clockBG = null, clock = null, infopanel = null, text_bgd = null, slider = null, ufo = null, earth = null, moon = null, starfield = null;

    public IniFile() {
        p = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream(defaultIni);
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
     * clockBG=data/digitalClock.png
     * clock=data/clock.png
     *
     * [button]
     * active=data/Button_active.png
     * inactive=data/Button_inactive.png
     * infopanel=data/infopanel.png
     * text_bgd=data/text_bgd.png
     * slider=data/Button_slider.png
     *
     * [view]
     * ufo =/data/ufo.png
     * earth=/data/earth.png
     * moon=/data/moon.png
     * starfield=data/starfield.gif
     */
}
