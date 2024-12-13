package game.core.models;

import game.core.physics.RigidBody;

public abstract class Character {
	protected int health;
	protected Vector2D pos;
	protected Vector2D hitbox;
	protected RigidBody rb;
	protected boolean invincible;

	protected Character(int health, Vector2D pos, Vector2D hitbox) {
		this.health = health;
		this.pos = pos;
		this.hitbox = hitbox;
		this.invincible = false;
	}

	public void updateHealth(int health) {
		this.health += health;
	}

	public Vector2D getPos() {
		return this.pos;
	}

	public void setPos(Vector2D pos) {
		this.pos = pos;
	}

	public Vector2D getHitbox() {
		return this.hitbox;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setInvincible(boolean isInvincible) {
		this.invincible = isInvincible;
	}

	public boolean isInvincible() {
		return this.invincible;
	}

	public abstract void spawn();

	public abstract void die();
}
