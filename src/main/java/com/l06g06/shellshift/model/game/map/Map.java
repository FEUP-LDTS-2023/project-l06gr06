package com.l06g06.shellshift.model.game.map;

import com.google.common.annotations.VisibleForTesting;
import com.l06g06.shellshift.Database;
import com.l06g06.shellshift.model.game.elements.*;
import com.l06g06.shellshift.model.game.elements.enemies.Enemy;
import com.l06g06.shellshift.model.game.elements.powerups.ActivePowerUp;
import com.l06g06.shellshift.model.game.elements.powerups.PowerUp;
import com.l06g06.shellshift.model.game.gun.Gun;
import com.l06g06.shellshift.model.game.gun.NormalFireStrategy;
import com.l06g06.shellshift.model.game.spawners.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private int score = 0;

    // DEBUG
    private Chell chell = new Chell(new Position(120, 0));
    private Gun gun;
    private List<Enemy> enemies = new ArrayList<>(0);
    private List<Platform> platforms = new ArrayList<>(0);
    private List<Bullet> bullets = new ArrayList<>(0);
    private List<PowerUp> powerUps = new ArrayList<>(0);
    private List<Coin> coins = new ArrayList<>(0);
    private final List<Cloud> clouds = new ArrayList<>(0);

    private final PlatformSpawner platformSpawner;
    private final CoinSpawner coinSpawner;
    private final EnemySpawner enemySpawner;
    private final PowerUpSpawner powerUpSpawner;
    private final CloudSpawner cloudSpawner;
    private final ActivePowerUp activePowerUp;

    private int coinsCollected = 0;
    private int monstersKilled = 0;

    private long gameStartTime;
    private double shiftCooldown = 0.08;
    private int spawnCooldown = 6;

    // Sizes
    public Map() {
        this.gun = new Gun(new NormalFireStrategy());
        this.bullets = new ArrayList<>();

        //plataformas iniciais
        this.platforms.add(new Platform(new Position(140, 55)));
        this.platforms.add(new Platform(new Position(215, 35)));

        this.platformSpawner = new PlatformSpawner(platforms);
        this.coinSpawner = new CoinSpawner(coins);
        this.enemySpawner = new EnemySpawner(enemies);
        this.powerUpSpawner = new PowerUpSpawner(powerUps);
        this.cloudSpawner = new CloudSpawner(clouds);
        this.activePowerUp = new ActivePowerUp();

        Random random = new Random();
        this.clouds.add(new Cloud(new Position(40 + random.nextInt(120), 5 + random.nextInt(60))));
    }


    // Chell
    public Chell getChell() {
        return this.chell;
    }

    public void setChell(Chell chell) {
        this.chell = chell;
    }

    // Gun
    public Gun getGun() {
        return this.gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }

    // Enemies
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    // Platforms
    public List<Platform> getPlatforms() {
        return platforms;
    }

    // Bullets
    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        this.bullets.add(bullet);
    }

    // PowerUps
    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    @VisibleForTesting
    public void setPowerUps(List<PowerUp> ppowerUp) {
        this.powerUps = ppowerUp;
    }

    // Coins
    public List<Coin> getCoins() {
        return coins;
    }

    public PlatformSpawner getPlatformSpawner() {
        return platformSpawner;
    }

    public CoinSpawner getCoinSpawner() {
        return coinSpawner;
    }

    public EnemySpawner getEnemySpawner() {
        return enemySpawner;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if (this.score > Database.getInstance().getMAXSCORE()) this.score = Database.getInstance().getMAXSCORE();
    }

    public void addCoin() {
        this.coinsCollected++;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public int getMonstersKilled() {
        return monstersKilled;
    }

    public void addMonsterKilled() {
        monstersKilled++;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public PowerUpSpawner getPowerUpSpawner() {
        return this.powerUpSpawner;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public double getShiftCooldown() {
        return shiftCooldown;
    }

    public int getSpawnCooldown() {
        return spawnCooldown;
    }

    public void setGameStartTime(long gst) {
        gameStartTime = gst;
    }

    public void setShiftCooldown(double ssc) {
        shiftCooldown = ssc;
    }

    public void setSpawnCooldown(int ssd) {
        spawnCooldown = ssd;
    }

    public ActivePowerUp getActivePowerUp() {
        return activePowerUp;
    }

    @VisibleForTesting
    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    @VisibleForTesting
    public void setCoinsCollected(int coinsCollected) {
        this.coinsCollected = coinsCollected;
    }

    @VisibleForTesting
    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public CloudSpawner getCloudSpawner() {
        return cloudSpawner;
    }
}
