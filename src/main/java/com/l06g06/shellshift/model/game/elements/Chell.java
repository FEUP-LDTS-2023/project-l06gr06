package com.l06g06.shellshift.model.game.elements;

import com.l06g06.shellshift.model.game.gun.Gun;

public class Chell extends Element {
    private Gun gun;
    private int lives;
    public Chell(Position position) {
        super(position);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void decreaseLives() {
        this.lives--;
    }

    public void increaseLives() {
        this.lives++;
    }

    public Gun getGun() {
        return this.gun;
    }
    public void setGun(Gun gun) {
        this.gun = gun;
    }
}
