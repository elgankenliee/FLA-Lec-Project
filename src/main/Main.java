package main;

import java.util.HashSet;
import java.util.Set;

import game.UIFactory;
import game.controllers.MovementController;
import game.core.animations.IAnimation;
import game.core.animations.player.PlayerAnimation;
import game.core.constants.PlayerState;
import game.core.models.Position;
import game.core.models.entities.Enemy;
import game.core.models.entities.Player;
import game.managers.PlayerManager;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
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


	// Sprite paths for enemy animations
	private final String enemyIdleSpritePath = "./assets/sprite/boss/boss_idle.png";
	private final String enemySpinSpritePath = "./assets/sprite/boss/boss_spin.png";
	private final String enemySpawnSpritePath = "./assets/sprite/boss/boss_spawn.png";

	// Load enemy sprite images
	private final Image[] enemySpriteImages = { new Image(enemyIdleSpritePath), new Image(enemySpinSpritePath),
			new Image(enemySpawnSpritePath) };
	int enemySpriteIdx = 2;

	private Player player = new Player(
			100,
			new Position(350, 580),
			PlayerState.IDLE
		);
	private PlayerManager playerManager = new PlayerManager(player);


	// Enemy instance
	private Enemy enemy = new Enemy(1000);

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

		window.setScene(gameScene);
		window.setFullScreen(true);

		gameScene.setOnKeyPressed(event -> {
			playerManager.addKeyPressed(event.getCode());
			
		});
		gameScene.setOnKeyReleased(event -> {
			playerManager.removeKeyPressed(event.getCode());
		});
		
	    AnimationTimer gameLoop = new AnimationTimer() {
	        private long lastUpdate = 0;

	        @Override
	        public void handle(long now) {
	            if (lastUpdate == 0 || now - lastUpdate >= 16_666_667) { // ~60 FPS
	            	update(); 
//	                renderGame();
	                lastUpdate = now;
	            }
	        }
	    };

	    gameLoop.start();
	}

	
	private void update() {
		playerGC.clearRect(0, 0, 2000, 2000);
		enemyGC.clearRect(0, 0, 2000, 2000);

		playerManager.update();
	    IAnimation currentAnimation = playerManager.getCurrentAnimation();
		

	    if (currentAnimation != null) {
	      final int cropWidth = currentAnimation.getCropWidth();
	      final int cropHeight = currentAnimation.getCropHeight();
	      
	    	int frameX = currentAnimation.getCurrentFrame() * cropWidth;
        int width = cropWidth;
	        
        if (playerManager.getDirection() < 0) {
        	frameX += cropWidth;
        	width = -width;
        }

        playerGC.drawImage(
            currentAnimation.getSpriteImage(),
            frameX, 0, width, cropHeight,
            player.getPos().getX(), player.getPos().getY(),
            cropWidth * playerSizeMultiplier,
            cropHeight * playerSizeMultiplier 
        );
	    }
		

	}

//	private void handlePlayerAudio() {
//
//	}


//	private void handleEnemyMovement() {
//
//	}

//	private void handleEnemyAnimations(int[] enemyIdleFrame, int[] enemySpawnFrame, Timeline enemyIdleAnimation,
//			Timeline enemySpawnAnimation) {
//		// Save the current state of the GraphicsContext
//		enemyGC.save();
//		enemyGC.translate(enemy.getPos().getX(), enemy.getPos().getY() - 250); // Adjusting Y position as per your requirement
//
//		// Draw frames on canvas
//		if (enemy.isSpawning()) {
//			handleEnemyCanvas(enemySpawnFrame);
//		} else {
//			handleEnemyCanvas(enemyIdleFrame);
//		}
//
//		// Restore the state of the GraphicsContext
//		enemyGC.restore();
//	}

//	private void handleEnemyCanvas(int[] spawnFrame) {
//		if (enemy.getPos().getX() > player.getPos().getX()) {
//			// Draw normally if the enemy is facing right
//			enemyGC.drawImage(enemySpriteImages[enemySpriteIdx], spawnFrame[0] * 120, 0, 120, 120, 0, 0,
//					120 * enemySizeMultiplier, 120 * enemySizeMultiplier);
//		} else {
//			// Flip horizontally if the enemy is to the left of the player
//			enemyGC.scale(-1, 1); // Flip horizontally
//			enemyGC.drawImage(enemySpriteImages[enemySpriteIdx], spawnFrame[0] * 120, 0, 120, 120,
//					-120 * enemySizeMultiplier, 0, 120 * enemySizeMultiplier, 120 * enemySizeMultiplier);
//		}
//	}

//	private void handlePlayerAnimations(int[] idleFrame, int[] walkFrame, int[] attackFrame, int[] crouchFrame,
//			Timeline idleAnimation, Timeline walkAnimation, Timeline attackAnimation, Timeline crouchAnimation) {
//		playerGC.save();
		
