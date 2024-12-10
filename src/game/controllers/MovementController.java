package game.controllers;

import java.util.Set;

import game.core.models.Position;
import game.core.physics.PhysicsEngine;
import javafx.scene.input.KeyCode;

public class MovementController {
  private final int acceleration;
  private Position velocity;
	private final Position terminalVelocity;
	private final double jumpStrength;


  public MovementController() {
    this.velocity = new Position(0,0);
    this.acceleration = 3;
    this.terminalVelocity = new Position(15, 50);
    this.jumpStrength = 30;
  }
    
    
  public Position update(
		Set<KeyCode> pressedKeys,
		PhysicsEngine physics,
		Position currentPos
  ) {
    	Position delta = new Position(0,0);
    	
      if (pressedKeys.contains(KeyCode.A)) {
        velocity.updateX(-acceleration);
      } 
      else if (pressedKeys.contains(KeyCode.D)) {
        velocity.updateX(acceleration);
      }
        
      velocity.setX(physics.applyFriction(velocity.getX()));
      
      velocity.setX(
        Math.max(
          -terminalVelocity.getX(),
          Math.min(velocity.getX(), terminalVelocity.getX())
        )
      );
        
      if(currentPos.getY() < physics.getGroundBoundary()) {
        velocity.updateY(physics.applyGravity(delta.getY()));
      }
      else if (pressedKeys.contains(KeyCode.W)){
        velocity.setY(-jumpStrength);
      }
        
      velocity.setY(
    		Math.max(
          -terminalVelocity.getY(),
          Math.min(velocity.getY(), terminalVelocity.getY())
        )
      );
        
      delta.set(velocity.getX(), velocity.getY());
                
      physics.inbound(
    		currentPos, 
    		delta.getX(), 
    		delta.getY(), 
    		(xBound, yBound) -> {
    			if (!xBound) delta.setX(0);
          if (!yBound && currentPos.getY() >= physics.getGroundBoundary()) {
            delta.setY(0);
          }
        }
      );
        
      return delta;
  }
}
