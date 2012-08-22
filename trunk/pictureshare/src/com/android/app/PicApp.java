package com.android.app;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.android.app.service.PicService;

public class PicApp extends Application {
	private static final String TAG = "PicApp";
	private static Context mContext;
    private PicService picService;
	
	  private ServiceConnection mConnection = new ServiceConnection() 
	    {

			public void onServiceConnected(ComponentName className, IBinder service) 
	        {
				picService = ((PicService.LocalBinder)service).getService();
	        }

	        public void onServiceDisconnected(ComponentName className) 
	        {
	        	picService = null;
	        }
	    };

	@Override
	public void onCreate() 
	{
		super.onCreate();
		Log.i(TAG, "App onCreate");
		mContext = this.getApplicationContext();
		
		startService(new Intent(this, PicService.class));
		bindService(new Intent(this, PicService.class), mConnection, Context.BIND_AUTO_CREATE);
		
		
		
	}
	
	public static Context getAppContext() {
		return mContext;
	}
	
	public PicService getService()
	{
		return picService;
	}
	
	public static PicApp getApp(Context context)
	{
		return (PicApp)context.getApplicationContext();
	}
	
	@Override
	public void onTerminate() 
	{
		stopDataService();
		
		super.onTerminate();
	}
	
	public void stopDataService()
	{
		if (mConnection != null)
		{
			unbindService(mConnection);
			mConnection = null;
			stopService(new Intent(this, PicService.class));
		}
	}
	
}
