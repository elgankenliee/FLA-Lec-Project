package Entities;

public class Player {
	private double x = 200; // Player's x position
	private double y = 580; // Player's y position

	private boolean isCrouching = false;
	private boolean isJumping = false; // Track if the player is jumping
	private boolean isWalking = false;
	private boolean isAttacking = false; // Track attacks
	private int atkIdx = 1; // Attack index for combos

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

	public boolean isCrouching() {
		return isCrouching;
	}

	public void setCrouching(boolean isCrouching) {
		this.isCrouching = isCrouching;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public boolean isWalking() {
		return isWalking;
	}

	public void setWalking(boolean isWalking) {
		this.isWalking = isWalking;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public int getAtkIdx() {
		return atkIdx;
	}

	public void setAtkIdx(int atkIdx) {
		this.atkIdx = atkIdx;
	}
}