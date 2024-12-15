package game.core.models;

import game.core.physics.RigidBody;

public class Enemy extends Character{
	private RigidBody rb;
	
	public Enemy(int health, Vector2D pos, int scale) {
	  super(
	      health,
	      pos, 
	      new Vector2D[] {
	          new Vector2D(120, 0), // bottom left
	          new Vector2D(120 * (scale-1), 120 * (scale-1)) // top right
	          },
	      scale
	    );
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