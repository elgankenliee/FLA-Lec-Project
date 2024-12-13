package game.core.interfaces;

public interface VectorMotion {
  public void addForce(double force, int direction);
  public int getDirection();
}