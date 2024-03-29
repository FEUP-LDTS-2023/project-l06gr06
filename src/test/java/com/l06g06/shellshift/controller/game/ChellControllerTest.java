package com.l06g06.shellshift.controller.game;

import com.l06g06.shellshift.Database;
import com.l06g06.shellshift.Game;
import com.l06g06.shellshift.controller.game.elements.ChellController;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.elements.Chell;
import com.l06g06.shellshift.model.game.elements.Platform;
import com.l06g06.shellshift.model.game.elements.Position;
import com.l06g06.shellshift.model.game.map.Map;
import com.l06g06.shellshift.model.game.spawners.PlatformSpawner;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.LongRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChellControllerTest {
    private Chell chell;
    private Map mockedMap ;
    private ChellController chellController;

    @BeforeEach
    public void setup(){
        Database.getInstance().setSound(true);
        this.chell = new Chell(new Position(0, 0));
        this.mockedMap = mock(Map.class);
        when(mockedMap.getChell()).thenReturn(chell);
        this.chellController = new ChellController(mockedMap);
    }

    @Test
    public void moveRightTest(){
        Chell chell = new Chell(new Position(0, 0));
        chell.setDirection(false);
        Mockito.when(mockedMap.getChell()).thenReturn(chell);
        ChellController chellController = new ChellController(mockedMap);
        chellController.moveRIGHT();

        Position expected = new Position(1, 0);
        assertEquals(expected, chell.getPosition());
        assertTrue(chell.isDirection());

        chell.setPosition(new Position(159, 0));
        chellController.moveRIGHT();
        Assertions.assertEquals(new Position(160, 0), chell.getPosition());

        chell.setPosition(new Position(160, 0));
        chellController.moveRIGHT();
        Assertions.assertEquals(new Position(160, 0), chell.getPosition());

        chell.setPosition(new Position(161, 0));
        chellController.moveRIGHT();
        Assertions.assertEquals(new Position(161, 0), chell.getPosition());
    }

    @Test
    public void moveLeftTest(){
        Chell chell = new Chell(new Position(0, 0));
        Assertions.assertTrue(chell.isDirection());
        Mockito.when(mockedMap.getChell()).thenReturn(chell);
        ChellController chellController = new ChellController(mockedMap);
        chellController.moveLEFT();

        Position expected = new Position(-1, 0);
        assertEquals(expected, chell.getPosition());
        assertFalse(chell.isDirection());
    }

    @Property
    public void jumpTest(@ForAll int time){
        Database.getInstance().setSound(true);
        this.chell = new Chell(new Position(0, 0));
        this.mockedMap = mock(Map.class);
        when(mockedMap.getChell()).thenReturn(chell);
        this.chellController = new ChellController(mockedMap);
        chellController.setCanJump(true);

        assertTrue(chellController.isCanJump());
        chellController.jump(time);
        assertTrue(chellController.isJumping());
        assertEquals(time, chellController.getJumpStartTime());
        assertFalse(chellController.isCanJump());
        assertEquals(chell.getPosition().getY(), chellController.getGroundY());
    }

    @Property
    void jumpUpdateTest(@ForAll @LongRange(min = 1, max = 665) long time)  {
        Database.getInstance().setSound(true);
        Map map = new Map();
        Chell chell = map.getChell();
        int initialX = chell.getPosition().getX();
        int initialY = chell.getPosition().getY();
        double velocity = chell.getVelocity();
        double gravity = chell.getGravity();
        long jumpStartTime = System.currentTimeMillis();
        time = jumpStartTime + time;
        chellController = new ChellController(map);
        chellController.setJumpStartTime(jumpStartTime);

        chellController.jumpUpdate(time);

        Assertions.assertEquals(initialX, chell.getPosition().getX());
        double elapsedTime = (time - jumpStartTime) / 1000.0;
        int expectedY = (int) (initialY - (velocity * elapsedTime - 0.5 * gravity * elapsedTime * elapsedTime));
        Assertions.assertEquals(expectedY, chell.getPosition().getY());
    }

    @Test
    void leftShiftTest(){
        chell.setPosition(new Position(20, 20));
        chellController.left_shift();
        assertEquals(new Position(19, 20), chell.getPosition());

        chellController.left_shift();
        chellController.left_shift();
        assertEquals(new Position(17, 20), chell.getPosition());
    }

    @Test
    void lookForPlatformCollisionTest(){
        Map map = new Map();
        List<Platform> platforms = new ArrayList<>(0);
        Platform platform = new Platform(new Position(0, 0));
        platforms.add(platform);
        map.setPlatforms(platforms);    // Map is initialized with one platform

        Chell chell = new Chell(new Position(-11, 16));
        map.setChell(chell);


        chellController = new ChellController(map);

        int groundY = (int) platform.getPolygon().getBounds().getMinY();

        chellController.lookForPlatformCollision();
        assertEquals(groundY - 2, map.getChell().getPosition().getY());
        Assertions.assertFalse(chellController.isJumping());
    }

    @Test
    void stepTest(){
        Game game = mock(Game.class);
        long time = System.currentTimeMillis();
        List<Gui.PressedKey> actions = List.of(Gui.PressedKey.UP);
        ChellController chellControllerSpy = spy(chellController);
        chellControllerSpy.setJumping(true);
        chellControllerSpy.setCanJump(true);
        chellControllerSpy.step(game, actions, time);
        verify(chellControllerSpy).jumpUpdate(anyLong());
    }

    @Test
    void stepShiftConditionTest(){
        Game game = mock(Game.class);
        List<Gui.PressedKey> actions = new ArrayList<>();
        Position chellPosition = new Position(3, 1);
        Chell chell = new Chell(chellPosition);
        Mockito.when(mockedMap.getChell()).thenReturn(chell);
        PlatformSpawner platformSpawner = mock(PlatformSpawner.class);
        when(mockedMap.getPlatformSpawner()).thenReturn(platformSpawner);
        long time1 = 79;
        long time2 = 80;
        long time3 = 81;
        chellController.setLastShiftTime(0);
        Mockito.when(mockedMap.getShiftCooldown()).thenReturn(0.08);

        chellController.step(game, actions, time1);
        Assertions.assertEquals(0, chellController.getLastShiftTime());
        chellController.step(game, actions, time2);
        Assertions.assertEquals(0.08, chellController.getLastShiftTime());
        ChellController chellControllerSpy = spy(chellController);
        chellControllerSpy.setLastShiftTime(0);
        chellControllerSpy.step(game, actions, time2);
        verify(chellControllerSpy, times(1)).left_shift();
        chellController.step(game, actions, time3);
        Assertions.assertEquals(0.08, chellController.getLastShiftTime());
    }

    @Test
    void inputTest(){
        Game game = mock(Game.class);
        long time = System.currentTimeMillis();

        List<Gui.PressedKey> actions = List.of(Gui.PressedKey.UP);
        chellController.setJumping(false);
        chellController.setCanJump(true);
        chellController.setGroundY(20);
        chellController.step(game, actions, time);
        Assertions.assertEquals(20, chellController.getGroundY());
        Assertions.assertFalse(chellController.isCanJump());

        actions = List.of(Gui.PressedKey.LEFT);
        chell.setPosition(new Position(40, 40));
        chellController.step(game, actions, time);
        Assertions.assertEquals(40 - 1 - 1, chell.getPosition().getX()); // Chell moves and shifts to the left

        actions = List.of(Gui.PressedKey.RIGHT);
        chell.setPosition(new Position(40, 40));
        chellController.step(game, actions, time);
        Assertions.assertEquals(40 + 1 - 1, chell.getPosition().getX()); // Chell moves to the right and shifts to the left
    }

    @Test
    void checkLandingTest(){
        Map map = new Map();
        chell = new Chell(new Position(20, 300));
        chell.setVelocity(9960);
        chell.setGravity(8765);
        map.setChell(chell);
        map.setPlatforms(new ArrayList<>());
        chellController = new ChellController(map);
        chellController.setJumping(false);
        chellController.setCanJump(true);

        chellController.checkLanding();

        assertFalse(chellController.isCanJump());
        Assertions.assertEquals(new Position(20, 395), map.getChell().getPosition());

        ChellController chellControllerSpy = spy(chellController);  // This is to kill a mutation that removes the call to lookForPlatformCollision()
        chellControllerSpy.checkLanding();
        verify(chellControllerSpy, times(1)).lookForPlatformCollision();
    }

}
