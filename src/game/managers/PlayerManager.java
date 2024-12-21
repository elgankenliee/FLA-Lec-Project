package game.managers;

import game.controllers.AnimationController;
import game.controllers.AttackHandler;
import game.controllers.AudioController;
import game.controllers.MovementController;
import game.core.animations.CharacterAnimation;
import game.core.animations.IAnimation;
import game.core.audio.Audio;
import game.core.audio.IAudio;
import game.core.constants.PlayerStateEnum;
import game.core.interfaces.CharacterContext;
import game.core.interfaces.FXBehaviour;
import game.core.models.Player;
import game.core.models.Vector2D;
import game.core.states.boss.BossState;
import javafx.scene.input.KeyCode;

public class PlayerManager implements CharacterContext, FXBehaviour {
	private final Player player;
	private int stateCache = 1;
	private Input input;
	private final MovementController movementController;
	private final AnimationController animationController;
	private final AudioController audioController;
	private final int attackDuration;
	private final int staminaTreshold = 250;
//	private int attackCd;
	private int attackTimer;

	public PlayerManager(Player player) {
		this.player = player;
		this.input = Input.getInstance();
		this.movementController = new MovementController(player.getRb());
		this.animationController = new AnimationController();
		this.audioController = new AudioController();
		this.attackDuration = 12;
//		this.attackCd = 0;
		this.attackTimer = 0;

		initializeAnimations();
		initializeAudio();
	}

	@Override
	public void start() {

	}

	@Override
	public void update() {
		handleInput();
		handleMovement();
		handleAnimation();
		handleAttack();
		handleStamina();
		handleHealth();
	}

	private void handleStamina() {

		player.updateStamina(5);

	}

	private void handleHealth() {

		if (player.getHealth() <= 0)
			System.exit(0);

	}

	private void initializeAnimations() {
		animationController.addAnimation(PlayerStateEnum.IDLE,
				new CharacterAnimation("src/assets/sprite/player/player_idleedited.png", 4, 150));
		animationController.addAnimation(PlayerStateEnum.WALKING,
				new CharacterAnimation("src/assets/sprite/player/player_walk.png", 8, 40));
		animationController.addAnimation(PlayerStateEnum.JUMPING,
				new CharacterAnimation("src/assets/sprite/player/player_jump.png", 4, 200));
		animationController.addAnimation(PlayerStateEnum.JUMPING | PlayerStateEnum.WALKING,
				new CharacterAnimation("src/assets/sprite/player/player_jump.png", 4, 200));
		animationController.addAnimation(PlayerStateEnum.FALLING,
				new CharacterAnimation("src/assets/sprite/player/player_fall.png", 2, 150));
		animationController.addAnimation(PlayerStateEnum.ATTACKING,
				new CharacterAnimation("src/assets/sprite/player/player_attack_2.png", 8, 23, 80, 60));
		animationController.addAnimation(PlayerStateEnum.ATTACKING | PlayerStateEnum.JUMPING,
				new CharacterAnimation("src/assets/sprite/player/player_attack_airborne.png", 8, 23, 80, 60));
		animationController.setCurrentAnimation(PlayerStateEnum.IDLE);
	}

	private void initializeAudio() {
		audioController.addAudio(PlayerStateEnum.JUMPING, new Audio("src/assets/audio/sfx/jump.wav"));
		audioController.addAudio(PlayerStateEnum.ATTACKING, new Audio("src/assets/audio/sfx/swordswing1.wav"));
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

	public void handleAttack() {
		if (attackTimer > 0) {
			attackTimer--;
			if (attackTimer == 0) {
				player.removeState(PlayerStateEnum.ATTACKING);
			}
		}
//		if (attackCd > 0) {
//			attackCd--;
//		}
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
			if (attackTimer == 0 && player.getStamina() >= staminaTreshold) {
				player.addState(PlayerStateEnum.ATTACKING);
				AttackHandler.attack(0, 1, 20); // autism but yes
				attackTimer = attackDuration;
//				attackCd = 30;
				player.updateStamina(-staminaTreshold);
				return;
			}
		}

		if (player.hasState(PlayerStateEnum.ATTACKING)) {
			return;
		}
		if (player.getRb().getDelta().getY() > 0) {
			player.setState(PlayerStateEnum.FALLING);
			return;
		}

		if (input.getKey(KeyCode.W) && !player.hasState(PlayerStateEnum.FALLING)) {
			player.addState(PlayerStateEnum.JUMPING);
			return;
		} else {
			player.removeState(PlayerStateEnum.JUMPING);
		}

		if (input.getKey(KeyCode.A)) {
			player.setState(PlayerStateEnum.WALKING);
		} else if (input.getKey(KeyCode.D)) {
			player.setState(PlayerStateEnum.WALKING);
		} else {
			player.setState(PlayerStateEnum.IDLE);
		}
	}

	public IAnimation getCurrentAnimation() {
		return this.animationController.getCurrentAnimation();
	}

	@Override
	public int getAnimationCycleCount() {
		return Integer.MIN_VALUE;
	}

	@Override
	public void setAnimation(int animationId) {
		// TODO Auto-generated method stub
	}

	@Override
	public IAudio getCurrentSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSound(int soundId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeState(BossState newState) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector2D getPos() {
		return this.player.getPos();
	}

	@Override
	public Vector2D[] getHitbox() {
		return this.player.getHitbox();
	}

	@Override
	public void updateHealth(int delta) {
		this.player.updateHealth(delta);
	}

	@Override
	public void setInvincible(boolean isInvincible) {
		return;
	}

	@Override
	public boolean isInvincible() {
		return false;
	}

	@Override
	public int getScale() {
		// TODO Auto-generated method stub
		return player.getScale();
	}

	@Override
	public int getState() {
		return player.getState();
	}

}
