package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.BossContext;

public class AttackPreDash implements BossState{

  @Override
  public void start(BossContext context) {
    context.setAnimation(BossStateEnum.ATTACK | BossStateEnum.PRE_DASH);
  }

  @Override
  public void update(BossContext context) {
    context.addForce(2 * (context.getDirection() * -1), Vector.X);
    if(context.getAnimationCycleCount() > 0 ) {
      context.changeState(new AttackDashState());
    }
  }

  @Override
  public void exit(BossContext context) {
    // TODO Auto-generated method stub
    
  }

}
