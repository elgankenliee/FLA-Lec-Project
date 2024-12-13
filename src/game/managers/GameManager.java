package game.managers;

import java.util.HashMap;
import java.util.Map;

import game.core.models.Character;

public class GameManager {
  
  private static volatile GameManager instance;
  
  private Map<Integer, Character> entities;

  private GameManager() {
    entities = new HashMap<>();
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

  public void addEntity(Integer id, Character entity) {
    entities.put(id, entity);
  }
  
  public Character getEntity(Integer id) {
    return entities.get(id);
  }
}