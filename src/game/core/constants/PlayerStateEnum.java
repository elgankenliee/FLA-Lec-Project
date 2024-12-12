package game.core.constants;

public class PlayerStateEnum {
  public static final int IDLE = 0 << 0; 
  public static final int WALKING = 1 << 1;
  public static final int CROUCHING = 1 << 2;
  public static final int JUMPING = 1 << 3;
  public static final int ATTACKING = 1 << 4;
  public static final int FALLING = 1 << 5;
  

  public static boolean isInterruptible(int currentState) {
      return (currentState & ATTACKING) == 0;
  }
}
