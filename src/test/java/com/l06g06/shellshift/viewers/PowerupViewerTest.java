package com.l06g06.shellshift.viewers;

import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.elements.Position;
import com.l06g06.shellshift.model.game.elements.PowerUp;
import com.l06g06.shellshift.viewer.game.PowerupViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PowerupViewerTest {
    private PowerUp powerup;
    private PowerupViewer viewer;
    private Gui gui;

    @BeforeEach
    void setUp(){
        powerup = new PowerUp(new Position(10, 10));
        viewer = new PowerupViewer();
        gui = Mockito.mock(Gui.class);
    }

    @Test
    void drawPowerup(){
        viewer.draw(powerup, gui);
        Mockito.verify(gui, Mockito.times(1)).drawPowerup(powerup.getPosition());
    }
}