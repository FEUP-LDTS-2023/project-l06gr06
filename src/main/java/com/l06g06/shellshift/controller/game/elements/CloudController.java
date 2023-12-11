package com.l06g06.shellshift.controller.game.elements;

import com.l06g06.shellshift.Game;
import com.l06g06.shellshift.controller.game.GameController;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.elements.Cloud;
import com.l06g06.shellshift.model.game.elements.Coin;
import com.l06g06.shellshift.model.game.elements.Position;
import com.l06g06.shellshift.model.game.map.Map;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CloudController extends GameController {
    private double spawnCooldown = 3; // Spawn every 3 seconds
    private double shiftCooldown = 0.1; // Shift every 0.1 seconds
    private double lastSpawnTime = 0;
    private double lastShiftTime = 0;

    private boolean delayBackground = false;

    public CloudController(Map map) {
        super(map);
    }

    @Override
    public void step(Game game, List<Gui.PressedKey> action, long time) throws IOException {
        double currentTime = time / 1000.0; // Convert to seconds

        if (currentTime - lastShiftTime >= shiftCooldown){
            lastShiftTime = currentTime;
            left_shift();
        }


    }

    public void left_shift(){
        if (!delayBackground) delayBackground = true;
        else {
            delayBackground = false;
            List<Cloud> clouds = getModel().getClouds();
            Iterator<Cloud> cloudIterator = clouds.iterator();
            while (cloudIterator.hasNext()) {
                Cloud cloud = cloudIterator.next();
                if (cloud.getPosition().getX() < -10) {
                    cloudIterator.remove();
                } else cloud.setPosition(new Position(cloud.getPosition().getX() - 1, cloud.getPosition().getY()));
            }
        }


    }
}