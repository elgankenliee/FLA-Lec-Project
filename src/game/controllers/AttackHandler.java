package game.controllers;

import java.util.Random;

import game.camera.PaneObserver;
import game.core.constants.Vector;
import game.core.interfaces.CharacterContext;
import game.managers.GameManager;

public class AttackHandler {
	private static GameManager gm = GameManager.getInstance();
	private static Random rand = new Random();

	public static boolean attack(int attackerId, int targetId, int damage) {
		CharacterContext target = gm.getContext(targetId);
		CharacterContext attacker = gm.getContext(attackerId);

		double targetBottomLeftX = target.getPos().getX() + target.getHitbox()[0].getX();
		double targetBottomLeftY = target.getPos().getY() + target.getHitbox()[0].getY();
		double targetTopRightX = target.getPos().getX() + target.getHitbox()[1].getX();
		double targetTopRightY = target.getPos().getY() + target.getHitbox()[1].getY();

		double attackerBottomLeftX = attacker.getPos().getX() + attacker.getHitbox()[0].getX();
		double attackerBottomLeftY = attacker.getPos().getY() + attacker.getHitbox()[0].getY();
		double attackerTopRightX = attacker.getPos().getX() + attacker.getHitbox()[1].getX();
		double attackerTopRightY = attacker.getPos().getY() + attacker.getHitbox()[1].getY();

		boolean attacked = (attackerBottomLeftX < targetTopRightX) && (attackerTopRightX > targetBottomLeftX)
				&& (attackerBottomLeftY < targetTopRightY) && (attackerTopRightY > targetBottomLeftY);

		if (attacked && !target.isInvincible()) {
			gm.playGameSound(rand.nextInt(3));
			target.updateHealth(-damage);

			if (targetId == 1) {
				PaneObserver.getInstance().notifyEnemyListeners();
			} else if (targetId == 0) {
				target.addForce(60 * (attacker.getDirection()), Vector.X);
				PaneObserver.getInstance().notifyPlayerListeners();
			}
		}

		return attacked;
	}
}
