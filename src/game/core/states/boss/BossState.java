package game.core.states.boss;

import game.core.interfaces.BossContext;

public interface BossState {
  void start(BossContext context);
  void update(BossContext context);
  void exit(BossContext context);
}