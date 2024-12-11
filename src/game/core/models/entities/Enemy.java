package game.core.models.entities;

import game.core.models.Vector2D;
import game.core.physics.RigidBody;

public class Enemy implements ICharacter{
	private int health;
	private Vector2D pos;
	private RigidBody rb;
	private Boolean isSpawning = true;
	
	public Enemy(int health, Vector2D pos) {
		this.health = 1000;
		this.pos = pos;
		this.rb = new RigidBody(new Vector2D(20, 50));
	}
	
	@Override
	public void spawn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2D getPos() {
		return this.pos;
	}
	
	public void setPos(Vector2D pos) {
		this.pos = pos;
	}

	public Boolean isSpawning() {
		return isSpawning;
	}

	public void setSpawning(Boolean isSpawning) {
		this.isSpawning = isSpawning;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public RigidBody getRb() {
	  return this.rb;
	}

}