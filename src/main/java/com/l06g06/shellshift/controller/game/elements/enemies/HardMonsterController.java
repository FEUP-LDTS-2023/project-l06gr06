package com.l06g06.shellshift.controller.game.elements.enemies;

import com.l06g06.shellshift.Game;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.map.Map;

import java.util.List;


public class HardMonsterController extends EnemyController{
/*    double spawnCooldown = 3; // Spawn every 3 seconds
    double shiftCooldown = 0.1; // Shift every 0.1 seconds
    double lastSpawnTime = 0;
    double lastShiftTime = 0;*/
    public HardMonsterController(Map map) {
        super(map);
    }

    @Override
    public void step(Game game, List<Gui.PressedKey> action, long time) {

        /*double currentTime = time / 1000.0; // Convert to seconds

        // Spawn coin logic
        if (currentTime - lastSpawnTime >= spawnCooldown){
            lastSpawnTime = currentTime;
            //getModel().getHardMonsterSpawner().spawn(new Position(110, 50));
        }

        // Shift coin logic
        if (currentTime - lastShiftTime >= shiftCooldown){
            lastShiftTime = currentTime;
            left_shift();
        }
*/
    }

/*    public void left_shift(){
        for (Enemy enemy : getModel().getEnemies()){
            if (enemy instanceof HardMonster) {
                int x = enemy.getPosition().getX();
                int y = enemy.getPosition().getY();
                enemy.setPosition(new Position(x - 1, y));
            }
        }
    }*/
}
