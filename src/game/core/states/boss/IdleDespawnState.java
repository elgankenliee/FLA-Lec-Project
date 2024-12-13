package game.core.states.boss;

import java.util.Random;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.BossContext;
import game.core.models.Enemy;
import game.core.models.Vector2D;
import game.managers.EnemyManager;

public class IdleDespawnState implements BossState {
  
  private double nextSpawnPositionX;
  private final double minDisplacement = 100f;

  @Override
  public void start(BossContext context) {
    Random rand = new Random();
    double currentX = context.getEnemy().getPos().getX();

    double maxDLeft = currentX - 300 - minDisplacement;
    double maxDRight = 1300 - currentX - minDisplacement;

    boolean canGoLeft = maxDLeft >= 0;
    boolean canGoRight = maxDRight >= 0;

    double newX = currentX;
    double factor = (double)(rand.nextInt(10 - 5) + 5) / 10.0f;

    if (canGoLeft) {
      double displacement = factor * maxDLeft;
      newX = currentX - (minDisplacement + displacement);
    }
    else if (canGoRight) {
      double displacement = factor * maxDRight;
      newX = currentX + (minDisplacement + displacement);
    }

    nextSpawnPositionX = newX;
    
    if(!(canGoLeft || canGoRight)) {
      context.changeState(new AttackPreDashState());
    }
    context.setAnimation(BossStateEnum.IDLE | BossStateEnum.DESPAWN);
  }

  @Override
  public void update(BossContext context) {
    if (context.getAnimationCycleCount() > 0) {
      context.getEnemy().getPos().setX(nextSpawnPositionX);
      context.changeState(new SpawnState());
    }
  }

  @Override
  public void exit(BossContext context) {
  }
}