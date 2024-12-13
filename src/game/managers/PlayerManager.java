package game.managers;

import game.controllers.AnimationController;
import game.controllers.AudioController;
import game.controllers.MovementController;
import game.core.animations.CharacterAnimation;
import game.core.animations.IAnimation;
import game.core.constants.PlayerStateEnum;
import game.core.interfaces.FXBehaviour;
import game.core.interfaces.VectorMotion;
import game.core.models.Player;
import game.core.sounds.CharacterAudio;
import javafx.scene.input.KeyCode;

public class PlayerManager implements VectorMotion, FXBehaviour {
	private final Player player;
	private int stateCache = 1;
	private Input input;

	private final MovementController movementController;
	private final AnimationController animationController;

	private final AudioController audioController;

	public PlayerManager(Player player) {
		this.player = player;
		this.input = Input.getInstance();
		this.movementController = new MovementController(player.getRb());
		this.animationController = new AnimationController();
		this.audioController = new AudioController();

		initializeAnimations();
		initializeSounds();
	}

	private void initializeAnimations() {
		animationController.addAnimation(PlayerStateEnum.IDLE,
				new CharacterAnimation("src/assets/sprite/player/player_idleedited.png", 4, 150));
		animationController.addAnimation(PlayerStateEnum.WALKING,
				new CharacterAnimation("src/assets/sprite/player/player_walk.png", 8, 45));
		animationController.addAnimation(PlayerStateEnum.JUMPING,
				new CharacterAnimation("src/assets/sprite/player/player_jump.png", 4, 200));
		animationController.addAnimation(PlayerStateEnum.FALLING,
				new CharacterAnimation("src/assets/sprite/player/player_fall.png", 2, 150));
		animationController.addAnimation(PlayerStateEnum.ATTACKING,
				new CharacterAnimation("src/assets/sprite/player/player_attack_1.png", 8, 23, 80, 60));
		animationController.addAnimation(PlayerStateEnum.ATTACKING + 1,
				new CharacterAnimation("src/assets/sprite/player/player_attack_2.png", 8, 23, 80, 60));
		animationController.setCurrentAnimation(PlayerStateEnum.IDLE);
	}

	private void initializeSounds() {
		audioController.addAudio(PlayerStateEnum.JUMPING, new CharacterAudio("src/assets/audio/sfx/jump.wav"));
		audioController.addAudio(PlayerStateEnum.ATTACKING, new CharacterAudio("src/assets/audio/sfx/swordswing1.wav"));

	}

	@Override
	public void update() {
		handleInput();
		handleMovement();
		handleAnimation();
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

		if (player.getState() != stateCache) {
			stateCache = player.getState();
			animationController.setCurrentAnimation(player.getState());
		}

	}

//	private void handleSound() {
//		
//
//	}

	private void handleInput() {
		player.resetState();

		if (input.getKey(KeyCode.SPACE)) {
			player.setState(PlayerStateEnum.ATTACKING);
			audioController.setCurrentSound(PlayerStateEnum.ATTACKING);
			return;
		}

		if (player.getRb().getDelta().getY() > 1) {
			player.setState(PlayerStateEnum.FALLING);

			return;
		}

		if (input.getKey(KeyCode.W)) {
			player.setState(PlayerStateEnum.JUMPING);
			audioController.setCurrentSound(PlayerStateEnum.JUMPING);
			return;
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

}
