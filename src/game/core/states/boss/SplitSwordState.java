package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.BossContext;

public class SplitSwordState implements BossState{

  @Override
  public void start(BossContext context) {
    context.setAnimation(BossStateEnum.IDLE | BossStateEnum.SPLIT_SWORD);
  }

  @Override
  public void update(BossContext context) {
    
    if(context.getAnimationCycleCount() > 0) {   
      context.changeState(new AttackPreDashState());
    }
    
  }

  @Override
  public void exit(BossContext context) {
    // TODO Auto-generated method stub
    
  }

}
