package com.android.app.service;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.android.app.PicApp;
import com.android.app.R;
import com.android.app.intent.PicIntent;
import com.android.app.receceiver.HeartBeatCheckReceiver;
import com.android.app.utils.Utils;

/**
 * 消息机制实现方案
 * 
 * @author Administrator
 * 
 */
public class PicService extends Service {

	private static final String TAG = "PicService";
	
	public static long lastSendTime = 0l;
	public static long lastReceiveTime = 0l;
	private int HB_DEFAULT_ITV = 60;
	private int HB_DEFAULT_CHECK_ITV = 60;
	private int HB_DEFAULT_CHECK_COUNT = 3;
	private int interval = HB_DEFAULT_ITV;
	private static ArrayList<ServiceHandler> mServiceHandlers = new ArrayList<ServiceHandler>();

	public static ArrayList<Activity> allActivity = new ArrayList<Activity>();
	private ServiceHandler mHandler = null;

	public static abstract class ServiceHandler extends Handler {
		public final static int ID_Message_HeartBeat_Check = 2000;
		public final static int ID_Message_Offline = 2001;

		public void handleMessage(Message msg) {

			Log.i("ServiceHandler", "handleMessage, msg.what:" + msg.what
					+ " msg.obj:" + msg.obj + " msg.arg1:" + msg.arg1
					+ "msg.arg2:" + msg.arg2);

			super.handleMessage(msg);

			switch (msg.what) {
			case ID_Message_HeartBeat_Check:
				onHeartBeatCheck();
				break;
			case ID_Message_Offline:
				onOffline();
				break;
			default:
				defaultMessageProcess(msg);
				break;
			}
		}

		protected void onOffline() {
		};

		protected void onHeartBeatCheck() {
		};

		protected void defaultMessageProcess(Message msg) {
		};
	}

	public class LocalBinder extends Binder {
		public PicService getService() {
			return PicService.this;
		}
	}

	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind is running");
		return mBinder;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate is running");
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "onStart is running");
//		registerHeartBeatCheckAlarm();//发送心跳包的时机 
		
//		Utils.showNotification(this, R.drawable.android_default, "有新的通知消息。","有新的通知消息。" + Utils.formatTime(System.currentTimeMillis()));
		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "onUnbind is running");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy is running");
		super.onDestroy();
	}

	public void registerHeartBeatCheckAlarm() {
		Log.i(TAG, "registerHeartBeatCheckAlarm() start");

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

		Intent intent = new Intent(this, HeartBeatCheckReceiver.class);
		intent.setAction(PicIntent.ACTION_HEARTBEAT_CHECK);

		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);

		long itv = HB_DEFAULT_CHECK_ITV * 1000;
		am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System
				.currentTimeMillis()
				+ itv, itv, pi);

		Log.i(TAG, "registerHeartBeatCheckAlarm() end");
	}

	public void doHeartBeatCheck() {
		Log.i(TAG, "doHeartBeatCheck() start");
		Log.i(TAG, "HeartBeat Interval:" + interval);

		boolean isNeedOffline = false;

		long nowTime = System.currentTimeMillis();
		if (nowTime - lastSendTime > (interval * 1000)) {
			Log.i(TAG, "send heartbeat packet");

			try {
				//发送心跳包
//				LaBiConnection.getInstance().SendHeartBeat();
			} catch (Exception e) {
				Log.e(TAG, e.toString());
				Log.i(TAG, "send heartbeat error");
				isNeedOffline = true;
			}
		} else {
			Log.i(TAG, "nowTime: " + nowTime
					+ ", lastSendTime: " + lastSendTime);
		}

		if (nowTime - lastReceiveTime > (interval * HB_DEFAULT_CHECK_COUNT * 1000)) {
			Log.i(TAG, "client timeout");
			isNeedOffline = true;
		}

		if (isNeedOffline) {
			sendMsg(PicService.ServiceHandler.ID_Message_Offline);
		}


		Log.i(TAG, "doHeartBeatCheck() end");
	}

	private void sendMsg(int what) {
		Message msg = Message.obtain();
		msg.what = what;
		sendMessageToHandler(msg);
	}

	private void sendMsg(int what, int arg1) {
		Message msg = Message.obtain();
		msg.what = what;
		msg.arg1 = arg1;
		sendMessageToHandler(msg);
	}

	private void sendMsg(int what, int arg1, Object obj) {
		Message msg = Message.obtain();
		msg.what = what;
		msg.arg1 = arg1;
		msg.obj = obj;
		sendMessageToHandler(msg);
	}
	
	public static void addServiceHandler(ServiceHandler handler) {
		synchronized (mServiceHandlers) {
			if (mServiceHandlers == null) {
				mServiceHandlers = new ArrayList<ServiceHandler>();
			}
			mServiceHandlers.add(handler);
		}
	}

	public static void removeServiceHandler(ServiceHandler handler) {
		synchronized (mServiceHandlers) {
			if (null != mServiceHandlers)
				mServiceHandlers.remove(handler);
		}
	}
	
	public static void sendMessageToHandler(Message msg) {
		synchronized (mServiceHandlers) {
			for (ServiceHandler handler : mServiceHandlers) {
				Message newMsg = Message.obtain(msg);
				handler.sendMessage(newMsg);
			}
		}
	}

	public static void sendMessageToHandlerDelay(Message msg, int seconds) {
		synchronized (mServiceHandlers) {
			for (ServiceHandler handler : mServiceHandlers) {
				Message newMsg = Message.obtain(msg);
				handler.sendMessageDelayed(newMsg, 1000 * seconds);
			}
		}
	}
	
	public static void promptExitApp(Context context) {
		//退出所有Activity
    	for(int i=0;i<allActivity.size();i++)
    	{
    		((Activity)allActivity.get(i)).finish();
    	}
    	allActivity.clear();
    	//退出Service	
    	PicApp.getApp(context).stopDataService();
    	unRegisterHeartBeatCheckAlarm(context);
    	
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public static void unRegisterHeartBeatCheckAlarm(Context context) {
		Log.i(TAG, "unRegisterHeartBeatCheckAlarm() start");

		AlarmManager am = (AlarmManager)context.getSystemService("alarm");

		Intent intent = new Intent(context, HeartBeatCheckReceiver.class);
		intent.setAction(PicIntent.ACTION_HEARTBEAT_CHECK);

		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

		am.cancel(pi);
	}
	
}
