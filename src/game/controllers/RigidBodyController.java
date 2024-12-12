package game.controllers;

import game.core.constants.Vector;
import game.core.models.Vector2D;
import game.core.physics.RigidBody;

public abstract class RigidBodyController {

  protected RigidBody rb;
  public abstract void update(Vector2D pos);
  
  protected RigidBodyController(RigidBody rb) {
    this.rb = rb;
  }

  public void addForce(double force, int direction) {
    if(direction ==  Vector.X) {
      rb.getVelocity().updateX(force);
    }
    else if(direction == Vector.Y) {
      rb.getVelocity().updateY(force);
    }
  }
}
