package game.core.states.boss;

import game.core.interfaces.BossContext;


public class IdleState implements BossState {

  @Override
  public void start(BossContext context) {
    context.setAnimation(3);
  }

  @Override
  public void update(BossContext context) {

  }

  @Override
  public void exit(BossContext context) {
  }
}