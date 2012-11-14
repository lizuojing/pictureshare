package com.android.app.entity;

import org.json.JSONObject;

public class Menue {
	private String title;
	private String url;
	private String content;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void parseJson(JSONObject jsonObject) {
		if (jsonObject != null) {
			if (!jsonObject.isNull("title")) {
				this.setTitle(jsonObject.optString("title"));
			}
			if (!jsonObject.isNull("url")) {
				this.setUrl(jsonObject.optString("url"));
			}
			if (!jsonObject.isNull("content")) {
				this.setContent(jsonObject.optString("content"));
			}
		}
	}

}
