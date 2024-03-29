package com.l06g06.shellshift.model.game.elements;

import com.l06g06.shellshift.Database;
import com.l06g06.shellshift.model.game.gun.FireStrategy;
import com.l06g06.shellshift.model.game.gun.Gun;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ChellTest {

    private Chell chell;
    private Position mockpos;

    @BeforeEach
    void setup(){
        Database.getInstance().setSound(true);
        mockpos = Mockito.mock(Position.class);
        Mockito.when(mockpos.getX()).thenReturn(5);
        Mockito.when(mockpos.getY()).thenReturn(5);
        chell = new Chell(mockpos);
    }

    @Test
    void set_get_gun(){

        class mockFireStrategy implements FireStrategy {
            @Override
            public double getReloadTime() {
                return 0;
            }
        }

        Gun gun = new Gun(new mockFireStrategy());
        chell.setGun(gun);
        Assertions.assertEquals(gun,chell.getGun());
    }

    @Test
    void get_width_height(){
        Assertions.assertEquals(15, Chell.getWidth());
        Assertions.assertEquals(15, Chell.getHeight());
    }

    @Property
    void settersGettersVelocityTest(@ForAll int velocity){
        Database.getInstance().setSound(true);
        mockpos = Mockito.mock(Position.class);
        chell = new Chell(mockpos);
        chell.setVelocity(velocity);
        Assertions.assertEquals(velocity, chell.getVelocity());
    }

    @Property
    void settersGettersGravityTest(@ForAll int gravity) {
        Database.getInstance().setSound(true);
        mockpos = Mockito.mock(Position.class);
        chell = new Chell(mockpos);
        chell.setGravity(gravity);
        Assertions.assertEquals(gravity, chell.getGravity());
    }

    @Property
    void settersGettersDirectionTest(@ForAll boolean direction) {
        Database.getInstance().setSound(true);
        mockpos = Mockito.mock(Position.class);
        chell = new Chell(mockpos);
        chell.setDirection(direction);
        Assertions.assertEquals(direction, chell.isDirection());
    }

    @Property
    void chell_lives(@ForAll int lives) {
        Database.getInstance().setSound(true);
        mockpos = Mockito.mock(Position.class);
        Mockito.when(mockpos.getX()).thenReturn(5);
        Mockito.when(mockpos.getY()).thenReturn(5);
        chell = new Chell(mockpos);

        // Get and set lives.
        chell.setLives(lives);
        Assertions.assertEquals(lives, chell.getLives());

        // Test for increaseLives() method.
        chell.setLives(lives);
        chell.increaseLives();
        Assertions.assertEquals(lives + 1, chell.getLives());

        // Test for decreaseLives() method.
        chell.setLives(lives);
        chell.decreaseLives();
        Assertions.assertEquals(lives - 1, chell.getLives());
    }

    @Test
    public void hitboxTest(){
        chell.setPosition(new Position(0, 0));
        Assertions.assertEquals(-14, chell.getPolygon().xpoints[0]);
        Assertions.assertEquals(0, chell.getPolygon().xpoints[1]);
        Assertions.assertEquals(-14, chell.getPolygon().xpoints[2]);
        Assertions.assertEquals(0, chell.getPolygon().xpoints[3]);

        Assertions.assertEquals(-14, chell.getPolygon().ypoints[0]);
        Assertions.assertEquals(-14, chell.getPolygon().ypoints[1]);
        Assertions.assertEquals(1, chell.getPolygon().ypoints[2]);
        Assertions.assertEquals(1, chell.getPolygon().ypoints[3]);
    }

    @Test
    public void invencibilityTimerTest(){
        chell.setInvincibilityEndTime(100);
        Assertions.assertTrue(chell.isInvincible());
        long currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime <= 1200);
        Assertions.assertFalse(chell.isInvincible());
    }

    @Test
    public void blink() {
        Assertions.assertFalse(chell.getBlink());
        chell.toggleBlink();
        Assertions.assertTrue(chell.getBlink());
        chell.toggleBlink();
        Assertions.assertFalse(chell.getBlink());
    }

    @Test
    public void getHorizontalSpeedTest(){
        Assertions.assertEquals(1, chell.getActualHorizontalSpeed());
        Assertions.assertEquals(1, chell.getHorizontalSpeed());
    }

}
