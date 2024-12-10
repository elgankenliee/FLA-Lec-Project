package game.managers;

import game.controllers.AnimationController;
import game.core.animations.IAnimation;
import game.core.animations.CharacterAnimation;
import game.core.interfaces.FXBehaviour;
import game.core.models.Position;
import game.core.models.entities.Enemy;
import game.core.physics.PhysicsEngine;

public class EnemyManager implements FXBehaviour{

  private Enemy enemy;
  private PhysicsEngine physics;
  private Position delta;
  
  private AnimationController animationController;
  
  
  public EnemyManager(Enemy enemy) {
    this.enemy = enemy;
    this.physics = new PhysicsEngine();
    this.delta = new Position(0, 0);
    this.animationController = new AnimationController();
    
    start();
  }

  @Override
  public void start() {
    animationController.addAnimation(0, new CharacterAnimation("src/assets/sprite/boss/boss_idle.png", 8, 150, 120, 120));
    animationController.setCurrentAnimation(0);
  }

  @Override
  public void update() {
    animationController.update(System.currentTimeMillis());
  }
  
  public IAnimation getCurrentAnimation() {
    return this.animationController.getCurrentAnimation();
  }

}
