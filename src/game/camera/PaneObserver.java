package game.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PaneObserver {
	private static PaneObserver instance;
	private final List<ImageView> FXListeners = new ArrayList<>();
	private final List<Pane> playerListeners = new ArrayList<>();
	private final List<Pane> enemyListeners = new ArrayList<>();
	Random rand = new Random();

	private PaneObserver() {
	}

	public static PaneObserver getInstance() {
		if (instance == null) {
			instance = new PaneObserver();
		}
		return instance;
	}

	public void addFXListener(ImageView imgView) {
		FXListeners.add(imgView);
	}

	public void addPlayerListener(Pane pane) {
		playerListeners.add(pane);
	}

	public void removePlayerListener(Pane pane) {
		playerListeners.remove(pane);
	}

	public void notifyPlayerListeners() {
		for (Pane pane : playerListeners) {
			applyVibration(pane);
		}
		for (ImageView overlay : FXListeners) {
			applyOverlayEffect(overlay);
		}
	}

	public void addEnemyListener(Pane pane) {
		enemyListeners.add(pane);
	}

	public void removeEnemyListener(Pane pane) {
		enemyListeners.remove(pane);
	}

	public void notifyEnemyListeners() {
		for (Pane pane : enemyListeners) {
			applyVibration(pane);
			applyFadeEffect(pane);
		}
	}

	private void applyVibration(Pane pane) {
		double initialMagnitude = 30;
		int iterations = 3;
		javafx.animation.Timeline vibrationTimeline = new javafx.animation.Timeline();

		for (int i = 0; i < iterations; i++) {
			double magnitude = initialMagnitude * (1 - (i / (double) iterations));
			KeyFrame moveLeft = new KeyFrame(javafx.util.Duration.millis(i * 50),
					new KeyValue(pane.translateXProperty(), -magnitude));

			KeyFrame moveRight = new KeyFrame(javafx.util.Duration.millis(i * 50 + 25),
					new KeyValue(pane.translateXProperty(), magnitude));

			KeyFrame reset = new KeyFrame(javafx.util.Duration.millis((i + 1) * 50),
					new KeyValue(pane.translateXProperty(), 0));

			vibrationTimeline.getKeyFrames().addAll(moveLeft, moveRight, reset);
		}
		vibrationTimeline.play();
	}

	private void applyOverlayEffect(ImageView overlay) {
		// If the overlay is already fading out, reset its opacity to fully visible

		overlay.setOpacity(0.8);

		// Create a FadeTransition for the fade-out effect
		FadeTransition fadeOut = new FadeTransition(Duration.millis(3000), overlay);
		fadeOut.setFromValue(0.8); // Start from fully visible
		fadeOut.setToValue(0.0); // End fully transparent
//		fadeOut.setDelay(Duration.seconds(0.1)); // Small delay for continuous hits
		fadeOut.setOnFinished(e -> {
			overlay.setOpacity(0.0);

		}); // Ensure it's fully transparent after fade

		if (fadeOut != null && fadeOut.getStatus() == Animation.Status.RUNNING) {
			fadeOut.stop();
		}
		// Play the fade-out animation
		fadeOut.play();
	}

	private void applyFadeEffect(Node node) {
		Pane pane = (Pane) node;
		Rectangle fadeOverlay = new Rectangle(pane.getWidth() * 0.5 * 0.92, 40, Color.WHITE);
		fadeOverlay.setOpacity(0);
		pane.getChildren().add(fadeOverlay);

		FadeTransition fadeToBlack = new FadeTransition(Duration.millis(300), fadeOverlay);
		fadeToBlack.setFromValue(0.6);
		fadeToBlack.setToValue(0.0);

		fadeToBlack.setOnFinished(event -> pane.getChildren().remove(fadeOverlay));
		fadeToBlack.play();
	}
}
