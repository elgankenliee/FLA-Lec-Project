package game.core.models.entities;

import game.core.models.Position;

public class Player implements ICharacter{
	private int health;
	private Position pos;
  private boolean facingRight;
  private int state; // Bitmask for states
	private int attackIndex; // Attack index for combos
	
	public Player(int health, Position pos, int state) {
		this.health = health;
		this.pos = pos;
		this.state = state;
		this.attackIndex = 1;
	}

	@Override
	public void move(double x, double y) {
		this.pos.updateX(x);
		this.pos.updateY(y);
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

  public boolean isFacingRight() {
      return facingRight;
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
  
  // SETTER AND GETTERS
  
  public void setHealth(int health) {
    this.health = health;
  }
    
	public void setPos(Position pos) {
		this.pos = pos;
	}
    
	public void setAttackIndex(int attackIndex) {
		this.attackIndex = attackIndex;
	}
	
  public void setFacingRight(boolean facingRight) {
    this.facingRight = facingRight;
  }
    
	public void setState(int state) {
		this.state = state;
	}
	
    
  public int getHealth(){
    return this.health;
  }
    
	@Override
	public Position getPos() {
		return this.pos;
	}
	
	public int getState() {
		return this.state;
	}
	
	public int getAttackIndex() {
		return this.attackIndex;
	}
}
