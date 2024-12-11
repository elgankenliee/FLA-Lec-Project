package game.controllers;

import game.core.models.Vector2D;
import game.core.physics.PhysicsEngine;
import game.core.physics.RigidBody;
import game.managers.Input;
import javafx.scene.input.KeyCode;


public class MovementController extends RigidBodyController{
  private final int acceleration;
	private final double jumpStrength;
	private Input input;

  public MovementController(RigidBody rb) {
    super(rb);
    this.acceleration = 3;
    this.rb = rb;
    this.jumpStrength = 30;
    this.input = Input.getInstance();
  }
 
  @Override
  public void update(Vector2D pos) {
      if (input.getKey(KeyCode.A)) {
          rb.getVelocity().updateX(acceleration);
          rb.setDirection(-1);
      } else if (input.getKey(KeyCode.D)) {
          rb.getVelocity().updateX(acceleration);
          rb.setDirection(1);
      }

      if (input.getKey(KeyCode.W) && pos.getY() >= PhysicsEngine.getGroundBoundary()) {
          rb.getVelocity().setY(-jumpStrength);
      }

      rb.update(pos);
  }
  
  
  public int getDirection() {
    return this.rb.getDirection();
  }
}
