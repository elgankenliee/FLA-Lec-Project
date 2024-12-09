package game.core.animations;

import javafx.scene.image.Image;

public interface IAnimation {

	public void update(long currentTime);
	public int getCurrentFrame();
	public Image getSpriteImage();
}
