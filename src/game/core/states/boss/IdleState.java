package game.core.states.boss;

import java.util.Random;

import game.core.constants.BossStateEnum;
import game.core.interfaces.BossContext;


public class IdleState implements BossState {

  private final int targetCycles;
  private final boolean mode;

  public IdleState() {
    Random rand =  new Random();
    this.targetCycles = rand.nextInt(2); 
    this.mode = rand.nextBoolean();
  }

  @Override
  public void start(BossContext context) {
    context.setAnimation(BossStateEnum.IDLE);
  }

  @Override
  public void update(BossContext context) {
    if(mode) {
      context.changeState(new IdleDespawnState());
      return;
    }
    if (context.getAnimationCycleCount() >= targetCycles) {
      context.changeState(new SplitSwordState());
    }
  }

  @Override
  public void exit(BossContext context) {
  }
}