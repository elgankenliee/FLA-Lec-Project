package game.core.interfaces;

import game.core.models.Vector2D;
import game.core.states.boss.BossState;

public interface CharacterContext extends AnimatedBehaviour, VectorMotion, AudioBehaviour {
	public void changeState(BossState newState);
	public Vector2D getPos();
	public Vector2D getHitbox();
	public void updateHealth(int delta);
	public void setIncincible(boolean isInvincible);
	public boolean isInvincible();
}