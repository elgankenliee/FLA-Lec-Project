package game.core.states.boss;

import game.controllers.AttackHandler;
import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.CharacterContext;

public class AttackDashState implements BossState {

	private boolean dashed;
	private boolean hasAttacked;

	@Override
	public void start(CharacterContext context) {
		this.dashed = false;
		this.hasAttacked = false;
		context.setAnimation(BossStateEnum.ATTACK | BossStateEnum.DASH);
		context.setSound(BossStateEnum.ATTACK | BossStateEnum.DASH);
		context.setInvincible(false);

	}

	@Override
	public void update(CharacterContext context) {

		if (!hasAttacked) {
			hasAttacked = AttackHandler.attack(1, 0, 50);
		}

		if (!dashed) {
			context.addForce(60 * context.getDirection(), Vector.X);
			dashed = true;
		}

		if (context.getAnimationCycleCount() > 6) {
			context.changeState(new IdleState());
		}

	}

	@Override
	public void exit(CharacterContext context) {
		// TODO Auto-generated method stub

	}

}
