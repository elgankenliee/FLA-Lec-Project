package main;

import java.io.File;

import game.UIFactory;
import game.camera.PaneObserver;
import game.core.animations.IAnimation;
import game.core.constants.PlayerStateEnum;
import game.core.constants.Vector;
import game.core.models.Enemy;
import game.core.models.Player;
import game.core.models.Vector2D;
import game.managers.EnemyManager;
import game.managers.GameManager;
import game.managers.Input;
import game.managers.PlayerManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	private final boolean DOOM_MODE = true;

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
	StackPane camera = new StackPane();
	private Scene gameScene = new Scene(root, 1800, 900);
	StackPane bossBarContainer = new StackPane();
	StackPane playerBarContainer = new StackPane();
	StackPane entityInfoContainer = new StackPane();

	private Player player = new Player(1000, new Vector2D(640, 600), PlayerStateEnum.IDLE, 4);
	private PlayerManager playerManager = new PlayerManager(player);

	private Label playerHealthLabel = UIFactory.makeLabel("Player Health: " + player.getHealth(), 10);

	private Enemy enemy = new Enemy(1000, new Vector2D(640, 600), 4);

	private EnemyManager enemyManager = new EnemyManager(enemy);

	private Label enemyHealthLabel = UIFactory.makeLabel("Enemy Health: " + enemy.getHealth(), 10);

	private Input input = Input.getInstance();

	// Background pane setup
	private StackPane bgPane = new StackPane();

	// MediaView setup (for video backgrounds)
	private MediaView mediaView;
	MediaPlayer mediaPlayer;

	Image bloodImage = new Image("./assets/sprite/ui/BloodOverlay.png");
	ImageView overlayView = new ImageView(bloodImage);

	private String bgMusicPath = "../assets/audio/song/tokyobluesloop.mp3"; // Replace with your file path
