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
  
  public void setDirection(int direction) {
    this.direction = direction;
  }
  
  public int getDirection() {
    return this.direction;
  }

}
