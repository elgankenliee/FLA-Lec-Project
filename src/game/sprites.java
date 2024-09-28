package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class sprites extends Application {

    private static final int COLUMNS = 4; // Number of columns in the sprite sheet
    private static final int COUNT = 4; // Total number of frames in the sprite sheet
    private static final int OFFSET_X = 0; // X offset of the first frame in the sprite sheet
    private static final int OFFSET_Y = 0; // Y offset of the first frame in the sprite sheet
    private static final int WIDTH = 60; // Width of each frame
    private static final int HEIGHT = 64; // Height of each frame

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Load the sprite sheet image
        Image spriteSheet = new Image("/player_idleedited.png");

        // Create an ImageView to display the animation
        ImageView imageView = new ImageView(spriteSheet);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        // Create a Timeline to update the animation at regular intervals
        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(150), event -> {
                    int index = (int) ((imageView.getViewport().getMinX() + WIDTH) / WIDTH) % COLUMNS;
                    int x = (index % COLUMNS) * WIDTH + OFFSET_X;
                    int y = (index / COLUMNS) * HEIGHT + OFFSET_Y;
                    imageView.setViewport(new Rectangle2D(x, y, WIDTH, HEIGHT));
                })
        );
//        imageView.setFitWidth(300);
//        imageView.setPreserveRatio(true);
        animation.setCycleCount(Animation.INDEFINITE); // Repeat the animation indefinitely
        animation.play();

        // Create a StackPane to hold the ImageView
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, WIDTH*3, HEIGHT*3);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sprite Sheet Animation");
        primaryStage.show();
    }
}
