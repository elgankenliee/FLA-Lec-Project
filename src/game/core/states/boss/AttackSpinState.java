package game.core.states.boss;

import game.controllers.AttackHandler;
import game.core.constants.BossStateEnum;
import game.core.interfaces.CharacterContext;

public class AttackSpinState implements BossState {

	@Override
	public void start(CharacterContext context) {
		context.setAnimation(BossStateEnum.ATTACK | BossStateEnum.SPIN);
		context.setSound(BossStateEnum.ATTACK | BossStateEnum.SPIN);
	}

	@Override
	public void update(CharacterContext context) {

		AttackHandler.attack(1, 0, 1);

		if (context.getAnimationCycleCount() > 1) {
			context.changeState(new IdleDespawnState());
		}
	}

	@Override
	public void exit(CharacterContext context) {

	}

}
