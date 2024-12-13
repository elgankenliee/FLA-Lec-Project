package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.interfaces.BossContext;

public class SpawnState implements BossState {

	@Override
	public void start(BossContext context) {
		context.setAnimation(BossStateEnum.SPAWN);
	}

	@Override
	public void update(BossContext context) {
		if (context.getAnimationCycleCount() > 0) {
			context.changeState(new IdleState());
		}
	}

	@Override
	public void exit(BossContext context) {
	}
}