package com.android.app.entity;

import java.util.ArrayList;

/**
 * 图片信息
 * 
 * @author Administrator
 * 
 */
public class Avatar {
	private String avatarID;
	private String ownerId;
	private long longitude;
	private long latitude;
	private String path;// 文件名从文件路径中截取尾部部分得到
	private String title;
	private ArrayList<Comment> comments;
	private ArrayList<Point> points;
	private long time;

	class Point {
		int pointx;
		int pointy;
		public Point(int x, int y) {
			pointx = x;
			pointy = y;
		}
	}

	public String getAvatarID() {
		return avatarID;
	}

	public void setAvatarID(String avatarID) {
		this.avatarID = avatarID;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
