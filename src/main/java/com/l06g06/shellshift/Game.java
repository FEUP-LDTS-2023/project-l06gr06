package com.l06g06.shellshift;

import com.l06g06.shellshift.model.mainmenu.MainMenu;
import com.l06g06.shellshift.gui.LanternaGUI;
import com.l06g06.shellshift.states.MainMenuState;
import com.l06g06.shellshift.states.State;

import com.l06g06.shellshift.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private State state;
    private LanternaGUI gui;

    public Game() throws IOException, URISyntaxException, FontFormatException {
         this.gui = new LanternaGUI(160, 100);
         this.state = new MainMenuState(new MainMenu());
    }

    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException {
        new Game().start();

    }

    public void setState(State state) {
        this.state = state;
    }

    private void start() throws IOException {
        int FPS = 60;
        int frameTime = 1000 / FPS;

        while (this.state != null) {

            long startTime = System.currentTimeMillis();

            state.step(this, gui);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0) Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }

        gui.close();
    }
}
