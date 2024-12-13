package game.controllers;

import game.core.interfaces.VectorMotion;
import game.core.models.Vector2D;
import game.core.physics.RigidBody;

public abstract class RigidBodyController implements VectorMotion {

  protected RigidBody rb;
  public abstract void update(Vector2D pos);
  
  protected RigidBodyController(RigidBody rb) {
    this.rb = rb;
  }
}
