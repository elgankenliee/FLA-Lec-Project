package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.BossContext;

public class AttackSpinState implements BossState{

  private int dashed;
  @Override
  public void start(BossContext context) {
    this.dashed = 0;
    context.setAnimation(BossStateEnum.ATTACK | BossStateEnum.SPIN);
  }

  @Override
  public void update(BossContext context) {
    int cycle = context.getAnimationCycleCount();
    if((cycle % 4 == 0 || cycle % 7 == 0) && dashed != cycle) {
      context.addForce(50 * context.getDirection(), Vector.X);
      dashed = cycle;
    }
  }

  @Override
  public void exit(BossContext context) {
    // TODO Auto-generated method stub
    
  }

}
