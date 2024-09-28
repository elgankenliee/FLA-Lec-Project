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
import javafx.stage.Stage;
import javafx.util.Duration;

class GameBackground {
	double x = 0; // Player's x position
	double y = 580; // Player's y position

	void move(double dx, double dy) {
		x += dx;
		y += dy;
	}
}

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
	private Scene gameScene = new Scene(root, 1800, 900);

	// Sprite paths for player animations
	private final String idleSpritePath = "/player_idleedited.png";
	private final String attack1SpritePath = "./assets/sprite/player/player_attack_1.png";
	private final String attack2SpritePath = "./assets/sprite/player/player_attack_2.png";
	private final String walkSpritePath = "/player_walk.png";
	private final String crouchSpritePath = "./assets/sprite/player/player_duck.png";
	private final String jumpSpritePath = "./assets/sprite/player/player_jump.png";
	private final String fallSpritePath = "./assets/sprite/player/player_fall.png";
	private final String attack3SpritePath = "./assets/sprite/player/player_attack_airborne.png";

	// Load all sprite images
	private final Image[] spriteImages = { new Image(idleSpritePath), new Image(attack1SpritePath),
			new Image(attack2SpritePath), new Image(walkSpritePath), new Image(crouchSpritePath),
			new Image(jumpSpritePath), // Jump sprite
			new Image(fallSpritePath), // Fall sprite
			new Image(attack3SpritePath) };

	// Sprite paths for enemy animations
	private final String enemyIdleSpritePath = "./assets/sprite/boss/boss_idle.png";
	private final String enemySpinSpritePath = "./assets/sprite/boss/boss_spin.png";

	// Load enemy sprite images
	private final Image[] enemySpriteImages = { new Image(enemyIdleSpritePath), new Image(enemySpinSpritePath) };

	// Sprite sheet configuration
	private static final int SPRITE_COLUMNS = 4;
	private static final int FRAME_WIDTH = 60;
	private static final int FRAME_HEIGHT = 60;

	// Input handling and player state management
	private Set<KeyCode> pressedKeys = new HashSet<>();
	private Player player = new Player();
	private boolean isCrouching = false;
	private boolean isJumping = false; // Track if the player is jumping
	private boolean isWalking = false;
	private Boolean[] isAttacking = { false }; // Track attacks
	private int atkIdx = 1; // Attack index for combos

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

		Button headerLabel = UIFactory.makeButton("The Nigga Slasher", 70);
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
		Media media = new Media(getClass().getResource(bgPath).toExternalForm());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaView = new MediaView(mediaPlayer);
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

