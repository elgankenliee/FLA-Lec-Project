package game.core.physics;

import game.core.models.Vector2D;

/*
 * Velocity:
 *  X: positive value, direction determined by another variable
 *  Y: positive and negative value, determining the direction because why not
 */

public class RigidBody {

  private Vector2D velocity;
  private Vector2D delta;
  private final Vector2D terminalVelocity;
  private final PhysicsEngine physics;
  private int direction;

  public RigidBody(Vector2D terminalVelocity) {
      this.velocity = new Vector2D(0, 0);
      this.delta = new Vector2D(0, 0);
      this.terminalVelocity = terminalVelocity;
      this.physics = PhysicsEngine.getInstance();
      this.direction = 1;
  }

  public void update(Vector2D pos) {
      physics.applyFriction(velocity);

      velocity.setX(
          Math.max(-terminalVelocity.getX(), Math.min(velocity.getX(), terminalVelocity.getX()))
      );

      if (pos.getY() < PhysicsEngine.getGroundBoundary()) {
          physics.applyGravity(velocity);
      }

      velocity.setY(
          Math.max(-terminalVelocity.getY(), Math.min(velocity.getY(), terminalVelocity.getY()))
      );

      delta.set(direction * velocity.getX(), velocity.getY());

      physics.applyBoundaryLimits(pos, delta);

      pos.update(delta.getX(), delta.getY());
  }

  public void setDirection(int direction) {
      this.direction = direction;
  }

  public int getDirection() {
      return this.direction;
  }

  public Vector2D getVelocity() {
      return velocity;
  }

  public void setVelocity(Vector2D velocity) {
      this.velocity = velocity;
  }

  public Vector2D getDelta() {
      return delta;
  }
}