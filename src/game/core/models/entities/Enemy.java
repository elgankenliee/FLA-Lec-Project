package game.core.models.entities;

import game.core.models.Position;

public class Enemy implements ICharacter{
	private int health;
	private Position pos;
	private Boolean isSpawning = true;
	
	public Enemy(int health, Position pos) {
		this.health = 1000;
		this.pos = pos;
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

	@Override
	public Position getPos() {
		return this.pos;
	}
	
	public void setPos(Position pos) {
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


}