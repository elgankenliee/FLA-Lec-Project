package game.managers;

import java.util.HashMap;
import java.util.Map;

import game.controllers.AudioController;
import game.core.audio.Audio;
import game.core.interfaces.CharacterContext;

public class GameManager {

	private static volatile GameManager instance;
	private Map<Integer, CharacterContext> contexts;
	private AudioController audioController;

	private GameManager() {
		contexts = new HashMap<>();
		audioController = new AudioController();
		initializeGameSounds();
	}

	public static GameManager getInstance() {
		if (instance == null) {
			synchronized (GameManager.class) {
				if (instance == null) {
					instance = new GameManager();
				}
			}
		}
		return instance;
	}

	public void initializeGameSounds() {
		audioController.addAudio(0, new Audio("src/assets/audio/sfx/player_hurt.wav"));
		audioController.addAudio(1, new Audio("src/assets/audio/sfx/player_hurt2.wav"));
		audioController.addAudio(2, new Audio("src/assets/audio/sfx/player_hurt3.wav"));
	}

	public void playGameSound(int soundId) {
		audioController.setCurrentSound(soundId);
	}

	public void addContext(Integer id, CharacterContext context) {
		contexts.put(id, context);
	}

	public CharacterContext getContext(Integer id) {
		return contexts.get(id);
	}
}