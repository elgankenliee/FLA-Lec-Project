package game;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIFactory {
	
	public static Label makeLabel(String str, int size) {
		
		Font customFont = Font.loadFont(UIFactory.class.getResourceAsStream("/assets/font/prstartk-webfont.ttf"), size);
		Label newLabel = new Label(str);
		newLabel.setStyle("-fx-background-color: none;");
		newLabel.setTextFill(Color.WHITE);
		newLabel.setFont(customFont);
		
		return newLabel;
	}
	
	public static Button makeButton(String str) {
		
		Font customFont = Font.loadFont(UIFactory.class.getResourceAsStream("/assets/font/prstartk-webfont.ttf"), 25);
		Button menuButton = new Button(str);
		menuButton.setStyle("-fx-background-color: none;");
		menuButton.setTextFill(Color.WHITE);
		menuButton.setFont(customFont);
		
		return menuButton;
	}
	
	public static Button makeButton(String str, int size) {
		
		Font customFont = Font.loadFont(UIFactory.class.getResourceAsStream("/assets/font/prstartk-webfont.ttf"), size);
		Button menuButton = new Button(str);
		menuButton.setStyle("-fx-background-color: none;");
		menuButton.setTextFill(Color.WHITE);
		menuButton.setFont(customFont);
		
		return menuButton;
	}

	public UIFactory() {
		// TODO Auto-generated constructor stub
	}

}
