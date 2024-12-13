package game.controllers;

import game.core.constants.Vector;
import game.core.models.Vector2D;
import game.core.physics.RigidBody;

public class NPCMovementController extends RigidBodyController {
  private int direction;

  public NPCMovementController(RigidBody rb) {
    super(rb);
    this.direction = Vector.LEFT;
  }

  @Override
  public void update(Vector2D pos) {   
    // state controlled behaviour
    rb.update(pos);
  }

  @Override
  public void addForce(double force, int direction) {
    if(direction ==  Vector.X) {
      rb.getVelocity().updateX(force);
    }
    else if(direction == Vector.Y) {
      rb.getVelocity().updateY(force);
    }
  }
  
  @Override
  public int getDirection() {
    return this.direction;
  }
  
  public void setDirection(int direction) {
    this.direction = direction;
  }
}
