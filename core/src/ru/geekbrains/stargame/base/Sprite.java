package ru.geekbrains.stargame.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.utils.Regions;

public class Sprite extends Rect {

  protected TextureRegion[] regions;

  protected float angle;
  protected int   frame;

  protected float   scale     = 1f;
  private   boolean destroyed = false;

  public Sprite(TextureRegion region) throws GameException {
    if (region == null) throw new GameException("Region is null");
    regions    = new TextureRegion[1];
    regions[0] = region;
  }

  public Sprite() {}

  public Sprite(TextureRegion region,
                int rows,
                int cols,
                int frames) throws GameException {
    if (region == null) {
      throw new GameException("Region is null");
    }
    this.regions = Regions.split(region, rows, cols, frames);
  }

  public void setHeightProportion(float height) {
    setHeight(height);
    float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
    setWidth(height * aspect);
  }

  public void update(float delta) {}

  public void draw(SpriteBatch batch) {
    batch.draw(
        regions[frame],
        getLeft(),
        getBottom(),
        halfWidth,
        halfHeight,
        getWidth(),
        getHeight(),
        scale,
        scale,
        angle);
  }

  public void resize(Rect worldBounds) {}

  public boolean touchDown(Vector2 touch,
                           int pointer,
                           int button) {
    return false;
  }

  public boolean touchUp(Vector2 touch,
                         int pointer,
                         int button) {
    return false;
  }

  public boolean touchDragged(Vector2 touch,
                              int pointer) {
    return false;
  }

  public float getAngle() {
    return angle;
  }

  public void setAngle(float angle) {
    this.angle = angle;
  }

  public float getScale() {
    return scale;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  public void flushDestroy() {
    destroyed = false;
  }

  public void destroy() {
    destroyed = true;
  }

}
