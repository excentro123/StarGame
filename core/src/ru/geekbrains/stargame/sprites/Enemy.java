package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Ship;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class Enemy extends Ship {



  public Enemy(BulletPool bulletPool,
               ExplosionPool explosionPool,
               Rect worldBounds) {
    this.bulletPool    = bulletPool;
    this.worldBounds   = worldBounds;
    this.explosionPool = explosionPool;

    v       = new Vector2();
    v0      = new Vector2();
    bulletV = new Vector2();
  }

  @Override
  public void update(final float delta) {
    super.update(delta);
    if (getBottom() <= worldBounds.getBottom()) {
      destroy();
    }
  }

  public void set(
      TextureRegion[] regions,
      Vector2 v0,
      TextureRegion[] bulletRegion,
      float bulletHeight,
      float bulletVY,
      int damage,
      float reloadInterval,
      Sound shootSound,
      int hp,
      float height) {
    this.regions = regions;
    this.v0.set(v0);
    this.bulletRegions = bulletRegion;
    this.bulletHeight  = bulletHeight;
    this.bulletV.set(0, bulletVY);
    this.damage         = damage;
    this.reloadInterval = reloadInterval;
    this.reloadTimer    = reloadInterval;
    this.shootSound     = shootSound;
    this.hp             = hp;
    this.v.set(v0);
    setHeightProportion(height);
  }

  public boolean isBulletCollision(final Rect bullet) {
    return !(bullet.getRight() < getLeft()
             || bullet.getLeft() > getRight()
             || bullet.getBottom() > getTop()
             || bullet.getTop() < position.y);
  }
}
