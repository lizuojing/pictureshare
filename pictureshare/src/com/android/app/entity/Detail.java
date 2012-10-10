package com.android.app.entity;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Detail extends ResultOPE{

	public Point lbPoint;
	public Point ruPoint;
	public Point getLbPoint() {
		return lbPoint;
	}
	public void setLbPoint(Point lbPoint) {
		this.lbPoint = lbPoint;
	}
	public Point getRuPoint() {
		return ruPoint;
	}
	public void setRuPoint(Point ruPoint) {
		this.ruPoint = ruPoint;
	}
	
	public String toJsonString()
	{
		
		JSONObject jsonall=new JSONObject();
		JSONObject parmsJson=new JSONObject();
		try {
			jsonall.put(JsonTitle.LEFT_BUTTON,lbPoint.toString());
			jsonall.put(JsonTitle.RIGHT_UP,ruPoint.toString());
			parmsJson.put(JsonTitle.PARAMS, jsonall);
		} catch (JSONException e) {
			Log.e("Detail","error");
			e.printStackTrace();
		}
		return parmsJson.toString();
	}
	
}
