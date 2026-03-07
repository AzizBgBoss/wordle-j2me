package com.azizbgboss.wordle;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

public class Wordle extends MIDlet {
    private Display display;
    private WordleGame game;

    public void startApp() {
        display = Display.getDisplay(this);
        game = new WordleGame(this);
        display.setCurrent(game.getCanvas());
    }

    public void pauseApp() {}

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }

    public void quit() {
        notifyDestroyed();
    }

    public Display getDisplay() {
        return display;
    }
}