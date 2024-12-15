package game.core.states.boss;

import java.util.Random;

import game.core.constants.BossStateEnum;
import game.core.interfaces.CharacterContext;


public class IdleState implements BossState {

  private final int targetCycles;
  private final int mode;

  public IdleState() {
    Random rand =  new Random();
    this.targetCycles = rand.nextInt(2); 
    this.mode = rand.nextInt(2);
  }

  @Override
  public void start(CharacterContext context) {
    context.setAnimation(BossStateEnum.IDLE);
    context.setInvincible(false);
  }

  @Override
  public void update(CharacterContext context) {
    if(mode == 0) {
      context.changeState(new IdleDespawnState());
    }
    else if(mode == 1) {
      if (context.getAnimationCycleCount() >= targetCycles) {
        context.changeState(new SplitSwordState());
      }
    }
  }

  @Override
  public void exit(CharacterContext context) {
  }
}