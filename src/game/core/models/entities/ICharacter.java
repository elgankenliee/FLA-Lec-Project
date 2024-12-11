package game.core.models.entities;

import game.core.models.Vector2D;

public interface ICharacter {
	public Vector2D getPos();
	public void spawn();
	public void die();
}
