package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.BossContext;

public class LevitateState implements BossState {

	@Override
	public void start(BossContext context) {
		context.setAnimation(BossStateEnum.LEVITATE);
		context.setSound(BossStateEnum.LEVITATE);
	}

	@Override
	public void update(BossContext context) {

		if (context.getAnimationCycleCount() <= 3) {
			context.addForce(2 * Vector.UP, Vector.Y);
		}
		if (context.getAnimationCycleCount() > 3) {
			context.changeState(new IdleState());
		}

	}

	@Override
	public void exit(BossContext context) {
		// Cleanup if needed
	}
}