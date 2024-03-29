package com.l06g06.shellshift.controller.game.elements;

import com.google.common.annotations.VisibleForTesting;
import com.l06g06.shellshift.Game;
import com.l06g06.shellshift.Sound;
import com.l06g06.shellshift.SoundsFx;
import com.l06g06.shellshift.controller.game.GameController;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.elements.Platform;
import com.l06g06.shellshift.model.game.elements.Position;
import com.l06g06.shellshift.model.game.elements.powerups.PowerUp;
import com.l06g06.shellshift.model.game.map.Map;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PowerUpController extends GameController {

    private double lastSpawnTime = 0;
    private double lastShiftTime = 0;
    private final Random random;

    public PowerUpController(Map map) {
        super(map);
        this.random = new Random();
    }

    @Override
    public void step(Game game, List<Gui.PressedKey> action, long time) throws IOException {

        double currentTime = time / 1000.0; // Convert to seconds

        // Spawn power up logic
        if (currentTime - lastSpawnTime >= getModel().getSpawnCooldown() + 10) {
            lastSpawnTime = currentTime;
            spawnOnPlatform();
        }

        // Shift power up logic
        if (currentTime - lastShiftTime >= getModel().getShiftCooldown()) {
            lastShiftTime = currentTime;
            left_shift();
        }

        powerUpCollision();

    }

    public void left_shift() {
        for (PowerUp powerUp : getModel().getPowerUps()) {
            int x = powerUp.getPosition().getX();
            int y = powerUp.getPosition().getY();
            powerUp.setPosition(new Position(x - 1, y));
        }
    }

    public void spawnOnPlatform() {
        List<Platform> platforms = getModel().getPlatforms();

        Platform randomPlatform;
        int i = 0;
        do {
            randomPlatform = platforms.get(random.nextInt(platforms.size()));
            i++;
        } while (randomPlatform.getPosition().getX() < 200 && i < 30);

        if (i >= 30) {
            return;
        }

        int minY = 200;
        for (int y : randomPlatform.getPolygon().ypoints) {
            if (y < minY) minY = y;
        }

        int offsetX = -random.nextInt(65);

        Position powerUpPosition = new Position(randomPlatform.getPosition().getX() + offsetX, minY - 15);
        getModel().getPowerUpSpawner().spawn(powerUpPosition);
    }

    public void powerUpCollision() {
        List<PowerUp> powerUps = getModel().getPowerUps();
        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            if (getModel().getChell().getPolygon().intersects(powerUp.getPolygon().getBounds2D())) {
                powerUp.activate(getModel());
                powerUpIterator.remove();
                Sound sound = Sound.getInstance();
                sound.playSound(SoundsFx.PowerUP);
            }
        }
    }

    @VisibleForTesting
    public void setLastShiftTime(double lastShiftTime) {
        this.lastShiftTime = lastShiftTime;
    }

    @VisibleForTesting
    public void setLastSpawnTime(double lastSpawnTime) {
        this.lastSpawnTime = lastSpawnTime;
    }
}
