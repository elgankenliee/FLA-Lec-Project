package game.core.models;

import game.core.physics.RigidBody;

public class Player implements ICharacter{
	private int health;
	private Vector2D pos;
	private RigidBody rb;
	private int state; // Bitmask for states
	private int attackIndex; // Attack index for combos
	
	public Player(int health, Vector2D pos, int state) {
		this.health = health;
		this.pos = pos;
		this.rb = new RigidBody(new Vector2D(20,50));
		this.state = state;
		this.attackIndex = 1;
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
  
  // SETTER AND GETTERS
  
  public void setHealth(int health) {
    this.health = health;
  }
    
	public void setPos(Vector2D pos) {
		this.pos = pos;
	}
    
	public void setAttackIndex(int attackIndex) {
		this.attackIndex = attackIndex;
	}
    
	public void setState(int state) {
		this.state = state;
	}
	
    
  public int getHealth(){
    return this.health;
  }
    
	@Override
	public Vector2D getPos() {
		return this.pos;
	}
	
	public int getState() {
		return this.state;
	}
	
	public int getAttackIndex() {
		return this.attackIndex;
	}
	
	public RigidBody getRb() {
	  return this.rb;
	}

}
