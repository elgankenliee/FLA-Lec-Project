package game.controllers;

import game.core.constants.Vector;
import game.core.models.Vector2D;
import game.core.physics.PhysicsEngine;
import game.core.physics.RigidBody;
import game.managers.Input;
import javafx.scene.input.KeyCode;


public class MovementController extends RigidBodyController {
  private final int acceleration;
	private final double jumpStrength;
	private Input input;
  private int direction;


  public MovementController(RigidBody rb) {
    super(rb);
    this.acceleration = 3;
    this.rb = rb;
    this.jumpStrength = 30;
    this.input = Input.getInstance();
    this.direction = Vector.LEFT;

  }
 
  @Override
  public void update(Vector2D pos) {
      if (input.getKey(KeyCode.A)) {
          rb.getVelocity().updateX(acceleration * Vector.LEFT);
          direction = Vector.LEFT;
      } 
      else if (input.getKey(KeyCode.D)) {
          rb.getVelocity().updateX(acceleration * Vector.RIGHT);
          direction = Vector.RIGHT;
      }

      if (input.getKey(KeyCode.W) && pos.getY() >= PhysicsEngine.getGroundBoundary()) {
          rb.getVelocity().setY(-jumpStrength);
      }

      rb.update(pos);
  }
  
  public int getDirection() {
    return this.direction;
}
}
