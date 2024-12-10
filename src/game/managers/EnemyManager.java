package game.managers;

import game.controllers.AnimationController;
import game.core.animations.IAnimation;
import game.core.animations.CharacterAnimation;
import game.core.interfaces.FXBehaviour;
import game.core.models.Position;
import game.core.models.entities.Enemy;
import game.core.models.entities.Player;
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

	@Override
	public void update(Player player) {
		// TODO Auto-generated method stub
		 Position playerPos = player.getPos();
	    Position enemyPos = enemy.getPos();
	    double speed = 6.0;

	    // Horizontal movement toward the player
	    if (enemyPos.getX() > playerPos.getX()) {
	        enemy.move(-speed, 0); // Move left
	    } else if (enemyPos.getX() < playerPos.getX()) {
	        enemy.move(speed, 0); // Move right
	    }

	    // Vertical movement
	    if (enemyPos.getY() > playerPos.getY()) {
	        enemy.move(0, -speed * 2); // Move up
	    } else if (enemyPos.getY() < playerPos.getY() && enemyPos.getY() < 450) {
	        enemy.move(0, speed / 1); // Move down
	    }
	    // Debug
	    //System.out.println(enemyPos.getY());
	    
	 // Calculate the distance between enemy and player
	    double distanceX = Math.abs(enemyPos.getX() - playerPos.getX());
	    double distanceY = Math.abs(enemyPos.getY() - playerPos.getY());
	    double collisionThreshold = 127; // Adjust this value based on your game's scale

	    if (distanceX < collisionThreshold && distanceY < collisionThreshold) {
	        player.setHealth(player.getHealth() - 1);
	    }
	    
	    animationController.update(System.currentTimeMillis());

	}

}
