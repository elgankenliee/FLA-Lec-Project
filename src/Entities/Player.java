package Entities;

public class Player {
	double x = 200; // Player's x position
	double y = 580; // Player's y position

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

}