package game.core.states.boss;

import java.util.Random;

import game.core.constants.BossStateEnum;
import game.core.interfaces.BossContext;


public class IdleState implements BossState {

  private final int targetCycles;

  public IdleState() {
    this.targetCycles = new Random().nextInt(4); 
  }

  @Override
  public void start(BossContext context) {
    context.setAnimation(BossStateEnum.IDLE);
  }

  @Override
  public void update(BossContext context) {
    if (context.getAnimationCycleCount() >= targetCycles) {
      context.changeState(new AttackPreDash());
    }
  }

  @Override
  public void exit(BossContext context) {
  }
}