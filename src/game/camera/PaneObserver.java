package game.camera;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PaneObserver {
	private static PaneObserver instance;
	private final List<Pane> playerListeners = new ArrayList<>();
	private final List<Pane> enemyListeners = new ArrayList<>();

	private PaneObserver() {
	}

	public static PaneObserver getInstance() {
		if (instance == null) {
			instance = new PaneObserver();
		}
		return instance;
	}

	// Add a listener
	public void addPlayerListener(Pane pane) {
		playerListeners.add(pane);
	}

	// Remove a listener
	public void removePlayerListener(Pane pane) {
		playerListeners.remove(pane);
	}

	// Notify all listeners
	public void notifyPlayerListeners() {
		for (Pane pane : playerListeners) {
			applyVibration(pane); // Call the vibration method here
		}
	}

	public void addEnemyListener(Pane pane) {
		enemyListeners.add(pane);
	}

	// Remove a listener
	public void removeEnemyListener(Pane pane) {
		enemyListeners.remove(pane);
	}

	// Notify all listeners
	public void notifyEnemyListeners() {
		for (Pane pane : enemyListeners) {
			applyVibration(pane); // Call the vibration method here
			applyFadeEffect(pane);
		}
	}

	// Vibration method
	private void applyVibration(Pane bossBarContainer) {
		double initialMagnitude = 15;
		int iterations = 6;
		javafx.animation.Timeline vibrationTimeline = new javafx.animation.Timeline();

		for (int i = 0; i < iterations; i++) {
			double magnitude = initialMagnitude * (1 - (i / (double) iterations));
			javafx.animation.KeyFrame moveLeft = new javafx.animation.KeyFrame(javafx.util.Duration.millis(i * 50),
					new javafx.animation.KeyValue(bossBarContainer.translateXProperty(), -magnitude));

			javafx.animation.KeyFrame moveRight = new javafx.animation.KeyFrame(
					javafx.util.Duration.millis(i * 50 + 25),
					new javafx.animation.KeyValue(bossBarContainer.translateXProperty(), magnitude));

			javafx.animation.KeyFrame reset = new javafx.animation.KeyFrame(javafx.util.Duration.millis((i + 1) * 50),
					new javafx.animation.KeyValue(bossBarContainer.translateXProperty(), 0));

			vibrationTimeline.getKeyFrames().addAll(moveLeft, moveRight, reset);
		}

		vibrationTimeline.play();
	}

	private void applyFadeEffect(Pane pane) {
		Rectangle fadeOverlay = new Rectangle(pane.getWidth() * 0.5 * 0.92, 40, Color.WHITE);
		fadeOverlay.setOpacity(0); // Start fully transparent
		pane.getChildren().add(fadeOverlay);

		FadeTransition fadeToBlack = new FadeTransition(Duration.millis(300), fadeOverlay);
		fadeToBlack.setFromValue(0.6); // Initial opacity (white)
		fadeToBlack.setToValue(0.0); // Fully transparent

		fadeToBlack.setOnFinished(event -> pane.getChildren().remove(fadeOverlay));
		fadeToBlack.play();
	}
}
