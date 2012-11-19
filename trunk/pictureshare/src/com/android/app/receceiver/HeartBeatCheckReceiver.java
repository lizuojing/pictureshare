package com.android.app.receceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.android.app.service.PicService;



public class HeartBeatCheckReceiver extends BroadcastReceiver {
    private final String TAG = "HeartBeatCheckReceiver" ;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "need check heartbeat, send ID_Message_HeartBeat_Check Msg");
		Message msg = Message.obtain();
		msg.what = PicService.ServiceHandler.ID_Message_HeartBeat_Check;
		PicService.sendMessageToHandler(msg);
	}
	
}
