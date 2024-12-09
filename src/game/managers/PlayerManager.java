package game.managers;


import java.util.HashSet;
import java.util.Set;

import game.controllers.AnimationController;
import game.controllers.MovementController;
import game.core.animations.IAnimation;
import game.core.animations.player.PlayerAnimation;
import game.core.constants.PlayerState;
import game.core.models.Position;
import game.core.models.entities.Player;
import game.core.physics.PhysicsEngine;
import javafx.scene.input.KeyCode;

public class PlayerManager {
	private PhysicsEngine physics;
  private final Player player;
  private Set<KeyCode> pressedKeys;
  private Position delta;
    
	private final MovementController movementController;
	private final AnimationController animationController;

  public PlayerManager(Player player) {

    this.physics = new PhysicsEngine();
    this.player = player;
    this.pressedKeys = new HashSet<>();
    this.movementController = new MovementController();
    this.animationController = new AnimationController();
    this.delta = new Position(0,0);

    animationController.addAnimation(PlayerState.IDLE, new PlayerAnimation("src/assets/sprite/player/player_idleedited.png", 4, 150));
    animationController.addAnimation(PlayerState.WALKING, new PlayerAnimation("src/assets/sprite/player/player_walk.png", 8, 50));
    animationController.addAnimation(PlayerState.JUMPING, new PlayerAnimation("src/assets/sprite/player/player_jump.png", 4, 200));
    animationController.addAnimation(PlayerState.FALLING, new PlayerAnimation("src/assets/sprite/player/player_fall.png", 2, 150));
    animationController.addAnimation(PlayerState.ATTACKING, new PlayerAnimation("src/assets/sprite/player/player_attack_1.png", 5, 100));

    animationController.setCurrentAnimation(PlayerState.IDLE);

  }
  
  public void update() {
    handleInput();
    handleMovement();
    handleAnimation();
  }
  
  private void handleMovement() {
    delta = movementController.update(
      pressedKeys,
      physics,
      player.getPos()
    );

    player.move(delta.getX(), delta.getY());
  }
  
  private void handleAnimation() {
    animationController.setCurrentAnimation(player.getState());
    animationController.update(System.currentTimeMillis());
  }

  private void handleInput() {
      
    player.resetState();
    
    if (delta.getY() > 1) {
      player.setState(PlayerState.FALLING);
      return;
    }

    if (pressedKeys.contains(KeyCode.W)) {
      player.setState(PlayerState.JUMPING);
      return;
    }

    if (pressedKeys.contains(KeyCode.A)) {
      player.setState(PlayerState.WALKING);
      player.setFacingRight(false);
    }
    else if (pressedKeys.contains(KeyCode.D)) {
      player.setState(PlayerState.WALKING);
      player.setFacingRight(true); 
    }
    else{
      player.setState(PlayerState.IDLE);
    }
  }
  
  public void addKeyPressed(KeyCode keyPressed) {
    pressedKeys.add(keyPressed);
  }
  
  public void removeKeyPressed(KeyCode keyPressed) {
    pressedKeys.remove(keyPressed);
  }
  
  public IAnimation getCurrentAnimation() {
    return this.animationController.getCurrentAnimation();
  }
}
