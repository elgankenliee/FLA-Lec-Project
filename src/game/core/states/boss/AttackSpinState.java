package game.core.states.boss;

import game.controllers.AttackHandler;
import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.BossContext;

public class AttackSpinState implements BossState{

  @Override
  public void start(BossContext context) {
    context.setAnimation(BossStateEnum.ATTACK | BossStateEnum.SPIN);
  }

  @Override
  public void update(BossContext context) {
    
    AttackHandler.attack(1, 0, 1);
    
    if(context.getAnimationCycleCount() > 1) {
      context.changeState(new IdleDespawnState());
    }
  }

  @Override
  public void exit(BossContext context) {
    
  }

}
