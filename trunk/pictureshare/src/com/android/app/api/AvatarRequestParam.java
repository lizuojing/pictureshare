package com.android.app.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.app.entity.Location;

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
	private ArrayList<String> points;
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

	public ArrayList<String> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<String> points) {
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

	public String toJsonString(String page, String pages) {
		JSONObject jsonParmas = new JSONObject();
		JSONArray jsonArray = null;
		try {
			if (getPoints() != null) {
				jsonArray = new JSONArray();
				JSONObject json = null;
				for (String point : getPoints()) {
					json = new JSONObject();
					json.put("point", point);
					jsonArray.put(json.get("point"));

				}
			}

			String locationParam = "";
			if (getLocation() != null) {
				locationParam += getLocation().getLatitude() + ","
						+ getLocation().getLongitude();
			}

			JSONObject jsonAll = new JSONObject();
			jsonAll.put("photoid", getPhotoid());
			if (getTipsid() != null) {
				jsonAll.put("tipsid", getTipsid());
			}
			jsonAll.put("username", getUsername());
			jsonAll.put("email", getEmail());
			jsonAll.put("tag", getLabel());
			jsonAll.put("location", locationParam);
			jsonAll.put("points", jsonArray);
//			jsonAll.put("points", "['12,12','12,12','12,12','12,12']");
			jsonAll.put("page", page);
			jsonAll.put("pagesize", pages);
			jsonParmas.put("params", jsonAll);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonParmas.toString();
	}
}
