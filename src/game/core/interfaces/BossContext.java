package game.core.interfaces;

import game.core.models.Enemy;
import game.core.states.boss.BossState;

public interface BossContext extends AnimatedBehaviour, VectorMotion {
  public abstract void changeState(BossState newState);
  public abstract Enemy getEnemy();
}