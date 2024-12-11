package game.core.models;

public class Vector2D {
	
	private double x;
	private double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(double x, double y) {
    this.x += x;
    this.y += y;
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

	public void updateX(double x) {
		this.x += x;
	}
	
	public void updateY(double y) {
		this.y += y;
	}
	
}
