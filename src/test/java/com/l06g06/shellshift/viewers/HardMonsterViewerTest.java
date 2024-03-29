package com.l06g06.shellshift.viewers;

import com.l06g06.shellshift.Components;
import com.l06g06.shellshift.Database;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.elements.Position;
import com.l06g06.shellshift.model.game.elements.enemies.HardMonster;
import com.l06g06.shellshift.model.game.elements.enemies.moveStrategies.MoveStrategy;
import com.l06g06.shellshift.viewer.game.HardMonsterViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class HardMonsterViewerTest {
    private HardMonster hardMonster;
    private MoveStrategy moveStrategy;
    private HardMonsterViewer hardMonsterViewer;
    private Gui gui;

    @BeforeEach
    void setUp(){
        Database.getInstance().setSound(true);
        this.moveStrategy = mock(MoveStrategy.class);
        this.hardMonster = new HardMonster(new Position(10, 10), moveStrategy);
        hardMonsterViewer = new HardMonsterViewer();
        gui = mock(Gui.class);
    }

    @Test
    void drawHardMonsterTest(){
        hardMonsterViewer.draw(hardMonster, gui);
        Mockito.verify(gui, Mockito.times(1)).drawImageASCII(eq(Components.HardMonster.getImage()), eq(hardMonster.getPosition()));
        verify(gui, times(hardMonster.getHP()/25)).drawImageASCII(eq(Components.HealthBar.getImage()), any(Position.class));
    }
}

