package com.l06g06.shellshift.controller.game;

import com.l06g06.shellshift.Database;
import com.l06g06.shellshift.Game;
import com.l06g06.shellshift.controller.game.elements.*;
import com.l06g06.shellshift.controller.game.elements.enemies.EnemyController;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.map.Map;
import com.l06g06.shellshift.model.gameOver.GameOver;
import com.l06g06.shellshift.states.GameOverState;

import java.io.IOException;
import java.util.List;

public class MapController extends GameController{
    private long addedScoreTimer = System.currentTimeMillis();
    private final ChellController chellController;
    private final BulletController bulletController;
    private final PlatformController platformController;
    private final CoinController coinController;
    private final EnemyController enemyController;
    private final CloudController cloudController;
    private final PowerUpController powerUpController;
    private static long gameStartTime;
    private static double shiftCooldown = 0.08;
    private static int spawnCooldown = 5;
    boolean checkpoint1 = false;
    boolean checkpoint2 = false;

    public MapController(Map map){
        super(map);
        this.chellController = new ChellController(map);
        this.bulletController = new BulletController(map);
        this.platformController = new PlatformController(map);
        this.coinController = new CoinController(map);
        this.enemyController = new EnemyController(map);
        this.cloudController = new CloudController(map);
        this.powerUpController = new PowerUpController(map);
        gameStartTime = System.currentTimeMillis();
        this.shiftCooldown = 0.08;
        this.spawnCooldown = 5;
        this.checkpoint1 = false;
        this.checkpoint2 = false;
    }

    public void step(Game game, List<Gui.PressedKey> action, long time) throws IOException {
        enemyController.step(game, action, time);
        bulletController.step(game, action, time);
        chellController.step(game, action, time);
        platformController.step(game, action, time);
        coinController.step(game, action, time);
        cloudController.step(game, action, time);
        powerUpController.step(game, action, time);

        // adiciona 1 ponto a cada segundo
        if (System.currentTimeMillis() - addedScoreTimer >= 1000) {
            setAddedScoreTimer(System.currentTimeMillis());
            getModel().setScore(getModel().getScore() + 1);
        }

        // game over conditions, com out of bounds, border a direita esta no moveRIGHT()
        if (isGameOver()) {
            updateDatabase();
            getModel().stopCloudAddingTask();
            Game.sleepTimeMS(200);
            game.setState((new GameOverState(new GameOver())));
        }

        long elapsedTimeSinceGameStart =  (time - MapController.getGameStartTime()) / 1000;
        updateAcceleration(elapsedTimeSinceGameStart);

    }

    public void updateAcceleration(long elapsedTimeSinceGameStart){
        // Acceleration is divided in 3 levels
        if (!checkpoint1 && elapsedTimeSinceGameStart >= 30){
            checkpoint1 = true;
            shiftCooldown = 0.05;
        } else if (!checkpoint2 && elapsedTimeSinceGameStart >= 120){
            checkpoint2 = true;
            shiftCooldown = 0.03;
            spawnCooldown = 2;
        }
    }

    public boolean isGameOver() {
        return getModel().getChell().getPosition().getY() > 150 | getModel().getChell().getPosition().getX() < 0 | getModel().getChell().getLives() <= 0;
    }

    public void updateDatabase() {
        Database.getInstance().addScore(getModel().getScore());
        Database.getInstance().addCoins(getModel().getCoinsCollected());
        Database.getInstance().addMonstersKilled(getModel().getMonstersKilled());
    }

    public void setAddedScoreTimer(long addedScoreTimer) {
        this.addedScoreTimer = addedScoreTimer;
    }

    public static long getGameStartTime(){
        return gameStartTime;
    }

    public static double getShiftCooldown(){
        return shiftCooldown;
    }

    public static int getSpawnCooldown() {
        return spawnCooldown;
    }

    public boolean isCheckpoint1() {
        return checkpoint1;
    }

    public boolean isCheckpoint2() {
        return checkpoint2;
    }

    public static void setGameStartTime(long gameStartTime) {
        MapController.gameStartTime = gameStartTime;
    }
}
