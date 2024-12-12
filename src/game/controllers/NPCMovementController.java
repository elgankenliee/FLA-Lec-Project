package game.controllers;

import game.core.models.Vector2D;
import game.core.physics.RigidBody;

public class NPCMovementController extends RigidBodyController {

  public NPCMovementController(RigidBody rb) {
    super(rb);
  }

  @Override
  public void update(Vector2D pos) {    
    rb.update(pos);
  }

}
