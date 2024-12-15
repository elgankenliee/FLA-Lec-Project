package game.core.states.boss;

import game.core.constants.BossStateEnum;
import game.core.interfaces.CharacterContext;

public class SpawnState implements BossState {

	@Override
	public void start(CharacterContext context) {
		context.setAnimation(BossStateEnum.SPAWN);
		context.setInvincible(true);
	}

	@Override
	public void update(CharacterContext context) {
		if (context.getAnimationCycleCount() > 0) {
			context.changeState(new IdleState());
		}
	}

	@Override
	public void exit(CharacterContext context) {
	}
}