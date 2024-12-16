package game.managers;

import game.controllers.AnimationController;
import game.controllers.AudioController;
import game.controllers.NPCMovementController;
import game.core.animations.CharacterAnimation;
import game.core.animations.IAnimation;
import game.core.audio.Audio;
import game.core.audio.IAudio;
import game.core.constants.BossStateEnum;
import game.core.interfaces.CharacterContext;
import game.core.interfaces.FXBehaviour;
import game.core.models.Enemy;
import game.core.models.Vector2D;
import game.core.states.boss.BossState;
import game.core.states.boss.SpawnLevitateState;

public class EnemyManager implements CharacterContext, FXBehaviour {
	private Enemy enemy;
	private NPCMovementController movementController;
	private AnimationController animationController;
	private AudioController audioController;
	private BossState currentState;

	public EnemyManager(Enemy enemy) {
		this.enemy = enemy;
		this.movementController = new NPCMovementController(enemy.getRb());
		this.animationController = new AnimationController();
		this.audioController = new AudioController();
		initializeAnimations();
		initializeAudio();
		this.currentState = new SpawnLevitateState();
	}

	@Override
	public void start() {
		currentState.start(this);
	}

	@Override
	public void update() {
		if (currentState != null) {
			currentState.update(this);
		}
		movementController.update(enemy.getPos());
		animationController.update(System.currentTimeMillis());
	}

	public void initializeAnimations() {
		animationController.addAnimation(BossStateEnum.SPAWN,
				new CharacterAnimation("src/assets/sprite/boss/boss_spawn.png", 5, 60, 120, 120));
		animationController.addAnimation(BossStateEnum.LEVITATE,
				new CharacterAnimation("src/assets/sprite/boss/boss_levitate.png", 6, 90, 120, 120));
		animationController.addAnimation(BossStateEnum.SPAWN | BossStateEnum.LEVITATE,
				new CharacterAnimation("src/assets/sprite/boss/boss_levitate_spawn.png", 5, 60, 120, 120));
		animationController.addAnimation(BossStateEnum.IDLE,
				new CharacterAnimation("src/assets/sprite/boss/boss_idle.png", 8, 120, 120, 120));
		animationController.addAnimation(BossStateEnum.ATTACK | BossStateEnum.SPIN,
				new CharacterAnimation("src/assets/sprite/boss/boss_spin.png", 9, 30, 120, 120));
		animationController.addAnimation(BossStateEnum.ATTACK | BossStateEnum.PRE_DASH,
				new CharacterAnimation("src/assets/sprite/boss/boss_predash.png", 3, 60, 120, 120));
		animationController.addAnimation(BossStateEnum.ATTACK | BossStateEnum.DASH,
				new CharacterAnimation("src/assets/sprite/boss/boss_dash.png", 1, 60, 120, 120));
		animationController.addAnimation(BossStateEnum.IDLE | BossStateEnum.SPLIT_SWORD,
				new CharacterAnimation("src/assets/sprite/boss/boss_split_sword.png", 6, 30, 120, 120));
		animationController.addAnimation(BossStateEnum.IDLE | BossStateEnum.DESPAWN,
				new CharacterAnimation("src/assets/sprite/boss/boss_idle_despawn.png", 6, 30, 120, 120));
	}

	public void initializeAudio() {

		audioController.addAudio(BossStateEnum.DESPAWN, new Audio("src/assets/audio/sfx/boss_disappear.wav", 0.1f));
		audioController.addAudio(BossStateEnum.ATTACK | BossStateEnum.DASH,
				new Audio("src/assets/audio/sfx/boss_dash.wav"));
		audioController.addAudio(BossStateEnum.SPAWN | BossStateEnum.LEVITATE,
				new Audio("src/assets/audio/sfx/boss_reappear.wav"));
		audioController.addAudio(BossStateEnum.LEVITATE, new Audio("src/assets/audio/sfx/boss_flame.wav"));
		audioController.addAudio(BossStateEnum.SPIN | BossStateEnum.ATTACK,
				new Audio("src/assets/audio/sfx/boss_spin.wav", 0.6));
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
		if (currentState != null) {
			currentState.exit(this);
		}
		currentState = newState;
		currentState.start(this);
	}

	@Override
	public IAudio getCurrentSound() {
		// TODO Auto-generated method stub
		return this.audioController.getCurrentSound();
	}

	@Override
	public void setSound(int soundId) {
		this.audioController.setCurrentSound(soundId);

	}

	@Override
	public Vector2D getPos() {
		return this.enemy.getPos();
	}

	@Override
	public Vector2D[] getHitbox() {
		return this.enemy.getHitbox();
	}

	@Override
	public void updateHealth(int delta) {
		this.enemy.updateHealth(delta);
	}

	@Override
	public void setInvincible(boolean isInvincible) {
		this.enemy.setInvincible(isInvincible);
	}

	@Override
	public boolean isInvincible() {
		return enemy.isInvincible();
	}

	@Override
	public int getState() {
		return Integer.MIN_VALUE;
	}

	@Override
	public int getScale() {
		// TODO Auto-generated method stub
		return enemy.getScale();
	}

}
