package game.core.interfaces;

import game.core.models.Enemy;
import game.core.states.boss.BossState;

public abstract class BossContext implements AnimatedBehaviour, ContextualMovement {
  public abstract void changeState(BossState newState);
  public abstract Enemy getEnemy();
}