package com.android.app.entity;

public class Rect extends Point {
	Point pointCenter;
	float radius;

	public Point p_CP = new Point();
	public Point p_PP = new Point();
	public Point p_CC = new Point();
	public Point p_PC = new Point();

	public Rect(float radius) {
		this.radius = radius;
	}

	public Point getCenterPoint() {
		return pointCenter;
	}

	public Rect(float x, float y, float radius) {
		pointCenter = new Point(x, y);
		this.radius = radius;
		p_PC.setPoint(pointCenter.getX() + radius, pointCenter.getY() - radius);
		p_PP.setPoint(pointCenter.getX() + radius, pointCenter.getY() + radius);
		p_CC.setPoint(pointCenter.getX() - radius, pointCenter.getY() - radius);
		p_CP.setPoint(pointCenter.getX() - radius, pointCenter.getY() + radius);
	}

	public void changeCenter(Point newCenter) {
		pointCenter = newCenter;
		p_PC.setPoint(pointCenter.getX() + radius, pointCenter.getY() - radius);
		p_PP.setPoint(pointCenter.getX() + radius, pointCenter.getY() + radius);
		p_CC.setPoint(pointCenter.getX() - radius, pointCenter.getY() - radius);
		p_CP.setPoint(pointCenter.getX() - radius, pointCenter.getY() + radius);
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public boolean isInArea(float x, float y) {

		if (x > p_CP.x && y < p_CP.y && x < p_PC.x && y > p_PC.y) {
			return true;
		}
		return false;
	}
}
