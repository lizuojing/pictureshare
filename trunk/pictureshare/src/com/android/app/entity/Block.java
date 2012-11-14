package com.android.app.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class Block {
	
	private String title;
	private ArrayList<Menue> list;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<Menue> getList() {
		return list;
	}
	public void setList(ArrayList<Menue> list) {
		this.list = list;
	}
	public void parseJson(JSONObject jsonObject) {
		if(jsonObject!=null) {
			if (!jsonObject.isNull("title")) {
				this.setTitle(jsonObject.optString("title"));
			}
			if (!jsonObject.isNull("menus")) {
				JSONArray array = jsonObject.optJSONArray("menus");
				if (array != null && array.length() > 0) {
					ArrayList<Menue> menues = new ArrayList<Menue>();
					for (int i = 0; i < array.length(); i++) {
						Menue menue = new Menue();
						menue.parseJson((JSONObject) array.opt(i));
						menues.add(menue);
					}
					Log.e("Menue","menues is " + menues.size());
					this.setList(menues);
				}
			}
		}
		
	}
	
	

}
