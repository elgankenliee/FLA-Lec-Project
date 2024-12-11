package game.managers;



import game.controllers.AnimationController;
import game.controllers.MovementController;
import game.core.animations.IAnimation;
import game.core.animations.CharacterAnimation;
import game.core.constants.PlayerState;
import game.core.interfaces.FXBehaviour;
import game.core.models.entities.Player;
import game.core.physics.PhysicsEngine;
import javafx.scene.input.KeyCode;

public class PlayerManager implements FXBehaviour {
	private PhysicsEngine physics;
  private final Player player;
  private int stateCache = 1;
  private Input input;
    
	private final MovementController movementController;
	private final AnimationController animationController;

  public PlayerManager(Player player) {

    this.physics = new PhysicsEngine();
    this.player = player;
    this.input = Input.getInstance();
    this.movementController = new MovementController();
    this.animationController = new AnimationController();

    start();
  }
  
  
  @Override
  public void start() {
    animationController.addAnimation(PlayerState.IDLE, new CharacterAnimation("src/assets/sprite/player/player_idleedited.png", 4, 150));
    animationController.addAnimation(PlayerState.WALKING, new CharacterAnimation("src/assets/sprite/player/player_walk.png", 8, 45));
    animationController.addAnimation(PlayerState.JUMPING, new CharacterAnimation("src/assets/sprite/player/player_jump.png", 4, 200));
    animationController.addAnimation(PlayerState.FALLING, new CharacterAnimation("src/assets/sprite/player/player_fall.png", 2, 150));
    animationController.addAnimation(PlayerState.ATTACKING, new CharacterAnimation("src/assets/sprite/player/player_attack_1.png", 8, 23, 80, 60));
    animationController.addAnimation(PlayerState.ATTACKING + 1, new CharacterAnimation("src/assets/sprite/player/player_attack_2.png", 8, 23, 80, 60));
    animationController.setCurrentAnimation(PlayerState.IDLE);
  }
  
  @Override
  public void update() {
    handleInput();
    handleMovement();
    handleAnimation();
  }
  
  
  private void handleMovement() {
    movementController.update(
      physics,
      player.getPos()
    );
  }
  
  private void handleAnimation() {
    animationController.update(System.currentTimeMillis());
    
    if(player.getState() != stateCache) {
      stateCache = player.getState();
      animationController.setCurrentAnimation(player.getState());
    }
    
  }

  private void handleInput() {
    player.resetState();
    
    if(input.getKey(KeyCode.SPACE)) {
      player.setState(PlayerState.ATTACKING);
      return;
    }
    
    if (movementController.getDelta().getY() > 1) {
      player.setState(PlayerState.FALLING);
      return;
    }

    if (input.getKey(KeyCode.W)) {
      player.setState(PlayerState.JUMPING);
      return;
    }

    if (input.getKey(KeyCode.A)) {
      player.setState(PlayerState.WALKING);
    }
    else if (input.getKey(KeyCode.D)) {
      player.setState(PlayerState.WALKING);
    }
    else{
      player.setState(PlayerState.IDLE);
    }
  }
  
  public IAnimation getCurrentAnimation() {
    return this.animationController.getCurrentAnimation();
  }
  
  public int getDirection() {
    return movementController.getDirection();
  }

}
