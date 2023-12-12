package com.l06g06.shellshift.controller.game.elements;

import com.l06g06.shellshift.Game;
import com.l06g06.shellshift.controller.game.GameController;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.elements.Coin;
import com.l06g06.shellshift.model.game.elements.Platform;
import com.l06g06.shellshift.model.game.elements.Position;
import com.l06g06.shellshift.model.game.elements.powerups.PowerUp;
import com.l06g06.shellshift.model.game.map.Map;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PowerUpController extends GameController {

    private double spawnCooldown = 15; // Spawn every 3 seconds
    private double shiftCooldown = 0.1; // Shift every 0.1 seconds
    private double lastSpawnTime = 0;
    private double lastShiftTime = 0;
    private Random random;

    public PowerUpController(Map map) {
        super(map);
        this.random = new Random();
    }

    @Override
    public void step(Game game, List<Gui.PressedKey> action, long time) throws IOException {

        double currentTime = time / 1000.0; // Convert to seconds

        // Spawn platforms logic
        if (currentTime - lastSpawnTime >= spawnCooldown){
            lastSpawnTime = currentTime;
            spawnOnPlatform();
        }

        // Shift platforms logic
        if (currentTime - lastShiftTime >= shiftCooldown){
            lastShiftTime = currentTime;
            left_shift();
        }

        powerUpCollision();

    }

    public void left_shift(){
        for (PowerUp powerUp : getModel().getPowerUps()){
            int x = powerUp.getPosition().getX();
            int y = powerUp.getPosition().getY();
            powerUp.setPosition(new Position(x - 1, y));
        }
    }

    public void spawnOnPlatform() {
        List<Platform> platforms = getModel().getPlatforms();

        Platform randomPlatform;
        do {
            randomPlatform = platforms.get(random.nextInt(platforms.size()));
            System.out.println("x: " + randomPlatform.getPosition().getX());
            System.out.println("SEARCHING");
        } while (randomPlatform.getPosition().getX() <= 170);

        System.out.println("FOUND");
        int minY = 200;
        for (int y : randomPlatform.getPolygon().ypoints) {
            if (y < minY) minY = y;
        }

        int offsetX = random.nextInt(-65, 0);

        Position powerUpPosition = new Position(randomPlatform.getPosition().getX() + offsetX, minY - 15);
        getModel().getPowerUpSpawner().spawn(powerUpPosition);
    }

    public void powerUpCollision() {
        List<PowerUp> powerUps = getModel().getPowerUps();
        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while(powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            if (getModel().getChell().getPolygon().intersects(powerUp.getPolygon().getBounds2D())) {
                powerUp.activate(getModel());
                powerUpIterator.remove();
            }
        }
    }

}
