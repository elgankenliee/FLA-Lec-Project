package game.core.states.boss;

import java.util.Random;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.BossContext;

public class SplitSwordState implements BossState{

  private int shouldDefendChance;
  @Override
  public void start(BossContext context) {
    Random rand = new Random();
    this.shouldDefendChance = rand.nextInt(5) + 1;
    context.setAnimation(BossStateEnum.IDLE | BossStateEnum.SPLIT_SWORD);
  }

  @Override
  public void update(BossContext context) {
    
    if(context.getAnimationCycleCount() > 0) {   
      if(shouldDefendChance < 2) context.changeState(new AttackSpinState());
      else context.changeState(new AttackPreDashState());
    }
    
  }

  @Override
  public void exit(BossContext context) {
    // TODO Auto-generated method stub
    
  }

}
