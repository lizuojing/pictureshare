package com.android.app.data;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

/**
 * 设置信息存储
 * @author Administrator
 *
 */
public class SettingLoader {

	private static final String CONFIG = "config";
	private static final String HASLOGIN = "hasLogin";
	private static final String GalleryThumbnailCache_Dir = "/PicManager/cache/galleryThumbnailCache/";
	private static final String REGEMAIL = "regemail";
	private static final String PHOTOID = "current_photoid";

	public static boolean setLogin(Context context,boolean isLogin) {
		SharedPreferences preferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(HASLOGIN, isLogin);
		return edit.commit();
	}
	
	public static boolean isLogin(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		return preferences.getBoolean(HASLOGIN, false);
	}

	public static File getGalleryThumbnailCacheDir() {
		File cacheDir = new File(Environment.getExternalStorageDirectory(), GalleryThumbnailCache_Dir);
		if (!cacheDir.exists())
		{
			cacheDir.mkdirs();
		}
		
		return cacheDir;
	}

	public static String getRegEmail(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		return preferences.getString(REGEMAIL, null);
	}
	
	public static boolean setRegEmail(Context context,String regemail) {
		SharedPreferences preferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(REGEMAIL, regemail);
		return edit.commit();
	}

	public static boolean setPhotoId(Context context, String photoid) {
		SharedPreferences preferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(PHOTOID, photoid);
		return edit.commit();
	}
	
	public static String getPhotoId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		return preferences.getString(PHOTOID, null);
	}
}
