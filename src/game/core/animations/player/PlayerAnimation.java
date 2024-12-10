package game.core.animations.player;


import java.io.File;

import game.core.animations.IAnimation;
import javafx.scene.image.Image;

public class PlayerAnimation implements IAnimation{
  private final int DEFAULT_FRAME_WIDTH = 60;
  private final int DEFAULT_FRAME_HEIGHT = 60;

  
  private final String spritePath;
  private final Image spriteImage;
  private final int spriteColumns;
  private final long frameDuration;
  private final int cropWidth;
  private final int cropHeight;
  private int cyclesCompleted;;
  
  private int currentFrame;
  private long lastFrameTime;
  
  private int transitionEase;

  public PlayerAnimation(
      String spritePath,
      int spriteColumns,
      long frameDuration
  ) {
    this.spritePath = spritePath;
    this.spriteImage = new Image(new File(spritePath).toURI().toString());
    this.spriteColumns = spriteColumns;
    this.frameDuration = frameDuration;
    this.cropWidth = DEFAULT_FRAME_WIDTH;
    this.cropHeight = DEFAULT_FRAME_HEIGHT;
    this.cyclesCompleted = 0;
    this.currentFrame = 0;
    this.lastFrameTime = 0;
    this.transitionEase = (int) ((int)this.spriteColumns * 0.8);
  }
  
  public PlayerAnimation(
      String spritePath,
      int spriteColumns,
      long frameDuration,
      int cropWidth,
      int cropHeight
  ) {
    this.spritePath = spritePath;
    this.spriteImage = new Image(new File(spritePath).toURI().toString());
    this.spriteColumns = spriteColumns;
    this.frameDuration = frameDuration;
    this.cropWidth = cropWidth;
    this.cropHeight = cropHeight;
  }

  @Override
  public void start() {
    this.cyclesCompleted = 0;
    
  }
  
  @Override
  public void update(long currentTime) {
      if (currentTime - lastFrameTime >= this.frameDuration) {
          currentFrame++;
//          if(currentFrame >= this.transitionEase) {
//            cyclesCompleted++;
//          }
          if (currentFrame >= this.spriteColumns) {
              currentFrame = 0;
              cyclesCompleted++;
          }
          lastFrameTime = currentTime;
      }
  }

  @Override
  public int getCurrentFrame() {
    return this.currentFrame;
  }

  @Override
  public Image getSpriteImage() {
    return this.spriteImage;
  }

  @Override
  public int getCropWidth() {
    return this.cropWidth;
  }

  @Override
  public int getCropHeight() {
    return this.cropHeight;
  }
  
  @Deprecated
  @Override
  public int getCyclesCompleted() {
    return cyclesCompleted;
  }

  
}
