package com.android.app.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class ImageItem {
	
	private static final String TAG = "ImageItem";
	private String imageUrl;
	private String thumbnail;
	private ImageTag imagetag;
	private ArrayList<Block> blocks;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public ImageTag getTag() {
		return imagetag;
	}
	public void setTag(ImageTag tag) {
		this.imagetag = tag;
	}
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	public void parseJson(JSONObject jsonObject) {
		Log.e(TAG, "items is " + jsonObject);
		if (jsonObject != null) {
			if (!jsonObject.isNull("image")) {
				this.setImageUrl(jsonObject.optString("image"));
			}
			if (!jsonObject.isNull("thumbnail")) {
				this.setThumbnail(jsonObject.optString("thumbnail"));
			}
			if (!jsonObject.isNull("summary")) {
				JSONObject summaryJson = jsonObject.optJSONObject("summary");
				Log.e("ImageTag","imageTag is " + summaryJson);
				if (summaryJson != null) {
					ImageTag imageTag = parserTag(summaryJson);
					Log.e("ImageTag","imageTag is " + imageTag);
					this.setTag(imageTag);
				}
			}
			if (!jsonObject.isNull("content")) {
				JSONObject contentJson = jsonObject.optJSONObject("content");
				JSONArray array = contentJson.optJSONArray("blocks");
				Log.e("Block","array is " + array);
				if (array != null && array.length() > 0) {
					ArrayList<Block> blocks = new ArrayList<Block>();
					for (int i = 0; i < array.length(); i++) {
						Block block = new Block();
						block.parseJson((JSONObject) array.opt(i));
						blocks.add(block);
					}
					Log.e("Block","menues is " + blocks.size());
					this.setBlocks(blocks);
				}
			}
		}
	}
	private ImageTag parserTag(JSONObject jsonObject) {
		ImageTag tag = null;
		if (jsonObject != null) {
			tag = new ImageTag();
			if (!jsonObject.isNull("tags")) {
				
				tag.setTags(jsonObject.optString("tags"));
			}
			if (!jsonObject.isNull("owners")) {
				tag.setOwners(jsonObject.optString("owners"));
			}
			if (!jsonObject.isNull("coordiante")) {
				tag.setCoord(jsonObject.optString("coordiante"));
			}
			if (!jsonObject.isNull("time")) {
				tag.setTime(jsonObject.optString("time"));
			}
			if (!jsonObject.isNull("good")) {
				tag.setGoodcount(jsonObject.optInt("good"));
			}
			if (!jsonObject.isNull("tipsid")) {
				tag.setTipsid(jsonObject.optString("tipsid"));
			}
		}
		return tag;
	}
	
	

}
