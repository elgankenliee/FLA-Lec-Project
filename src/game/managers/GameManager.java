package game.managers;

import java.util.HashMap;
import java.util.Map;

import game.core.interfaces.CharacterContext;
import game.core.models.Character;

public class GameManager {
  
  private static volatile GameManager instance;
  
  private Map<Integer, CharacterContext> contexts;

  private GameManager() {
    contexts = new HashMap<>();
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

  public void addContext(Integer id, CharacterContext context) {
    contexts.put(id, context);
  }
  
  public CharacterContext getContext(Integer id) {
    return contexts.get(id);
  }
}