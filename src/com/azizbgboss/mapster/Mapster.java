package com.azizbgboss.mapster;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

public class Mapster extends MIDlet {
    private Display display;
    private GameCanvas gameCanvas;

    public void startApp() {
        display = Display.getDisplay(this);
        gameCanvas = new GameCanvas(this);
        display.setCurrent(gameCanvas);
        gameCanvas.start();
    }

    public void pauseApp() {
        gameCanvas.stop();
    }

    public void destroyApp(boolean unconditional) {
        gameCanvas.stop();
        notifyDestroyed();
    }
}