package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprites.Enemy;

public class EnemyEmitter {
  private static final float ENEMY_SMALL_HEIGHT          = 0.08f;
  private static final float ENEMY_SMALL_BULLET_HEIGHT   = 0.01f;
  private static final float ENEMY_SMALL_BULLET_VY       = -0.3f;
  private static final int   ENEMY_SMALL_DAMAGE          = 1;
  private static final float ENEMY_SMALL_RELOAD_INTERVAL = 1f;
  private static final int   ENEMY_SMALL_HP              = 5;

  private static final float ENEMY_MEDIUM_HEIGHT          = 0.1f;
  private static final float ENEMY_MEDIUM_BULLET_HEIGHT   = 0.02f;
  private static final float ENEMY_MEDIUM_BULLET_VY       = -0.25f;
  private static final int   ENEMY_MEDIUM_DAMAGE          = 5;
  private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 2f;
  private static final int   ENEMY_MEDIUM_HP              = 10;

  private static final float ENEMY_BIG_HEIGHT          = 0.12f;
  private static final float ENEMY_BIG_BULLET_HEIGHT   = 0.04f;
  private static final float ENEMY_BIG_BULLET_VY       = -0.3f;
  private static final int   ENEMY_BIG_DAMAGE          = 10;
  private static final float ENEMY_BIG_RELOAD_INTERVAL = 3f;
  private static final int   ENEMY_BIG_HP              = 15;

  private final Vector2   enemySmallV;
  private final Vector2   enemyMediumV;
  private final Vector2   enemyBigV;
  private final EnemyPool enemyPool;

  private final TextureRegion[] enemySmallRegion;
  private final TextureRegion[] enemyMediumRegion;
  private final TextureRegion[] enemyBigRegion;
  private final Rect            worldBounds;
  private final Sound           shootSound;
  private final TextureRegion[] bulletRegions;

  private float generateTimer;
  private int   level = 1;

  public EnemyEmitter(TextureAtlas atlas,
                      EnemyPool enemyPool,
                      Rect worldBounds,
                      Sound shootSound) {
    this.worldBounds   = worldBounds;
    this.shootSound    = shootSound;
    this.enemyPool     = enemyPool;
    this.bulletRegions = new TextureRegion[]{atlas.findRegion("laserRed14"), atlas.findRegion("laserRed10")};
    TextureRegion enemy0 = atlas.findRegion("enemyBlack1");
    this.enemySmallRegion = Regions.split(enemy0, 1, 1, 1);
    TextureRegion enemy1 = atlas.findRegion("enemyBlue2");
    this.enemyMediumRegion = Regions.split(enemy1, 1, 1, 1);
    TextureRegion enemy2 = atlas.findRegion("enemyGreen3");
    this.enemyBigRegion = Regions.split(enemy2, 1, 1, 1);
    this.enemySmallV    = new Vector2(0, -0.2f);
    this.enemyMediumV   = new Vector2(0, -0.1f);
    this.enemyBigV      = new Vector2(0, -0.05f);
  }

  public int getLevel() {
    return level;
  }

  public void generate(float delta,
                       int frags) {
    level = frags / 10 + 1;
    generateTimer += delta;
    final float generateInterval = 1f;
    if (generateTimer >= generateInterval) {
      generateTimer = 0f;
      Enemy enemy = enemyPool.obtain();
      float type = (float) Math.random();
      if (type < 0.6f) {
        enemy.set(
            enemySmallRegion,
            enemySmallV,
            bulletRegions,
            ENEMY_SMALL_BULLET_HEIGHT,
            ENEMY_SMALL_BULLET_VY,
            ENEMY_SMALL_DAMAGE * level,
            ENEMY_SMALL_RELOAD_INTERVAL,
            shootSound,
            ENEMY_SMALL_HP,
            ENEMY_SMALL_HEIGHT);
      } else if (type < 0.85f) {
        enemy.set(
            enemyMediumRegion,
            enemyMediumV,
            bulletRegions,
            ENEMY_MEDIUM_BULLET_HEIGHT,
            ENEMY_MEDIUM_BULLET_VY,
            ENEMY_MEDIUM_DAMAGE * level,
            ENEMY_MEDIUM_RELOAD_INTERVAL,
            shootSound,
            ENEMY_MEDIUM_HP,
            ENEMY_MEDIUM_HEIGHT);
      } else {
        enemy.set(
            enemyBigRegion,
            enemyBigV,
            bulletRegions,
            ENEMY_BIG_BULLET_HEIGHT,
            ENEMY_BIG_BULLET_VY,
            ENEMY_BIG_DAMAGE * level,
            ENEMY_BIG_RELOAD_INTERVAL,
            shootSound,
            ENEMY_BIG_HP,
            ENEMY_BIG_HEIGHT);
      }
      enemy.position.x =
          Rnd.nextFloat(
              worldBounds.getLeft() + enemy.getHalfWidth(),
              worldBounds.getRight() - enemy.getHalfWidth());
      enemy.setBottom(worldBounds.getTop());
    }
  }
}
