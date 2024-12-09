package game.core.physics;

import game.core.interfaces.PhysicsBoundaryHandler;
import game.core.models.Position;

public class PhysicsEngine {
  private final Position leftBoundary;
  private final Position rightBoundary;
  private final double friction;
  private final double gravity;

  public PhysicsEngine() {
    this.leftBoundary = new Position(300, 580);       
    this.rightBoundary = new Position(1300, 0); 
    this.friction = 1.5;
    this.gravity = 2;
  }

  public boolean inbound(Position pos, double dx, double dy, PhysicsBoundaryHandler boundaryHandler) {
    double newX = pos.getX() + dx;
    double newY = pos.getY() + dy;

    boolean xBound = newX >= leftBoundary.getX() && newX <= rightBoundary.getX();
    boolean yBound = newY >= rightBoundary.getY() && newY <= leftBoundary.getY();
    
    boundaryHandler.handle(xBound, yBound);

    return xBound && yBound;
  }
  
  public double applyFriction(double velocity) {
    if (Math.abs(velocity) < 0.1) {
        return 0;
    }
    if(velocity > 0) {
      return velocity - this.friction;
    }
    return velocity + this.friction;
  }
  
  public double applyGravity(double velocity) {
    return velocity + this.gravity;
  }
  
  public double getGroundBoundary() {
    return this.leftBoundary.getY();
  }

}
