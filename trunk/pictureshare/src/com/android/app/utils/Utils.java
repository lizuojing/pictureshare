package com.android.app.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utils {
	private static final String Cache_Dir = "/Scaner/cache/";

	/**
	 * 将dip转换为pix
	 * 
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int dipToPixels(Context context, float dip) {
		return (int) (context.getResources().getDisplayMetrics().density * dip);
	}

	public static boolean isNullOrEmpty(String str) {
		if(null==str||"".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static String formatTime(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * 获取屏幕高度
	 * @param mContext
	 * @return
	 */
	public static int getScreenHeight(Context mContext) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager WM = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		WM.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
	
	/**
	 * 获取屏幕宽度
	 * @param mContext
	 * @return
	 */
	public static int getScreenWidth(Context mContext) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager WM = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		WM.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static File getCacheDir() {
		File cacheDir = new File(Environment.getExternalStorageDirectory(),Cache_Dir);
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir;
	}
}
