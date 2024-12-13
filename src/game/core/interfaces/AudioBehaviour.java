package game.core.interfaces;

import game.core.sounds.IAudio;

public interface AudioBehaviour {
	public IAudio getCurrentSound();

	public void setSound(int soundId);
}
