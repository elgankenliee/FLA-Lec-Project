package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.CharacterContext;

public class AttackPreDashState implements BossState{

  @Override
  public void start(CharacterContext context) {
    context.setAnimation(BossStateEnum.ATTACK | BossStateEnum.PRE_DASH);
    context.setInvincible(false);
  }

  @Override
  public void update(CharacterContext context) {
    context.addForce(2 * (context.getDirection() * -1), Vector.X);
    if(context.getAnimationCycleCount() > 0 ) {
      context.changeState(new AttackDashState());
    }
  }

  @Override
  public void exit(CharacterContext context) {
    // TODO Auto-generated method stub
    
  }

}
