package game.core.models;

import game.core.physics.RigidBody;

public class Player extends Character {
	private RigidBody rb;
	private int state;
	private int stamina;

	public Player(int health, Vector2D pos, int state, int scale) {
    super(
      health,
      pos, 
      new Vector2D[] {
          new Vector2D(60, 0), // bottom left
          new Vector2D(60 * (scale), 60 * (scale)) // top right
          },
      scale
    );
   
    this.health = health;
    this.pos = pos;
    this.rb = new RigidBody(new Vector2D(20, 50));
    this.state = state;
    this.stamina = 1000;
    setInvincible(false);
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	public void resetState() {
		this.state = 0;
	}

	public void addState(int newState) {
		this.state |= newState;
	}

	public void removeState(int stateToRemove) {
		this.state &= ~stateToRemove;
	}

	public boolean hasState(int checkState) {
		return (this.state & checkState) != 0;
	}

	public boolean hasStateCombination(int requiredStates, int forbiddenStates) {
		return (this.state & requiredStates) == requiredStates && (this.state & forbiddenStates) == 0;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setPos(Vector2D pos) {
		this.pos = pos;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	public RigidBody getRb() {
		return this.rb;
	}

	public void updateStamina(int staminaModifier) {
		if (this.stamina + staminaModifier < 0 || this.stamina + staminaModifier > 1000)
			return;
		this.stamina += staminaModifier;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

}
