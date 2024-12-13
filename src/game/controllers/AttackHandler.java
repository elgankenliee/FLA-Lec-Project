package game.controllers;

import game.core.models.Character;
import game.managers.GameManager;

public class AttackHandler {
  
  private static GameManager gm = GameManager.getInstance();
  
  public static boolean attack(int attackerId, int targetId, int damage) {
    Character target = gm.getEntity(targetId);
    Character attacker = gm.getEntity(attackerId);
    
    double targetX = target.getPos().getX();
    double targetY = target.getPos().getY();
    double targetWidth = target.getHitbox().getX(); 
    double targetHeight = target.getHitbox().getY();
    
    double attackerX = attacker.getPos().getX();
    double attackerY = attacker.getPos().getY();
    double attackerWidth = attacker.getHitbox().getX();
    double attackerHeight = attacker.getHitbox().getY();
    
    boolean attacked = (attackerX < targetX + targetWidth) &&
        (attackerX + attackerWidth > targetX) &&
        (attackerY < targetY + targetHeight) &&
        (attackerY + attackerHeight > targetY);
    
    if(attacked) target.updateHealth(-damage);
    
    return attacked;
  }
}
