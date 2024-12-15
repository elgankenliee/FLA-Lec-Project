package game.core.states.boss;

import game.controllers.AttackHandler;
import game.core.constants.BossStateEnum;
import game.core.constants.Vector;
import game.core.interfaces.CharacterContext;

public class AttackSpinState implements BossState {

  private boolean hasAttacked;
	@Override
	public void start(CharacterContext context) {
		context.setAnimation(BossStateEnum.ATTACK | BossStateEnum.SPIN);
		context.setSound(BossStateEnum.ATTACK | BossStateEnum.SPIN);
		context.setInvincible(false);
		this.hasAttacked = false;
	}

	@Override
	public void update(CharacterContext context) {

		if(AttackHandler.attack(1, 0, 2)) {
		  hasAttacked = true;
		}

		int cycleCount = context.getAnimationCycleCount();
		if (cycleCount > 1) {
		  if(hasAttacked) {
		    context.addForce(20 * (context.getDirection()*-1), Vector.X);
	      context.changeState(new SplitSwordState());
		  }
		  else {
	      context.changeState(new IdleDespawnState());

		  }
    }

		
	}

	@Override
	public void exit(CharacterContext context) {

	}

}
