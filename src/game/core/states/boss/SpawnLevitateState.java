package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.CharacterContext;

public class SpawnLevitateState implements BossState {

	@Override
	public void start(CharacterContext context) {
		context.setAnimation(BossStateEnum.SPAWN | BossStateEnum.LEVITATE);
		context.setSound(BossStateEnum.SPAWN | BossStateEnum.LEVITATE);
		context.setInvincible(true);
	}

	@Override
	public void update(CharacterContext context) {
		if (context.getAnimationCycleCount() > 0) {
			context.changeState(new LevitateState());
		} else {
			context.addForce(2 * Vector.UP, Vector.Y);
		}

	}

	@Override
	public void exit(CharacterContext context) {
		// TODO Auto-generated method stub

	}

}
