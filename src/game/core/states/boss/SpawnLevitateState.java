package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.BossContext;

public class SpawnLevitateState implements BossState {

	@Override
	public void start(BossContext context) {
		context.setAnimation(BossStateEnum.SPAWN | BossStateEnum.LEVITATE);
		context.setSound(BossStateEnum.SPAWN | BossStateEnum.LEVITATE);
	}

	@Override
	public void update(BossContext context) {
		if (context.getAnimationCycleCount() > 0) {
			context.changeState(new LevitateState());
		} else {
			context.addForce(2 * Vector.UP, Vector.Y);
		}

	}

	@Override
	public void exit(BossContext context) {
		// TODO Auto-generated method stub

	}

}
