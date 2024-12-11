package game.managers;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.input.KeyCode;

public class Input {
  private static volatile Input instance;
  private final Set<KeyCode> pressedKeys; 
  
  private Input() {
      this.pressedKeys = new HashSet<>();
  }
  
  public static Input getInstance() {
    if (instance == null) {
        synchronized (Input.class) {
            if (instance == null) {
                instance = new Input();
            }
        }
    }
    return instance;
}


  public void pressKey(KeyCode key) {
      synchronized (pressedKeys) {
          pressedKeys.add(key);
      }
  }

  public void releaseKey(KeyCode key) {
      synchronized (pressedKeys) {
          pressedKeys.remove(key);
      }
  }

  public boolean getKey(KeyCode key) {
      synchronized (pressedKeys) {
          return pressedKeys.contains(key);
      }
  }

  public Set<KeyCode> getPressedKeys() {
      synchronized (pressedKeys) {
          return new HashSet<>(pressedKeys);
      }
  }
}
