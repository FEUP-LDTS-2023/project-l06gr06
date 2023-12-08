package com.l06g06.shellshift.model.creators;

import com.l06g06.shellshift.model.game.elements.Element;
import com.l06g06.shellshift.model.game.elements.Platform;
import com.l06g06.shellshift.model.game.elements.Position;
import com.l06g06.shellshift.model.game.elements.enemies.Enemy;
import com.l06g06.shellshift.model.game.elements.enemies.HardMonster;
import com.l06g06.shellshift.model.game.elements.enemies.SoftMonster;
import com.l06g06.shellshift.model.game.elements.enemies.moveStrategies.HorizontalMoveStrategy;

import java.util.Random;

public class EnemyCreator extends Creator{
    public Element create(Position position) {
        Random rn = new Random();
        int maximum = 50;
        int minimum = 30;
        int n = maximum - minimum + 1;
        int i = rn.nextInt() % n;
        int new_y = minimum + i;

        Random rn_enemy = new Random();
        int i2 = rn_enemy.nextInt(2);
        if (i2 == 0) return new HardMonster(new Position(position.getX(), new_y), new HorizontalMoveStrategy());
        return new SoftMonster(new Position(position.getX(), new_y), new HorizontalMoveStrategy());
    }

}
