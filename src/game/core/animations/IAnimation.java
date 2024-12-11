package game.core.animations;

import javafx.scene.image.Image;

public interface IAnimation {
  public void start();
	public void update(long currentTime);
	public int getCurrentFrame();
	public Image getSpriteImage();
	public int getCropWidth();
	public int getCropHeight();
	
  @Deprecated
	public int getCyclesCompleted();
}
