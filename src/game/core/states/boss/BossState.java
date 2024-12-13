package game.core.states.boss;

import game.core.interfaces.CharacterContext;

public interface BossState {
  void start(CharacterContext context);
  void update(CharacterContext context);
  void exit(CharacterContext context);
}