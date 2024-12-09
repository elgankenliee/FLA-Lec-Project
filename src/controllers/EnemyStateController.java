package controllers;

import game.core.models.entities.Enemy;

public class EnemyStateController extends Thread {

	Enemy enemy;

	public EnemyStateController(Enemy enemy) {
		this.enemy = enemy;
	}

	@Override
	public void run() {

		while (enemy.getHealth() < 1) {

		}

	}
}