//	private String bgMusicPath = "../assets/audio/song/doom.mp3"; // Replace with your file path

	private Media bgMusicMedia = new Media(getClass().getResource(bgMusicPath).toExternalForm());
	private MediaPlayer backgroundMusic = new MediaPlayer(bgMusicMedia);

	private final double maxPlayerHealth = 1000;
	private final double maxPlayerStamina = 1000;
	private Rectangle playerHealthBar;
	private Rectangle playerStaminaBar;
	ImageView playerBarImgView;

	private Rectangle enemyHealthBarForeground;
	private Rectangle enemyHealthBarBackground;
	ImageView enemyBarImgView;

	@Override
	public void start(Stage stage) {
		window = stage;
		setMainMenu();
		window.setFullScreen(true);
		window.show();
	}

	@Deprecated
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
		String bgPath = "src/assets/sprite/scene/battleBG.mp4";
		String enemyHealthBarPath = "./assets/sprite/ui/boss_healthbar.png";

		File bgFile = new File(bgPath);
		Media media = new Media(bgFile.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView = new MediaView(mediaPlayer);

		Image enemyBarImage = new Image(enemyHealthBarPath);
		enemyBarImgView = new ImageView(enemyBarImage);

		enemyBarImgView.setFitWidth(root.getWidth() * 0.5);
		enemyBarImgView.setPreserveRatio(true);

		enemyHealthBarForeground = new Rectangle(enemyBarImgView.getFitWidth() * 0.92, 40, Color.web("#992222"));
		enemyHealthBarBackground = new Rectangle(enemyBarImgView.getFitWidth() * 0.92, 20, Color.web("#b52a2a"));

		StackPane bossHealthContainer = new StackPane();
		bossHealthContainer.setMaxHeight(40);
		bossHealthContainer.setMaxWidth(enemyBarImgView.getFitWidth() * 0.92);
		bossHealthContainer.setBackground(
				new Background(new BackgroundFill(Color.web("#361b19"), new CornerRadii(0), new Insets(0))));
		bossHealthContainer.getChildren().addAll(enemyHealthBarForeground, enemyHealthBarBackground);
		bossHealthContainer.setAlignment(Pos.CENTER_LEFT);

		bossBarContainer.getChildren().addAll(bossHealthContainer, enemyBarImgView, enemyHealthLabel);
		bossBarContainer.setTranslateY(-280);

		String playerHealthBarPath = "./assets/sprite/ui/player_healthbar.png";
		Image playerBarImage = new Image(playerHealthBarPath);

		playerBarImgView = new ImageView(playerBarImage);
		playerBarImgView.setFitWidth(root.getWidth() * 0.2);
		playerBarImgView.setPreserveRatio(true);

		double barWidth = playerBarImgView.getFitWidth();
		double barHeight = 24;

		// Create the health bar shapes
		playerHealthBar = new Rectangle(barWidth, barHeight, Color.GREENYELLOW);
		playerStaminaBar = new Rectangle(barWidth, barHeight, Color.DARKCYAN);

		VBox playerHealth = new VBox();
		playerHealth.getChildren().addAll(playerHealthBar, playerStaminaBar);
		playerHealth.setPrefWidth(playerHealthBar.getWidth());

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
		VBox playerHealthContainer = new VBox();
		topBox.setPrefHeight(root.getHeight() * 0.9);
		playerHealthContainer.setPrefHeight(root.getHeight() - topBox.getHeight());
		playerHealthContainer.setTranslateX(100);
		playerHealthContainer.setPrefWidth(root.getWidth() / 2);
		playerHealthContainer.getChildren().addAll(topBox, playerHealthLabel, playerBarContainer);

		entityInfoContainer.getChildren().addAll(bossBarContainer, playerInfoContainer, playerHealthContainer);

		backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);

		// Set the size of the MediaView
		mediaView.setFitWidth(gameScene.getWidth());
		mediaView.setFitHeight(gameScene.getHeight());
		mediaView.setPreserveRatio(true);
		mediaView.setTranslateY(-50);

		mediaPlayer.setOnError(() -> {
			System.out.println("Error loading media: " + mediaPlayer.getError().getMessage());
		});

		mediaPlayer.setOnReady(() -> {
			mediaPlayer.play();
		});

		// Translucent black overlay
		Rectangle darkOverlay = new Rectangle(gameScene.getWidth(), gameScene.getHeight(), Color.BLACK);
		darkOverlay.setOpacity(0.3);

		gameScene.setFill(Color.BLACK);
		bgPane.setPrefWidth(gameContainer.getWidth());
		bgPane.setPrefHeight(gameContainer.getHeight());
		bgPane.setAlignment(Pos.CENTER);

		overlayView.setPreserveRatio(true);
		overlayView.setFitWidth(2400);

		camera.getChildren().add(gameContainer);
		gameContainer.setAlignment(Pos.CENTER);
		gameContainer.getChildren().addAll(mediaView, darkOverlay, enemyCanvas, playerCanvas);
		gameContainer.setPrefWidth(2000);
		gameContainer.setStyle("-fx-background-color : black;");

		overlayView.setOpacity(0);
		root.getChildren().addAll(camera, overlayView, entityInfoContainer);
		root.setStyle("-fx-background-color : black;");
		root.setPrefHeight(gameContainer.getHeight());
		root.setPrefWidth(gameContainer.getWidth());
		root.setAlignment(Pos.CENTER);

		window.setScene(gameScene);
		window.setFullScreen(true);

		GameManager.getInstance().addContext(0, playerManager);
		GameManager.getInstance().addContext(1, enemyManager);
		start();
	}

	private void start() {
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();

		backgroundMusic.play();
		backgroundMusic.setVolume(1);
		PaneObserver.getInstance().addPlayerListener(playerBarContainer);
		PaneObserver.getInstance().addPlayerListener(gameContainer);
		PaneObserver.getInstance().addEnemyListener(bossBarContainer);
		PaneObserver.getInstance().addFXListener(overlayView);

		gameScene.setOnKeyPressed(event -> {
			input.pressKey(event.getCode());

		});
		gameScene.setOnKeyReleased(event -> {
			input.releaseKey(event.getCode());
		});

		enemyManager.start();
		playerManager.start();

		AnimationTimer gameLoop = new AnimationTimer() {
			private long lastUpdate = 0;

			@Override
			public void handle(long now) {
				if (lastUpdate == 0 || now - lastUpdate >= 16_666_667) { // ~60 FPS
					update();
					render();
					lastUpdate = now;
				}
			}
		};

		gameLoop.start();
	}

	private void draw(GraphicsContext gc, IAnimation animation, Vector2D pos, int direction) {
		if (animation == null)
			return;

		int deltaX = animation.getCropWidth();
		int deltaY = animation.getCropHeight();
		int startX = animation.getCurrentFrame() * deltaX;
		int startY = 0;

		int adjustedWidth = deltaX;

		if (direction < 0) {
			startX += deltaX;
			adjustedWidth = -deltaX;
		}

		gc.drawImage(animation.getSpriteImage(), startX, startY, adjustedWidth, deltaY, pos.getX(),
				pos.getY() - deltaY * 4, deltaX * 4, deltaY * 4);

//	    gc.setFill(Color.RED);
//	    gc.fillRect(pos.getX()+deltaX, pos.getY(), deltaX*3, 5);
	}

	private void parallax() {
		camera.setTranslateX((-player.getPos().getX() * 0.2) + 160);
		entityInfoContainer.setTranslateX(player.getPos().getX() * 0.2 / 10);
		camera.setTranslateY((-player.getPos().getY() * 0.2) + 140);
	}

	private void render() {
		playerGC.clearRect(0, 0, 2000, 2000);
		enemyGC.clearRect(0, 0, 2000, 2000);

		IAnimation playerAnimation = playerManager.getCurrentAnimation();
		IAnimation enemyAnimation = enemyManager.getCurrentAnimation();

		draw(playerGC, playerAnimation, player.getPos(), playerManager.getDirection());
		int enemyDirection = (enemy.getPos().getX() > player.getPos().getX()) ? Vector.RIGHT : Vector.LEFT;

		enemyManager.setDirection(enemyDirection * -1);

		draw(enemyGC, enemyAnimation, enemy.getPos(), enemyDirection);

		parallax();
	}

	private void update() {
		playerManager.update();
		enemyManager.update();

		double currentHealth = player.getHealth() <= 0 ? 0 : player.getHealth();
		double healthPercent = currentHealth / maxPlayerHealth;
		double newHealthWidth = playerBarImgView.getFitWidth() * healthPercent;
		playerHealthBar.setWidth(newHealthWidth);

		double currentStamina = player.getStamina() <= 0 ? 0 : player.getStamina();
		double staminaPercent = currentStamina / maxPlayerStamina;
		double newStaminaWidth = playerBarImgView.getFitWidth() * staminaPercent;
		playerStaminaBar.setWidth(newStaminaWidth);

		double enemyCurrentHealth = enemy.getHealth() <= 0 ? 0 : enemy.getHealth();
		double enemyHealthPercent = enemyCurrentHealth / 1000;
		double enemyNewWidth = enemyBarImgView.getFitWidth() * 0.92 * enemyHealthPercent;
		enemyHealthBarBackground.setWidth(enemyNewWidth);
		enemyHealthBarForeground.setWidth(enemyNewWidth);

		playerHealthLabel.setText("Player Health: " + player.getHealth());
		enemyHealthLabel.setText("Enemy Health: " + enemy.getHealth());
	}

	public static void main(String[] args) {
		launch();
	}
}
