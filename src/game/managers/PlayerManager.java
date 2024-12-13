package game.managers;



import game.controllers.AnimationController;
import game.controllers.AttackHandler;
import game.controllers.MovementController;
import game.core.animations.IAnimation;
import game.core.animations.CharacterAnimation;
import game.core.constants.PlayerStateEnum;
import game.core.interfaces.VectorMotion;
import game.core.interfaces.FXBehaviour;
import game.core.models.Player;
import javafx.scene.input.KeyCode;

public class PlayerManager implements VectorMotion, FXBehaviour {
  private final Player player;
  private int stateCache = 1;
  private Input input;
  private final MovementController movementController;
  private final AnimationController animationController;
  private final int attackDuration; 
  private int attackCd;
  private int attackTimer;
  

  public PlayerManager(Player player) {
    this.player = player;
    this.input = Input.getInstance();
    this.movementController = new MovementController(player.getRb());
    this.animationController = new AnimationController();
    this.attackDuration = 12; 
    this.attackCd = 0;
    this.attackTimer = 0;

    initializeAnimations();
  }
  
  private void initializeAnimations() {
    animationController.addAnimation(PlayerStateEnum.IDLE, new CharacterAnimation("src/assets/sprite/player/player_idleedited.png", 4, 150));
    animationController.addAnimation(PlayerStateEnum.WALKING, new CharacterAnimation("src/assets/sprite/player/player_walk.png", 8, 45));
    animationController.addAnimation(PlayerStateEnum.JUMPING, new CharacterAnimation("src/assets/sprite/player/player_jump.png", 4, 200));
    animationController.addAnimation(PlayerStateEnum.FALLING, new CharacterAnimation("src/assets/sprite/player/player_fall.png", 2, 150));
    animationController.addAnimation(PlayerStateEnum.ATTACKING, new CharacterAnimation("src/assets/sprite/player/player_attack_1.png", 8, 23, 80, 60));
    animationController.addAnimation(PlayerStateEnum.ATTACKING, new CharacterAnimation("src/assets/sprite/player/player_attack_2.png", 8, 23, 80, 60));
    animationController.setCurrentAnimation(PlayerStateEnum.IDLE);
  }
  
  @Override
  public void update() {
    handleInput();
    handleMovement();
    handleAnimation();
    handleAttack();
  }
 
  public void handleAttack() {
    if (attackTimer > 0) {
      attackTimer--;
      if (attackTimer == 0) {
        player.removeState(PlayerStateEnum.ATTACKING);
      }
    } 
    if(attackCd > 0) {
      attackCd--;
    }
  }
  @Override
  public void addForce(double force, int direction) {
    movementController.addForce(force, direction);
  }
  
  @Override
  public int getDirection() {
    return movementController.getDirection();
  }
  
  private void handleMovement() {
    movementController.update(player.getPos());
  }
  

  private void handleAnimation() {
    animationController.update(System.currentTimeMillis());

    if (player.hasState(PlayerStateEnum.ATTACKING)) {
      if (stateCache != PlayerStateEnum.ATTACKING) {
        stateCache = PlayerStateEnum.ATTACKING;
        animationController.setCurrentAnimation(PlayerStateEnum.ATTACKING);
      }
      return;
    }

    int currentState = player.getState();
    if (currentState != stateCache) {
        stateCache = currentState;
        animationController.setCurrentAnimation(currentState);
    }
}

  private void handleInput() {
    if (input.getKey(KeyCode.SPACE)) {
      if (attackTimer == 0 && attackCd == 0) {
        player.addState(PlayerStateEnum.ATTACKING);
        AttackHandler.attack(0, 1, 20); // autism but yes
        attackTimer = attackDuration;
        attackCd = 30;
        return;
      }
    } 

    if(player.hasState(PlayerStateEnum.ATTACKING)) {
      return;
    }
    
    if (player.getRb().getDelta().getY() > 1) {
      player.setState(PlayerStateEnum.FALLING);
      return;
    }

    if (input.getKey(KeyCode.W)) {
      player.setState(PlayerStateEnum.JUMPING);
      return;
    }

    if (input.getKey(KeyCode.A)) {
      player.setState(PlayerStateEnum.WALKING);
    }
    else if (input.getKey(KeyCode.D)) {
      player.setState(PlayerStateEnum.WALKING);
    }
    else {
      player.setState(PlayerStateEnum.IDLE);
    }
  }
  
  public IAnimation getCurrentAnimation() {
    return this.animationController.getCurrentAnimation();
  }
}
