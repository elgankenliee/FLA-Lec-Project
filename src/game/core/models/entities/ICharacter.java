package game.core.models.entities;

import game.core.models.Position;

public interface ICharacter {
	public void move(double x, double y);
	public Position getPos();
	
	public void spawn();
	public void die();
}
