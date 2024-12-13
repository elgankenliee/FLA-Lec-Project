package game.controllers;

import java.util.HashMap;

import game.core.sounds.CharacterAudio;
import game.core.sounds.IAudio;

public class AudioController {
	private final HashMap<Integer, CharacterAudio> sounds;
	private IAudio currentSound;

	public AudioController() {
		this.sounds = new HashMap<>();
	}

	public void addAudio(int state, CharacterAudio sound) {
		sounds.put(state, sound);
	}

	public void setCurrentSound(int state) {
		if (currentSound != null)
			currentSound.stop();
		currentSound = sounds.get(state);
		currentSound.play();
	}

	public IAudio getCurrentSound() {
		return currentSound;
	}

}
