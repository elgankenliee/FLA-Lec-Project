package game.core.sounds;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class CharacterAudio implements IAudio {

	private final String audioPath;
	private final Media audioMedia;
	private final MediaPlayer audioPlayer;
	private final File audioFile;

	public CharacterAudio(String audioPath) {

		this.audioPath = audioPath;
		this.audioFile = new File(audioPath);
		this.audioMedia = new Media(this.audioFile.toURI().toString());
		this.audioPlayer = new MediaPlayer(audioMedia);
		audioPlayer.setVolume(0.4);
	}

	public String getAudioPath() {
		return audioPath;
	}

	public Media getAudioMedia() {
		return audioMedia;
	}

	public MediaPlayer getAudioPlayer() {
		return audioPlayer;
	}

	@Override
	public void play() {
		this.audioPlayer.stop();
		this.audioPlayer.play();
	}

	@Override
	public void stop() {
		this.audioPlayer.stop();
	}

}
