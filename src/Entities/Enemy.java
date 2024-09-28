package Entities;

public class Enemy {
	double x = 600;
	double y = 580;

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
