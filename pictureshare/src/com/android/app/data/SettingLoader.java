package com.android.app.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 设置信息存储
 * @author Administrator
 *
 */
public class SettingLoader {

	private static final String CONFIG = "config";
	private static final String HASLOGIN = "hasLogin";

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


}
