package game.core.animations.player;

import java.io.File;

import game.core.animations.IAnimation;
import javafx.scene.image.Image;

public class PlayerAnimation implements IAnimation{
    public static final int FRAME_WIDTH = 60;
    public static final int FRAME_HEIGHT = 60;

    private final String spritePath;
    private final Image spriteImage;
    private final int spriteColumns;
    private final long frameDuration;
    
    private int idleFrame = 0;
    private long lastFrameTime = 0;

    public PlayerAnimation(String spritePath, int spriteColumns, long frameDuration) {
        this.spritePath = spritePath;
        this.spriteImage = new Image(new File(spritePath).toURI().toString());
        this.spriteColumns = spriteColumns;
        this.frameDuration = frameDuration;
    }

    @Override
    public void update(long currentTime) {
        if (currentTime - lastFrameTime >= this.frameDuration) {
            idleFrame = (idleFrame + 1) % this.spriteColumns;
            lastFrameTime = currentTime;
        }
    }

    @Override
    public int getCurrentFrame() {
        return idleFrame;
    }

    @Override
    public Image getSpriteImage() {
        return spriteImage;
    }
}