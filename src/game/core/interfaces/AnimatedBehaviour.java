package game.core.interfaces;

import game.core.animations.IAnimation;

public interface AnimatedBehaviour {
  public IAnimation getCurrentAnimation();
  public int getAnimationCycleCount();
  public void setAnimation(int animationId);
}
