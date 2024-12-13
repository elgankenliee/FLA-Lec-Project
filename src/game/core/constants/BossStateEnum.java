package game.core.constants;

public class BossStateEnum {
  
  public static final int SPAWN = 1 << 0;
  public static final int IDLE = 1 << 1;
  public static final int LEVITATE = 1 << 2;
  public static final int ATTACK = 1 << 3;
  public static final int SPIN = 1 << 4;
  public static final int PRE_DASH = 1 << 5;
  public static final int DASH = 1 << 6;
  public static final int SPLIT_SWORD = 1 << 7;
  public static final int DESPAWN = 1 << 8;
}