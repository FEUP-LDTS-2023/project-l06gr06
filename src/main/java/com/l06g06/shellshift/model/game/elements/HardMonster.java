package com.l06g06.shellshift.model.game.elements;

public class HardMonster extends Enemy {

    public HardMonster(Position position) {
        super(position, 100);

    }

    @Override
    public void update() {
        HP += 50;
    }
}
