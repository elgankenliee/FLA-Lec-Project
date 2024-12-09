package game.controllers;

import java.util.HashMap;
import java.util.Map;

import game.core.animations.IAnimation;

public class AnimationController {


	private final Map<Integer, IAnimation> animations; 
    private IAnimation currentAnimation;

    public AnimationController() {
      this.animations = new HashMap<>();
    }

    public void addAnimation(int state, IAnimation animation) {
      animations.put(state, animation);
    }

    public void setCurrentAnimation(int state) {
      currentAnimation = animations.get(state);
    }

    public IAnimation getCurrentAnimation() {
      return currentAnimation;
    }

    public void update(long currentTime) {
      if (currentAnimation != null) {
          currentAnimation.update(currentTime);
      }
    }
}
