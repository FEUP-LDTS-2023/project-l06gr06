package com.l06g06.shellshift.controller.game.elements;

import com.l06g06.shellshift.Game;
import com.l06g06.shellshift.controller.game.GameController;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.elements.Bullet;
import com.l06g06.shellshift.model.game.elements.Position;
import com.l06g06.shellshift.model.game.elements.enemies.Enemy;
import com.l06g06.shellshift.model.game.map.Map;

import java.util.Iterator;
import java.util.List;


public class BulletController extends GameController {

    long reloadStartTime = 0;
    public BulletController(Map map) {
        super(map);
    }

    @Override
    public void step(Game game, List<Gui.PressedKey> action, long time) {
        for (Gui.PressedKey gpk : action) {
            switch (gpk) {
                case FIRE:
                    fire(time);
                    break;
            }
        }
        bulletUpdate();
        bulletCollision();
    }

    private void fire(long time) {
        if (time - reloadStartTime >= getModel().getGun().getReloadTime() && getModel().getGun().getNumBullets() > 0){
            int x = getModel().getChell().getPosition().getX();
            int y = getModel().getChell().getPosition().getY();
            Bullet bullet = new Bullet(new Position(getModel().getChell().isDirection() ? x : x - 16, y - 5));
            bullet.setDirection(getModel().getChell().isDirection());
            getModel().addBullet(bullet);

            getModel().getGun().decreaseNumBullet();
            reloadStartTime = time;
        }
    }

    public void bulletUpdate(){
        for (Bullet bullet : getModel().getBullets()){
            int x = bullet.getPosition().getX();
            int y = bullet.getPosition().getY();

            bullet.setPosition(new Position(bullet.isDirection() ? x + 2 : x - 2, y));
        }
    }

    public void bulletCollision() {
        List<Bullet> bullets = getModel().getBullets();
        List<Enemy> enemies = getModel().getEnemies();

        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();

            Iterator<Bullet> bulletIterator = bullets.iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                if (bullet.getPolygon().intersects(enemy.getPolygon().getBounds2D())) {
                    bulletIterator.remove();
                    enemy.decreaseHP(bullet.getDamage());
                    if (enemy.getHP() <= 0) {
                        enemyIterator.remove();
                        getModel().setScore(getModel().getScore() + enemy.getScore());
                    }
                }
            }
        }
    }

}
