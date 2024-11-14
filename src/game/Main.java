package game;

import java.util.HashSet;
import java.util.Set;

import Entities.Enemy;
import Entities.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	// Canvas and GraphicsContext setup
	private final Canvas playerCanvas = new Canvas(1800, 900);
	private final GraphicsContext playerGC = playerCanvas.getGraphicsContext2D();

	private final Canvas enemyCanvas = new Canvas(1800, 900);
	private final GraphicsContext enemyGC = enemyCanvas.getGraphicsContext2D();

	// Window and scene management
	private Stage window;
	private Scene mainMenu;
	private StackPane root = new StackPane();
	private StackPane gameContainer = new StackPane();
	private Scene gameScene = new Scene(root, 1800, 900);
	StackPane bossBarContainer = new StackPane();
	StackPane entityInfoContainer = new StackPane();

	// Sprite paths for player animations
	private final String idleSpritePath = "/player_idleedited.png";
	private final String attack1SpritePath = "./assets/sprite/player/player_attack_1.png";
	private final String attack2SpritePath = "./assets/sprite/player/player_attack_2.png";
	private final String walkSpritePath = "/player_walk.png";
	private final String crouchSpritePath = "./assets/sprite/player/player_duck.png";
	private final String jumpSpritePath = "./assets/sprite/player/player_jump.png";
	private final String fallSpritePath = "./assets/sprite/player/player_fall.png";
	private final String attack3SpritePath = "./assets/sprite/player/player_attack_airborne.png";
	private final String attack4SpritePath = "./assets/sprite/player/player_attack_airborne2.png";

	// Load all sprite images
	private final Image[] spriteImages = { new Image(idleSpritePath), new Image(attack1SpritePath),
			new Image(attack2SpritePath), new Image(walkSpritePath), new Image(crouchSpritePath),
			new Image(jumpSpritePath), // Jump sprite
			new Image(fallSpritePath), // Fall sprite
			new Image(attack3SpritePath), new Image(attack4SpritePath) };

	// Sprite paths for enemy animations
	private final String enemyIdleSpritePath = "./assets/sprite/boss/boss_idle.png";
	private final String enemySpinSpritePath = "./assets/sprite/boss/boss_spin.png";
	private final String enemySpawnSpritePath = "./assets/sprite/boss/boss_spawn.png";

	// Load enemy sprite images
	private final Image[] enemySpriteImages = { new Image(enemyIdleSpritePath), new Image(enemySpinSpritePath),
			new Image(enemySpawnSpritePath) };
	int enemySpriteIdx = 2;
	// Sprite sheet configuration
	private static final int SPRITE_COLUMNS = 4;
	private static final int FRAME_WIDTH = 60;
	private static final int FRAME_HEIGHT = 60;

	// Input handling and player state management
	private Set<KeyCode> pressedKeys = new HashSet<>();
	private Player player = new Player();

	// Movement properties
	private double currentSpeedX = 0; // Current horizontal speed
	private final double maxSpeed = 12; // Maximum speed
	private final double acceleration = 20; // Speed increase rate (per second)
	private final double deceleration = 30; // Speed decrease rate

	// Jump and gravity mechanics
	private double verticalVelocity = 0; // Current vertical velocity
	private final double jumpStrength = 20; // Jumping power
	private final double gravity = 0.5; // Gravity pull

	// Enemy instance
	private Enemy enemy = new Enemy();

	// Game constants
	private final int groundLevel = 580; // The Y-level representing the ground

	// Background pane setup
	private StackPane bgPane = new StackPane();

	// Audio paths and MediaPlayer setup
	private final String swordSlashAudioPath = "../assets/audio/sfx/swordswing1.wav";
	private Media swordSwingMedia = new Media(getClass().getResource(swordSlashAudioPath).toExternalForm());
	private MediaPlayer swordSwingAudio = new MediaPlayer(swordSwingMedia);

	private final String walkAudioPath = "../assets/audio/sfx/swordswing1.wav"; // Update if needed
	private Media walkMedia = new Media(getClass().getResource(walkAudioPath).toExternalForm());
	private MediaPlayer walkAudio = new MediaPlayer(walkMedia);

	// MediaView setup (for video backgrounds)
	private MediaView mediaView;

	// Scaling factor for player size
	private final int playerSizeMultiplier = 4;
	private final int enemySizeMultiplier = 4;

	// Player movement boundaries
	private final double minX = 300; // Minimum X position (left boundary)
	private final double maxX = 1300; // Maximum X position (right boundary)

	private String bgMusicPath = "../assets/audio/song/tokyobluesloop.mp3"; // Replace with your file path
	private Media bgMusicMedia = new Media(getClass().getResource(bgMusicPath).toExternalForm());
	private MediaPlayer backgroundMusic = new MediaPlayer(bgMusicMedia);

	@Override
	public void start(Stage stage) {
		window = stage;
		setMainMenu();
		window.setFullScreen(true);
		window.show();
	}

	private void drawGuideLine(Canvas c, GraphicsContext gc, double y) {
		gc.setStroke(Color.RED); // Set the color of the line
		gc.setLineWidth(2); // Set the width of the line
		// Draw a vertical line from top to bottom
		double xPosition = 100; // Change this to your desired X position
		gc.strokeLine(xPosition, 0, xPosition, c.getHeight());
		; // Draw a line from the left to the right
	}

	private void setMainMenu() {
		Image menuImage = new Image("assets/sprite/main_menu.png");
		BackgroundImage bgImage = new BackgroundImage(menuImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));

		VBox mainContainer = new VBox();
		mainContainer.setBackground(new Background(bgImage));

		Button playButton = UIFactory.makeButton("Play");
		Button optionsButton = UIFactory.makeButton("Options");
		Button quitButton = UIFactory.makeButton("Quit to Desktop");

		Button headerLabel = UIFactory.makeButton("PROJECT FLA WKWKW", 70);
		headerLabel.setPadding(new Insets(80));

		mainContainer.getChildren().addAll(headerLabel, playButton, optionsButton, quitButton);
		mainContainer.setAlignment(Pos.CENTER);

		playButton.setOnAction(e -> setGameScene());
		quitButton.setOnAction(e -> window.close());

		mainMenu = new Scene(mainContainer);
		window.setScene(mainMenu);
	}

	private void setGameScene() {
		// Set up media player with the video file
		String bgPath = "../assets/sprite/scene/battleBG.mp4";
		String enemyHealthBarPath = "./assets/sprite/ui/boss_healthbar.png";

		Media media = new Media(getClass().getResource(bgPath).toExternalForm());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaView = new MediaView(mediaPlayer);

		Image enemyBarImage = new Image(enemyHealthBarPath);
		ImageView enemyBarImgView = new ImageView(enemyBarImage);

		enemyBarImgView.setFitWidth(root.getWidth() * 0.5);
		enemyBarImgView.setPreserveRatio(true);

		Rectangle bossBarLayer1 = new Rectangle(enemyBarImgView.getFitWidth() * 0.92, 40, Color.web("#992222"));
		Rectangle bossBarLayer2 = new Rectangle(enemyBarImgView.getFitWidth() * 0.92, 20, Color.web("#b52a2a"));

//		StackPane bossHealthContainer = new StackPane();
//		bossHealthContainer.setPrefHeight(40);
//		bossHealthContainer.setPrefWidth(enemyBarImgView.getFitWidth() * 0.92);
//		bossHealthContainer.getChildren().addAll(bossBarLayer1, bossBarLayer2);
//		bossHealthContainer.setAlignment(Pos.CENTER_LEFT);

		bossBarContainer.getChildren().addAll(bossBarLayer1, bossBarLayer2, enemyBarImgView,
				UIFactory.makeLabel("enemy.getName()", 20));
		bossBarContainer.setTranslateY(-280);

		String playerHealthBarPath = "./assets/sprite/ui/player_healthbar.png";
		Image playerBarImage = new Image(playerHealthBarPath);

		ImageView playerBarImgView = new ImageView(playerBarImage);
		playerBarImgView.setFitWidth(root.getWidth() * 0.2);
		playerBarImgView.setPreserveRatio(true);

		StackPane playerBarContainer = new StackPane();
		VBox playerHealth = new VBox();
		playerHealth.getChildren().addAll(new Rectangle(playerBarImgView.getFitWidth(), 24, Color.GREENYELLOW),
				new Rectangle(playerBarImgView.getFitWidth(), 24, Color.DARKCYAN));
		playerBarContainer.getChildren().addAll(playerHealth, playerBarImgView);
		playerBarContainer.setAlignment(Pos.CENTER_LEFT);

		VBox playerInfoContainer = new VBox();
		VBox topBox = new VBox();
		topBox.setPrefHeight(root.getHeight() * 0.9);
		playerInfoContainer.setPrefHeight(root.getHeight() - topBox.getHeight());
		playerInfoContainer.setTranslateX(100);
		playerInfoContainer.setPrefWidth(root.getWidth() / 2);
		playerInfoContainer.getChildren().addAll(topBox, UIFactory.makeLabel("player.getName()", 13),
				playerBarContainer);

		entityInfoContainer.getChildren().addAll(bossBarContainer, playerInfoContainer);

		backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);

		// Set the size of the MediaView
		mediaView.setFitWidth(gameScene.getWidth());
		mediaView.setFitHeight(gameScene.getHeight()); // Set to desired height
		mediaView.setPreserveRatio(true); // Preserve aspect ratio
		mediaView.setTranslateY(-50);

		mediaPlayer.setOnError(() -> {
			System.out.println("Error loading media: " + mediaPlayer.getError().getMessage());
		});

		mediaPlayer.setOnReady(() -> {
			mediaPlayer.play(); // Play the video when it's ready
		});

		gameScene.setFill(Color.BLACK);
		bgPane.setPrefWidth(gameContainer.getWidth());
		bgPane.setPrefHeight(gameContainer.getHeight());
		bgPane.setAlignment(Pos.CENTER);

		// Add media view to the gameContainer stack pane
		gameContainer.setAlignment(Pos.CENTER);
		gameContainer.getChildren().addAll(mediaView, enemyCanvas, playerCanvas);
		gameContainer.setPrefWidth(2000);
		gameContainer.setStyle("-fx-background-color : black;");

		root.getChildren().addAll(gameContainer, entityInfoContainer);
		root.setStyle("-fx-background-color : black;");
		root.setPrefHeight(gameContainer.getHeight());
		root.setPrefWidth(gameContainer.getWidth());
		root.setAlignment(Pos.CENTER);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the video

		mediaPlayer.play(); // Start playing the video
		backgroundMusic.play();

		initializeAnimations();
		window.setScene(gameScene);
		window.setFullScreen(true);

		gameScene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
		gameScene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
	}

	private void initializeAnimations() {

		final int[] idleFrame = { 0 };
		final int[] walkFrame = { 0 };
		final int[] attackFrame = { 0 };
		final int[] crouchFrame = { 0 };

		final int[] enemyIdleFrame = { 0 };
		final int[] enemySpawnFrame = { 0 };

		Timeline idleAnimation = createIdleAnimation(idleFrame);
		Timeline walkAnimation = createWalkAnimation(walkFrame);
		Timeline crouchAnimation = createCrouchAnimation(crouchFrame);
		crouchAnimation.setCycleCount(Animation.INDEFINITE);
		Timeline attackAnimation = createAttackAnimation(attackFrame);
		attackAnimation.setCycleCount(8);
		Timeline enemyIdleAnimation = createEnemyIdleAnimation(enemyIdleFrame);
		Timeline enemySpawnToIdleAnimation = createEnemySpawnToIdleAnimation(enemySpawnFrame);
		enemySpawnToIdleAnimation.setOnFinished(event -> {
			enemySpawnToIdleAnimation.stop();
			enemySpawnFrame[0] = 0;
			enemy.setSpawning(false);
			enemySpriteIdx = 0;
			enemyIdleAnimation.play();
		});
		enemySpawnToIdleAnimation.play();
		Timeline movementTimeline = new Timeline(new KeyFrame(Duration.millis(16),
				event -> updateGameState(idleFrame, walkFrame, attackFrame, crouchFrame, enemyIdleFrame,
						enemySpawnFrame, idleAnimation, walkAnimation, attackAnimation, crouchAnimation,
						enemyIdleAnimation, enemySpawnToIdleAnimation)));
		movementTimeline.setCycleCount(Animation.INDEFINITE);
		movementTimeline.play();

	}

	private Timeline createIdleAnimation(int[] idleFrame) {
		return new Timeline(new KeyFrame(Duration.millis(150), event -> {
			if (!pressedKeys.contains(KeyCode.SPACE)) { // Play idle only if not attacking
				idleFrame[0] = (idleFrame[0] + 1) % SPRITE_COLUMNS;
			}
		}));
	}

	private Timeline createWalkAnimation(int[] walkFrame) {
		return new Timeline(new KeyFrame(Duration.millis(50), event -> {
			walkFrame[0] = (walkFrame[0] + 1) % 8;
		}));
	}

	private Timeline createAttackAnimation(int[] attackFrame) {
		final int ATTACK_FRAMES = 8;
		return new Timeline(new KeyFrame(Duration.millis(23), event -> {
			attackFrame[0]++;

		}));
	}

	private Timeline createCrouchAnimation(int[] crouchFrame) {
		final int CROUCH_FRAMES = 2;
		return new Timeline(new KeyFrame(Duration.millis(150), event -> {
			if (!player.isCrouching()) {
				crouchFrame[0] = 2;
			} else
				crouchFrame[0] = (crouchFrame[0] + 1) % CROUCH_FRAMES;
		}));
	}

	private Timeline createEnemyIdleAnimation(int[] enemyIdleFrames) {
		final int ENEMY_IDLE_FRAMES = 8;
		Timeline enemyIdleAnimation = new Timeline(new KeyFrame(Duration.millis(150), event -> {
			enemyIdleFrames[0] = (enemyIdleFrames[0] + 1) % ENEMY_IDLE_FRAMES;
		}));

		enemyIdleAnimation.setCycleCount(Animation.INDEFINITE);
		return enemyIdleAnimation;
	}

	private Timeline createEnemySpawnToIdleAnimation(int[] enemySpawnFrames) {
		final int SPAWN_FRAMES = 6;
		Timeline spawnAnimation = new Timeline(new KeyFrame(Duration.millis(150), event -> {
			enemySpawnFrames[0]++;
		}));
		spawnAnimation.setCycleCount(SPAWN_FRAMES - 1);
		return spawnAnimation;
	}

	private void updateGameState(int[] idleFrame, int[] walkFrame, int[] attackFrame, int[] crouchFrame,
			int[] enemyIdleFrame, int[] enemySpawnFrame, Timeline idleAnimation, Timeline walkAnimation,
			Timeline attackAnimation, Timeline crouchAnimation, Timeline enemyIdleAnimation,
			Timeline enemySpawnAnimation) {
		playerGC.clearRect(0, 0, 2000, 2000);
		enemyGC.clearRect(0, 0, 2000, 2000);

		handlePlayerMovement();
		handleEnemyMovement();

		handlePlayerAnimations(idleFrame, walkFrame, attackFrame, crouchFrame, idleAnimation, walkAnimation,
				attackAnimation, crouchAnimation);
		handleEnemyAnimations(enemyIdleFrame, enemySpawnFrame, enemyIdleAnimation, enemySpawnAnimation);

		handlePlayerAudio();
	}

	private void handlePlayerAudio() {
		if (player.isWalking()) {

		}
	}

	private void handlePlayerMovement() {
		// Update current speed based on key presses
		if (pressedKeys.contains(KeyCode.D) && player.getX() < maxX) {
			currentSpeedX += acceleration * 0.03;
			player.setWalking(true);// Increase speed
		} else if (pressedKeys.contains(KeyCode.A) && player.getX() > minX) {
			currentSpeedX -= acceleration * 0.016;
			player.setWalking(true);// Decrease speed
		} else {
			player.setWalking(false);
			if (currentSpeedX < 0.7 && currentSpeedX > -0.7) {
				currentSpeedX = 0;
			}
			// Apply friction when no keys are pressed
			else if (currentSpeedX > 0) {
				currentSpeedX -= deceleration * 0.016; // Slow down
			} else if (currentSpeedX < 0) {
				currentSpeedX += deceleration * 0.016; // Slow down
			}
		}
		// Clamp the speed to max speed
		currentSpeedX = Math.max(-maxSpeed, Math.min(currentSpeedX, maxSpeed));

		// Handle jumping
		if (pressedKeys.contains(KeyCode.W) && !player.isJumping()) {
			verticalVelocity = -jumpStrength; // Set upward velocity
			player.setJumping(true); // Player is now jumping
		}

		// Apply gravity
		if (player.isJumping()) {
			verticalVelocity += gravity; // Increase downward velocity
			player.move(0, verticalVelocity); // Move player vertically
		}

		// Move player horizontally based on current speed
		player.move(currentSpeedX, 0); // Horizontal movement based on current speed

		// Check if player has landed (simple ground collision)
		if (player.getY() >= groundLevel) { // Assuming groundLevel is defined
			player.setY(groundLevel); // Reset player to ground level
			player.setJumping(false); // Player can jump again
			verticalVelocity = 0; // Reset vertical velocity
		}

		double parallaxXFactor = 0.2; // Adjust this value to control the speed of the background movement
		gameContainer.setTranslateX((-player.getX() * parallaxXFactor) + 140);
		entityInfoContainer.setTranslateX(player.getX() * parallaxXFactor / 10);
		double parallaxYFactor = 0.2; // Adjust this value to control the speed of the background movement
		gameContainer.setTranslateY((-player.getY() * parallaxYFactor) + 130);
	}

	private void handleEnemyMovement() {
		if (enemy.getX() > player.getX()) {
//			enemy.move(-2, 0); // Move left toward the player
		} else {
//			enemy.move(2, 0); // Move right toward the player
		}
	}

	private void handleEnemyAnimations(int[] enemyIdleFrame, int[] enemySpawnFrame, Timeline enemyIdleAnimation,
			Timeline enemySpawnAnimation) {
		// Save the current state of the GraphicsContext
		enemyGC.save();
		enemyGC.translate(enemy.getX(), enemy.getY() - 250); // Adjusting Y position as per your requirement

		// Draw frames on canvas
		if (enemy.isSpawning()) {
			handleEnemyCanvas(enemySpawnFrame);
		} else {
			handleEnemyCanvas(enemyIdleFrame);
		}

		// Restore the state of the GraphicsContext
		enemyGC.restore();
	}

	private void handleEnemyCanvas(int[] spawnFrame) {
		if (enemy.getX() > player.getX()) {
			// Draw normally if the enemy is facing right
			enemyGC.drawImage(enemySpriteImages[enemySpriteIdx], spawnFrame[0] * 120, 0, 120, 120, 0, 0,
					120 * enemySizeMultiplier, 120 * enemySizeMultiplier);
		} else {
			// Flip horizontally if the enemy is to the left of the player
			enemyGC.scale(-1, 1); // Flip horizontally
			enemyGC.drawImage(enemySpriteImages[enemySpriteIdx], spawnFrame[0] * 120, 0, 120, 120,
					-120 * enemySizeMultiplier, 0, 120 * enemySizeMultiplier, 120 * enemySizeMultiplier);
		}
	}

	private void handlePlayerAnimations(int[] idleFrame, int[] walkFrame, int[] attackFrame, int[] crouchFrame,
			Timeline idleAnimation, Timeline walkAnimation, Timeline attackAnimation, Timeline crouchAnimation) {
		playerGC.save();
		if ((!pressedKeys.contains(KeyCode.W) && !pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.S)
				&& !pressedKeys.contains(KeyCode.D)) && !player.isAttacking() && !player.isJumping()) {
			playerGC.drawImage(spriteImages[0], idleFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getX(),
					player.getY(), FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
		}

		if (pressedKeys.contains(KeyCode.D) && !player.isAttacking() && !player.isJumping() && !player.isCrouching()) {
			walkAnimation.play();
			playerGC.drawImage(spriteImages[3], walkFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getX(),
					player.getY(), FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
		} else if (pressedKeys.contains(KeyCode.A) && !player.isAttacking() && !player.isJumping()
				&& !player.isCrouching()) {
			walkAnimation.play();
			// Save the current state of the GraphicsContext
			playerGC.translate(player.getX() + FRAME_WIDTH * playerSizeMultiplier, player.getY()); // Move to player's
																									// position
			playerGC.scale(-1, 1); // Flip horizontally
			playerGC.drawImage(spriteImages[3], walkFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, 0, 0,
					FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);

		} else {
			walkAnimation.stop();
			idleAnimation.play();
		}

		if (pressedKeys.contains(KeyCode.S) && !player.isJumping()) {
			player.setCrouching(true);
			crouchAnimation.play();
			playerGC.drawImage(spriteImages[4], crouchFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT,
					player.getX(), player.getY(), FRAME_WIDTH * playerSizeMultiplier,
					FRAME_HEIGHT * playerSizeMultiplier);
		} else {
			player.setCrouching(false);
		}

		if (pressedKeys.contains(KeyCode.SPACE) && !player.isAttacking()) {
			player.setAtkIdx(((player.getAtkIdx() + 1) % 2));
			player.setAttacking(true); // Set attacking state
			idleAnimation.pause(); // Pause idle animation
			attackFrame[0] = 0; // Reset attack frame
			attackAnimation.play(); // Play attack animation
		}

		if (!player.isAttacking()) {
			swordSwingAudio.stop();
			attackAnimation.stop();
			attackFrame[0] = 0;
		} else {
			if (player.isJumping()) {
				playerGC.drawImage(spriteImages[7 + player.getAtkIdx()], attackFrame[0] * 80, 0, 80, FRAME_HEIGHT,
						player.getX() - 40, player.getY(), 80 * playerSizeMultiplier,
						FRAME_HEIGHT * playerSizeMultiplier);
			} else {
				playerGC.drawImage(spriteImages[player.getAtkIdx() + 1], attackFrame[0] * 80, 0, 80, FRAME_HEIGHT,
						player.getX() - 40, player.getY(), 80 * playerSizeMultiplier,
						FRAME_HEIGHT * playerSizeMultiplier);
			}
		}

		if (attackFrame[0] >= 8) {
			attackFrame[0] = 0; // Reset attack frame index
			player.setAttacking(false); // Reset attack state
			attackAnimation.stop(); // Stop the attack animation
			swordSwingAudio.stop();
		} else if (attackFrame[0] == 0 && player.isAttacking()) {
			swordSwingAudio.play();
		}

		if (player.isJumping() && !player.isAttacking()) {
			// Check if player is jumping or falling based on vertical velocity
			if (verticalVelocity < 0) { // Jumping
				playerGC.drawImage(spriteImages[5], 0, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getX(), player.getY(),
						FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier); // Jump sprite
			} else { // Falling
				playerGC.drawImage(spriteImages[6], 0, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getX(), player.getY(),
						FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier); // Fall sprite
			}
		}
		playerGC.restore(); // Restore the state
	}

	public static void main(String[] args) {
		launch();
	}
}
