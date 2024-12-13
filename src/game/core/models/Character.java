package game.core.models;

public abstract class Character {
  protected int health;
  protected Vector2D pos;
  protected Vector2D hitbox;
  
  protected Character(int health, Vector2D pos, Vector2D hitbox) {
    this.health = health;
    this.pos = pos;
    this.hitbox = hitbox;
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
  
	public abstract void spawn();
	public abstract void die();
}
