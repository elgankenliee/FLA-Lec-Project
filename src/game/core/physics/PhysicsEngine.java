package game.core.physics;

import game.core.models.Vector2D;


public class PhysicsEngine {
    private static final Vector2D leftBoundary = new Vector2D(240, 800);
    private static final Vector2D rightBoundary = new Vector2D(1340, 0);
    private final double friction;
    private final double gravity;
    private final double marginError;

    private static PhysicsEngine instance;

    private PhysicsEngine() {
        this.friction = 1.5;
        this.gravity = 2;
        this.marginError = 0.5;
    }

    public static PhysicsEngine getInstance() {
        if (instance == null) {
          instance = new PhysicsEngine();
        }
        return instance;
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
      double vx = velocity.getX();
      
      if (Math.abs(vx) < friction) {
          velocity.setX(0);
      } 
      else {
          double force = (vx > 0) ? -friction : friction;
          velocity.updateX(force);
      }
  }

    public void applyGravity(Vector2D velocity) {
        velocity.updateY(this.gravity);
    }

    public static double getGroundBoundary() {
        return leftBoundary.getY();
    }
}
