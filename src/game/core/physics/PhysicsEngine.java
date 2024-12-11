package game.core.physics;

import game.core.models.Vector2D;

public class PhysicsEngine {
  private final Vector2D leftBoundary;
  private final Vector2D rightBoundary;
  private final double friction;
  private final double gravity;

  public PhysicsEngine() {
    this.leftBoundary = new Vector2D(300, 800);       
    this.rightBoundary = new Vector2D(1300, 0); 
    this.friction = 1.5;
    this.gravity = 2;
  }

  public boolean applyBoundaryLimits(Vector2D pos, Vector2D delta) {
    double newX = pos.getX() + delta.getX();
    double newY = pos.getY() + delta.getY();

    boolean xBound = newX >= leftBoundary.getX() && newX <= rightBoundary.getX();
    boolean yBound = newY >= rightBoundary.getY() && newY <= leftBoundary.getY();

    if (!xBound) {
        delta.setX(0);
    }

    if (!yBound && newY > leftBoundary.getY()) {
        delta.setY(leftBoundary.getY() - pos.getY());
    }

    return xBound && yBound;
}
  
  public void applyFriction(Vector2D velocity) {
    if (velocity.getX() < 0.1) {
      velocity.setX(0);
    }
    else if(velocity.getX() > 0) {
      velocity.updateX(-friction);
    }

  }
  
  public void applyGravity(Vector2D velocity) {
    velocity.updateY(this.gravity);
  }
  
  public double getGroundBoundary() {
    return this.leftBoundary.getY();
  }

}