//		Rectangle rect = new Rectangle(100, 100, Color.BLUE);
//		Rectangle rec2 = new Rectangle(100, 100, Color.RED);
		root.setAlignment(Pos.CENTER);
		gameScene.setFill(Color.BLACK);
		bgPane.setPrefWidth(root.getWidth());
		bgPane.setPrefHeight(root.getHeight());
		bgPane.setAlignment(Pos.CENTER);

		// Add media view to the root stack pane
		root.getChildren().addAll(mediaView, playerCanvas, enemyCanvas);
		root.setPrefWidth(2000);
		root.setStyle("-fx-background-color : black;");
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

		Timeline idleAnimation = createIdleAnimation(idleFrame);
		Timeline walkAnimation = createWalkAnimation(walkFrame);
		Timeline crouchAnimation = createCrouchAnimation(crouchFrame);
		crouchAnimation.setCycleCount(Animation.INDEFINITE);
		Timeline attackAnimation = createAttackAnimation(attackFrame);
		attackAnimation.setCycleCount(8);
		Timeline enemyIdleAnimation = createEnemyIdleAnimation(enemyIdleFrame);
		enemyIdleAnimation.play();

		Timeline movementTimeline = new Timeline(new KeyFrame(Duration.millis(16),
				event -> updateGameState(idleFrame, walkFrame, attackFrame, crouchFrame, enemyIdleFrame, idleAnimation,
						walkAnimation, attackAnimation, crouchAnimation, enemyIdleAnimation)));
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
			if (isAttacking[0]) {

				attackFrame[0]++;
			}
		}));
	}

	private Timeline createCrouchAnimation(int[] crouchFrame) {
		final int CROUCH_FRAMES = 2;
		return new Timeline(new KeyFrame(Duration.millis(150), event -> {
			if (!isCrouching) {
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

	private void updateGameState(int[] idleFrame, int[] walkFrame, int[] attackFrame, int[] crouchFrame,
			int[] enemyIdleFrame, Timeline idleAnimation, Timeline walkAnimation, Timeline attackAnimation,
			Timeline crouchAnimation, Timeline enemyIdleAnimation) {
		playerGC.clearRect(0, 0, 2000, 2000);
		enemyGC.clearRect(0, 0, 2000, 2000);

//		System.out.println(currentSpeedX);
//		System.out.println(acceleration);

//		drawGuideLine(playerCanvas, playerGC, playerCanvas.getHeight()); // For player
//		drawGuideLine(enemyCanvas, enemyGC, playerCanvas.getHeight()); // For enemy

		handlePlayerMovement();
		handleEnemyMovement();

		handlePlayerAnimations(idleFrame, walkFrame, attackFrame, crouchFrame, idleAnimation, walkAnimation,
				attackAnimation, crouchAnimation);
		handleEnemyAnimations(enemyIdleFrame, enemyIdleAnimation);

		handlePlayerAudio();
	}

	private void handlePlayerAudio() {
		if (isWalking) {

		}
	}

	private void handlePlayerMovement() {
		// Update current speed based on key presses
		if (pressedKeys.contains(KeyCode.D) && player.getX() < maxX) {
			currentSpeedX += acceleration * 0.03;
			isWalking = true;// Increase speed
		} else if (pressedKeys.contains(KeyCode.A) && player.getX() > minX) {
			currentSpeedX -= acceleration * 0.016;
			isWalking = true;// Decrease speed
		} else {
			isWalking = false;
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
		if (pressedKeys.contains(KeyCode.W) && !isJumping) {
			verticalVelocity = -jumpStrength; // Set upward velocity
			isJumping = true; // Player is now jumping
		}

		// Apply gravity
		if (isJumping) {
			verticalVelocity += gravity; // Increase downward velocity
			player.move(0, verticalVelocity); // Move player vertically
		}

		// Move player horizontally based on current speed
		player.move(currentSpeedX, 0); // Horizontal movement based on current speed

		// Check if player has landed (simple ground collision)
		if (player.getY() >= groundLevel) { // Assuming groundLevel is defined
			player.setY(groundLevel); // Reset player to ground level
			isJumping = false; // Player can jump again
			verticalVelocity = 0; // Reset vertical velocity
		}

		double parallaxXFactor = 0.2; // Adjust this value to control the speed of the background movement
		root.setTranslateX((-player.getX() * parallaxXFactor) + 140);

		double parallaxYFactor = 0.2; // Adjust this value to control the speed of the background movement
		root.setTranslateY((-player.getY() * parallaxYFactor) + 50);
	}

	private void handleEnemyMovement() {
		if (enemy.getX() > player.getX()) {
//			enemy.move(-2, 0); // Move left toward the player
		} else {
//			enemy.move(2, 0); // Move right toward the player
		}
	}

	private void handleEnemyAnimations(int[] enemyIdleFrame, Timeline enemyIdleAnimation) {
		// Save the current state of the GraphicsContext
		enemyGC.save();

		// Move to the enemy's position
		enemyGC.translate(enemy.getX(), enemy.getY() - 250); // Adjusting Y position as per your requirement

		// Check if the enemy is to the right of the player
		if (enemy.getX() > player.getX()) {
			// Draw normally if the enemy is facing right
			enemyGC.drawImage(enemySpriteImages[0], enemyIdleFrame[0] * 120, 0, 120, 120, 0, 0,
					120 * enemySizeMultiplier, 120 * enemySizeMultiplier);
		} else {
			// Flip horizontally if the enemy is to the left of the player
			enemyGC.scale(-1, 1); // Flip horizontally
			enemyGC.drawImage(enemySpriteImages[0], enemyIdleFrame[0] * 120, 0, 120, 120, -120 * enemySizeMultiplier, 0,
					120 * enemySizeMultiplier, 120 * enemySizeMultiplier);
		}

		// Restore the state of the GraphicsContext
		enemyGC.restore();
	}

	private void handlePlayerAnimations(int[] idleFrame, int[] walkFrame, int[] attackFrame, int[] crouchFrame,
			Timeline idleAnimation, Timeline walkAnimation, Timeline attackAnimation, Timeline crouchAnimation) {
		playerGC.save();
		if ((!pressedKeys.contains(KeyCode.W) && !pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.S)
				&& !pressedKeys.contains(KeyCode.D)) && !isAttacking[0] && !isJumping) {
			playerGC.drawImage(spriteImages[0], idleFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getX(),
					player.getY(), FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
		}

		if (pressedKeys.contains(KeyCode.D) && !isAttacking[0] && !isJumping && !isCrouching) {
			walkAnimation.play();
			playerGC.drawImage(spriteImages[3], walkFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getX(),
					player.getY(), FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
		} else if (pressedKeys.contains(KeyCode.A) && !isAttacking[0] && !isJumping && !isCrouching) {
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

		if (pressedKeys.contains(KeyCode.S) && !isJumping) {
			isCrouching = true;
			crouchAnimation.play();
			playerGC.drawImage(spriteImages[4], crouchFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT,
					player.getX(), player.getY(), FRAME_WIDTH * playerSizeMultiplier,
					FRAME_HEIGHT * playerSizeMultiplier);
		} else {
			isCrouching = false;
		}

		if (pressedKeys.contains(KeyCode.SPACE) && !isAttacking[0]) {
			atkIdx = ((atkIdx + 1) % 2);
			isAttacking[0] = true; // Set attacking state
			idleAnimation.pause(); // Pause idle animation
			attackFrame[0] = 0; // Reset attack frame
			attackAnimation.play(); // Play attack animation
		}

		if (!isAttacking[0]) {
			swordSwingAudio.stop();
			attackAnimation.stop();
			attackFrame[0] = 0;
		} else {
			if (isJumping) {
				playerGC.drawImage(spriteImages[7], attackFrame[0] * 80, 0, 80, FRAME_HEIGHT, player.getX() - 40,
						player.getY(), 80 * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
			} else {
				playerGC.drawImage(spriteImages[atkIdx + 1], attackFrame[0] * 80, 0, 80, FRAME_HEIGHT,
						player.getX() - 40, player.getY(), 80 * playerSizeMultiplier,
						FRAME_HEIGHT * playerSizeMultiplier);
			}
		}

		if (attackFrame[0] >= 8) {
			attackFrame[0] = 0; // Reset attack frame index
			isAttacking[0] = false; // Reset attack state
			attackAnimation.stop(); // Stop the attack animation
			swordSwingAudio.stop();
		} else if (attackFrame[0] == 0 && isAttacking[0]) {
			swordSwingAudio.play();
		}

		if (isJumping && !isAttacking[0]) {
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
