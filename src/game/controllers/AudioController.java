package game.controllers;

import java.io.File;
import java.util.HashMap;

import game.core.audio.Audio;
import game.core.audio.IAudio;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
	private final HashMap<Integer, Audio> sounds;
	private IAudio currentSound;

	public AudioController() {
		this.sounds = new HashMap<>();
	}

	public void addAudio(int state, Audio sound) {
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

	public static void playAudio(String audioPath) {
		File audioFile = new File(audioPath);
		Media audioMedia = new Media(audioFile.toURI().toString());
		MediaPlayer audioPlayer = new MediaPlayer(audioMedia);
		audioPlayer.setVolume(0.2);
		audioPlayer.play();
	}

}
