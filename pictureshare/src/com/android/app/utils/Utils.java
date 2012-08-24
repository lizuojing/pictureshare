package com.android.app.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.app.MainActivity;
import com.android.app.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
	
	public static boolean isNickname(final String str) {
		try {
			byte[] bytes=str.getBytes("gbk");
			if(bytes.length>20){
				return false;
			}
		} catch (UnsupportedEncodingException e) {
		}
		final String regex = "[\\u4e00-\\u9fa5\\w]{1,}";
		return match(regex, str);
	}
	
	public static boolean isEmail(final String str) {
		final String regex = "^[a-zA-Z0-9]{1,}[a-zA-Z0-9\\_\\.\\-]{0,}@(([a-zA-Z0-9]){1,}\\.){1,3}[a-zA-Z0-9]{0,}[a-zA-Z]{1,}$";
		return match(regex, str);
	}

	public static boolean isRegUserName(final String str) {
		final String regex = "[0-9a-zA-Z\\_]{5,20}";
		return match(regex, str);
	}
	
	public static boolean isLoginUserName(final String str) {
		final String regex = "[0-9a-zA-Z\\_]{3,20}";
		return match(regex, str);
	}

	public static boolean isPassword(final String str) {
		final String regex = "[\\S]{6,15}";
		return match(regex, str);
	}
	private static boolean match(final String regex, final String str) {
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static final int byteToShort(byte[] bytes) {
		return (bytes[0] << 8) + (bytes[1] & 0xFF);
	}

	public static boolean isSDCardEnable() {
		String SDState = Environment.getExternalStorageState();
		if (SDState != null && SDState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
	
	public static void showNotification(Context context, int id, String title, String message)
	{
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        String tickerText = message;

        Notification notification = new Notification(R.drawable.android_default, tickerText, System.currentTimeMillis());
        notification.defaults |= Notification.DEFAULT_SOUND;

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("from_notification", true);
        
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        notification.setLatestEventInfo(context, title, tickerText, contentIntent);
        
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(id, notification);
	}
	
	public static void deleteNotification(Context context, int id) 
	{
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
}
