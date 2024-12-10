package game.core.interfaces;

import game.core.models.entities.Player;

public interface FXBehaviour {

  public void start();
  public void update();
  public void update(Player player);
}
