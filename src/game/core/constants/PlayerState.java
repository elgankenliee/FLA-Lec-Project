package game.core.constants;

public class PlayerState {
    public static final int IDLE = 0; // 0000
    public static final int WALKING = 1 << 0; // 0001
    public static final int CROUCHING = 1 << 1;	// 0010
    public static final int JUMPING = 1 << 2;  // 0100
    public static final int ATTACKING = 1 << 3; // 1000
    public static final int FALLING = 1 << 4;
}