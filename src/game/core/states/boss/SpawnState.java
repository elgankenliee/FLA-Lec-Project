package game.core.states.boss;

import game.core.interfaces.BossContext;
import game.core.models.Enemy;
import game.managers.EnemyManager;

public class SpawnState implements BossState {

  @Override
  public void start(BossContext context) {
    context.setAnimation(0);
  }

  @Override
  public void update(BossContext context) {
    if (context.getAnimationCycleCount() > 0) {
      context.changeState(new IdleState());
    }
  }

  @Override
  public void exit(BossContext context) {
  }
}