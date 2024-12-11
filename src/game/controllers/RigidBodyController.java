package game.controllers;

import game.core.models.Vector2D;
import game.core.physics.RigidBody;

public abstract class RigidBodyController {

  protected RigidBody rb;
  public abstract void update(Vector2D pos);
  
  protected RigidBodyController(RigidBody rb) {
    this.rb = rb;
  }

}
