package game.core.models;

import game.core.physics.RigidBody;

public class Enemy extends Character{
	private RigidBody rb;
	
	public Enemy(int health, Vector2D pos) {
	  super(health, pos, new Vector2D(120*4, 120*4));
		this.rb = new RigidBody(new Vector2D(100, 50));
		
	}
	
	@Override
	public void spawn() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	public RigidBody getRb() {
	  return this.rb;
	}

}