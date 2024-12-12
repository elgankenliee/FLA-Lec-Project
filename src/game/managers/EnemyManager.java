package game.managers;

import game.controllers.AnimationController;
import game.controllers.NPCMovementController;
import game.core.animations.IAnimation;
import game.core.animations.CharacterAnimation;
import game.core.interfaces.BossContext;
import game.core.interfaces.FXBehaviour;
import game.core.models.Enemy;
import game.core.states.boss.BossState;
import game.core.states.boss.SpawnLevitateState;
import game.core.constants.*;

public class EnemyManager extends BossContext implements FXBehaviour {
  private Enemy enemy;
  private NPCMovementController movementController;
  private AnimationController animationController;
  private BossState currentState;

  public EnemyManager(Enemy enemy) {
    this.enemy = enemy;
    this.movementController = new NPCMovementController(enemy.getRb());
    this.animationController = new AnimationController();
    initializeAnimations();
    this.currentState = new SpawnLevitateState();
    currentState.start(this);
  }
  
  public void initializeAnimations() {
    animationController.addAnimation(BossStateEnum.SPAWN, new CharacterAnimation("src/assets/sprite/boss/boss_spawn.png", 6, 90, 120, 120));
    animationController.addAnimation(BossStateEnum.LEVITATE, new CharacterAnimation("src/assets/sprite/boss/boss_levitate.png", 6, 90, 120, 120));
    animationController.addAnimation(BossStateEnum.SPAWN | BossStateEnum.LEVITATE, new CharacterAnimation("src/assets/sprite/boss/boss_levitate_spawn.png", 5, 90, 120, 120));
    animationController.addAnimation(BossStateEnum.IDLE, new CharacterAnimation("src/assets/sprite/boss/boss_idle.png", 8, 150, 120, 120));
    animationController.addAnimation(BossStateEnum.ATTACK | BossStateEnum.SPIN, new CharacterAnimation("src/assets/sprite/boss/boss_spin.png", 9, 60, 120, 120));
    animationController.addAnimation(BossStateEnum.ATTACK | BossStateEnum.PRE_DASH, new CharacterAnimation("src/assets/sprite/boss/boss_predash.png", 3, 120, 120, 120));
    animationController.addAnimation(BossStateEnum.ATTACK | BossStateEnum.DASH, new CharacterAnimation("src/assets/sprite/boss/boss_dash.png", 1, 60, 120, 120));


  }

  @Override
  public void update() {
    if(currentState != null) {
      currentState.update(this);
    }
    movementController.update(enemy.getPos());
    animationController.update(System.currentTimeMillis());
  }
  
  @Override
  public Enemy getEnemy() {
    return this.enemy;
  }
  
  
  @Override
  public void setAnimation(int animationId) {
    animationController.setCurrentAnimation(animationId);
    
  }
  
  @Override
  public int getAnimationCycleCount() {
    return this.animationController.getCurrentAnimation().getCyclesCompleted();
  }

  @Override
  public IAnimation getCurrentAnimation() {
    return this.animationController.getCurrentAnimation();
  }
  
 @Override
 public void addForce(double force, int direction) {
   movementController.addForce(force, direction);
 }
 
 @Override
 public int getDirection() {
   return this.movementController.getDirection();
 }
 
 public void setDirection(int direction) {
   this.movementController.setDirection(direction);
 }

  public void changeState(BossState newState) {
    if(currentState != null) {
      currentState.exit(this);
    }
    currentState = newState;
    currentState.start(this);
  }
  
  



}