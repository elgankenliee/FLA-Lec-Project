package Entities;

public class Enemy {
	private int health = 1000;
	double x = 600;
	double y = 580;

	private Boolean isSpawning = true;

	public void move(double dx, double dy) {
		x += dx;
		y += dy;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Boolean isSpawning() {
		return isSpawning;
	}

	public void setSpawning(Boolean isSpawning) {
		this.isSpawning = isSpawning;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		health = health;
	}
}
