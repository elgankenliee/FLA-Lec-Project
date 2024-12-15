package game.core.states.boss;

import java.util.Random;

import game.core.constants.BossStateEnum;
import game.core.interfaces.CharacterContext;

public class IdleDespawnState implements BossState {

	private double nextSpawnPositionX;
	private final double minDisplacement = 100f;

	@Override
	public void start(CharacterContext context) {
	  context.setInvincible(true);
		Random rand = new Random();
		double currentX = context.getPos().getX();

		double maxDLeft = currentX - 300 - minDisplacement;
		double maxDRight = 1300 - currentX - minDisplacement;

		boolean canGoLeft = maxDLeft >= 0;
		boolean canGoRight = maxDRight >= 0;

		double newX = currentX;
		double factor = (double) (rand.nextInt(10 - 5) + 5) / 10.0f;

		if (canGoLeft) {
			double displacement = factor * maxDLeft;
			newX = currentX - (minDisplacement + displacement);
		} else if (canGoRight) {
			double displacement = factor * maxDRight;
			newX = currentX + (minDisplacement + displacement);
		}

		nextSpawnPositionX = newX;

		if (!(canGoLeft || canGoRight)) {
			context.changeState(new AttackPreDashState());
		}
		context.setAnimation(BossStateEnum.IDLE | BossStateEnum.DESPAWN);
		context.setSound(BossStateEnum.DESPAWN);
	}

	@Override
	public void update(CharacterContext context) {
		if (context.getAnimationCycleCount() > 0) {
			context.getPos().setX(nextSpawnPositionX);
			context.changeState(new SpawnState());
		}
	}

	@Override
	public void exit(CharacterContext context) {
	}
}