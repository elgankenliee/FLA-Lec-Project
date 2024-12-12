package game.core.states.boss;

import game.core.constants.Vector;
import game.core.interfaces.BossContext;

public class LevitateSpawnState implements BossState {

  private final double initialBoost = 0.1f;
  @Override
  public void start(BossContext context) {
    context.setAnimation(2);
    
  }

  @Override
  public void update(BossContext context) {
    if (context.getAnimationCycleCount() > 0) {
      context.changeState(new LevitateState());
    }
    else {
      context.addForce((2 + initialBoost) * Vector.UP, Vector.Y);
    }
    
  }

  @Override
  public void exit(BossContext context) {
    // TODO Auto-generated method stub
    
  }


}
