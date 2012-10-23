package com.android.app.api;

import java.util.ArrayList;

import com.android.app.entity.Location;
import com.android.app.entity.Point;

/**
 * 图片请求参数
 * 
 * @author Administrator
 * 
 */
public class AvatarRequestParam extends BaseRequestParam {

	private String label;// 标签
	private String username;
	private Location location;
	private ArrayList<Point> points;
	private String email;
	private String tipsid;
	private String photoid;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipsid() {
		return tipsid;
	}

	public void setTipsid(String tipsid) {
		this.tipsid = tipsid;
	}

	public String getPhotoid() {
		return photoid;
	}

	public void setPhotoid(String photoid) {
		this.photoid = photoid;
	}
}