//		idleAnimation.play();
//		
//		playerGC.drawImage(spriteImages[0], idleFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getPos().getX(),
//				player.getPos().getY(), FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
		

		
//		if ((!pressedKeys.contains(KeyCode.W) && !pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.S)
//				&& !pressedKeys.contains(KeyCode.D)) && !player.hasState(PlayerState.ATTACKING | PlayerState.JUMPING)) {
//			playerGC.drawImage(spriteImages[0], idleFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getPos().getX(),
//					player.getPos().getY(), FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
//		}
//
//		if (pressedKeys.contains(KeyCode.D) && !player.hasState(PlayerState.ATTACKING | PlayerState.JUMPING | PlayerState.CROUCHING)) {
//			walkAnimation.play();
//			playerGC.drawImage(spriteImages[3], walkFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getPos().getX(),
//					player.getPos().getY(), FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
//		} else if (pressedKeys.contains(KeyCode.A) && !player.hasState(PlayerState.ATTACKING | PlayerState.JUMPING | PlayerState.CROUCHING)) {
//			walkAnimation.play();
//			// Save the current state of the GraphicsContext
//			playerGC.translate(player.getPos().getX() + FRAME_WIDTH * playerSizeMultiplier, player.getPos().getY()); // Move to player's
//																									// position
//			playerGC.scale(-1, 1); // Flip horizontally
//			playerGC.drawImage(spriteImages[3], walkFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, 0, 0,
//					FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier);
//
//		} 
//		else {
//			walkAnimation.stop();
//			idleAnimation.play();
//		}
//
//		if (pressedKeys.contains(KeyCode.S) && !player.hasState(PlayerState.JUMPING)) {
//			player.crouch();
//			crouchAnimation.play();
//			playerGC.drawImage(spriteImages[4], crouchFrame[0] * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT,
//					player.getPos().getX(), player.getPos().getY(), FRAME_WIDTH * playerSizeMultiplier,
//					FRAME_HEIGHT * playerSizeMultiplier);
//		} else {
//			player.stopCrouching();
//		}
//
//		if (pressedKeys.contains(KeyCode.SPACE) && !player.hasState(PlayerState.ATTACKING)) {
//			player.setAttackIndex(((player.getAttackIndex() + 1) % 2));
//			player.attack(); // Set attacking state
//			idleAnimation.pause(); // Pause idle animation
//			attackFrame[0] = 0; // Reset attack frame
//			attackAnimation.play(); // Play attack animation
//		}
//
//		if (!player.hasState(PlayerState.ATTACKING)) {
//			swordSwingAudio.stop();
//			attackAnimation.stop();
//			attackFrame[0] = 0;
//		} else {
//			if (player.hasState(PlayerState.JUMPING)) {
//				playerGC.drawImage(spriteImages[7 + player.getAttackIndex()], attackFrame[0] * 80, 0, 80, FRAME_HEIGHT,
//						player.getPos().getX() - 40, player.getPos().getY(), 80 * playerSizeMultiplier,
//						FRAME_HEIGHT * playerSizeMultiplier);
//			} else {
//				playerGC.drawImage(spriteImages[player.getAttackIndex() + 1], attackFrame[0] * 80, 0, 80, FRAME_HEIGHT,
//						player.getPos().getX() - 40, player.getPos().getY(), 80 * playerSizeMultiplier,
//						FRAME_HEIGHT * playerSizeMultiplier);
//			}
//		}
//
//		if (attackFrame[0] >= 8) {
//			attackFrame[0] = 0; // Reset attack frame index
//			player.stopAttacking(); // Reset attack state
//			attackAnimation.stop(); // Stop the attack animation
//			swordSwingAudio.stop();
//		} else if (attackFrame[0] == 0 && player.hasState(PlayerState.ATTACKING)) {
//			swordSwingAudio.play();
//		}
//
//		if (player.hasStateCombination(PlayerState.JUMPING, PlayerState.ATTACKING)) {
//			// Check if player is jumping or falling based on vertical velocity
//			if (verticalVelocity < 0) { // Jumping
//				playerGC.drawImage(spriteImages[5], 0, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getPos().getX(), player.getPos().getY(),
//						FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier); // Jump sprite
//			} else { // Falling
//				playerGC.drawImage(spriteImages[6], 0, 0, FRAME_WIDTH, FRAME_HEIGHT, player.getPos().getX(), player.getPos().getY(),
//						FRAME_WIDTH * playerSizeMultiplier, FRAME_HEIGHT * playerSizeMultiplier); // Fall sprite
//			}
//		}
//		playerGC.restore(); // Restore the state
//	}

	public static void main(String[] args) {
		launch();
	}
}
