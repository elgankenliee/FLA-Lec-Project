package game.core.interfaces;

public interface ContextualMovement {
  public void addForce(double force, int direction);
  public int getDirection();
}