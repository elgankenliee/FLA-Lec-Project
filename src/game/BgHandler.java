package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BgHandler {
	private final Image[] layers;
	private final double[] layerOffsets; // To hold the parallax offsets
	private final double parallaxFactor; // Factor to control the speed of parallax effect

	public BgHandler(String[] layerPaths, double parallaxFactor) {
		this.layers = new Image[layerPaths.length];
		this.layerOffsets = new double[layerPaths.length];
		this.parallaxFactor = parallaxFactor;

		for (int i = 0; i < layerPaths.length; i++) {
			layers[i] = new Image(layerPaths[i]);
		}
	}

	public void update(double playerX) {
		for (int i = 0; i < layers.length; i++) {
			layerOffsets[i] = (playerX * parallaxFactor) / (i + 1);
		}
	}

	public void draw(GraphicsContext gc) {
		for (int i = 0; i < layers.length; i++) {
			gc.drawImage(layers[i], -layerOffsets[i], 0, layers[i].getWidth(), layers[i].getHeight());
		}
	}
}