package com.android.app.entity;

public class Point {

	float x, y;

	public Point() {
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Point setPoint(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
