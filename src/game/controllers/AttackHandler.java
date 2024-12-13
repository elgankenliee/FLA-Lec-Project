package game.controllers;

import game.core.constants.Vector;
import game.core.interfaces.CharacterContext;
import game.managers.GameManager;

public class AttackHandler {
  
  private static GameManager gm = GameManager.getInstance();
  
  public static boolean attack(int attackerId, int targetId, int damage) {
    CharacterContext target = gm.getContext(targetId);
    CharacterContext attacker = gm.getContext(attackerId);
    
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
    
    if(attacked) {
      target.updateHealth(-damage);
      // add clash sound
//      if(target.getDirection() ==  Vector.RIGHT) {
//        target.addForce(20 * Vector.LEFT, Vector.X);
//      }
//      else {
//        target.addForce(20 * Vector.RIGHT, Vector.X);
//      }
    };
    return attacked;
  }
}
