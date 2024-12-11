package game.controllers;

import java.util.Set;

import game.core.models.Vector2D;
import game.core.physics.PhysicsEngine;
import game.managers.Input;
import javafx.scene.input.KeyCode;

/*
 * Velocity:
 *  X: positive value, direction determined by another variable
 *  Y: positive and negative value, determining the direction because why not
 */

public class MovementController {
  private final int acceleration;
  private Vector2D velocity; 
  private Vector2D delta;
  private int direction;
	private final Vector2D terminalVelocity;
	private final double jumpStrength;
	private Input input;

  public MovementController() {
    this.acceleration = 3;
    this.velocity = new Vector2D(0,0);
    this.delta = new Vector2D(0, 0);
    this.direction = 1;
    this.terminalVelocity = new Vector2D(15, 50);
    this.jumpStrength = 30;
    this.input = Input.getInstance();
  }
    
  public void update(
		PhysicsEngine physics,
		Vector2D pos
  ) {
    	
      if (input.getKey(KeyCode.A)) {
        velocity.updateX(acceleration);
        direction = -1;
      } 
      else if (input.getKey(KeyCode.D)) {
        velocity.updateX(acceleration);
        direction = 1;
      }
        
      physics.applyFriction(velocity);
            
      velocity.setX(
        Math.max(
          -terminalVelocity.getX(),
          Math.min(velocity.getX(), terminalVelocity.getX())
        )
      );
        
      if(pos.getY() < physics.getGroundBoundary()) {
        physics.applyGravity(velocity);
      }
      else if (input.getKey(KeyCode.W)){
        velocity.setY(-jumpStrength);
      }
        
      velocity.setY(
    		Math.max(
          -terminalVelocity.getY(),
          Math.min(velocity.getY(), terminalVelocity.getY())
        )
      );
      
      double directionalDeltaX =  direction * velocity.getX();
      delta.set(directionalDeltaX, velocity.getY());
                
      physics.applyBoundaryLimits(pos, delta);
        
      pos.update(delta.getX(), delta.getY());
      
//      System.out.println("x :" + pos.getX() +  " | y:" + pos.getY());
  }
  
  
  public Vector2D getDelta() {
    return this.delta;
  }
  
  public int getDirection() {
    return this.direction;
  }
}
