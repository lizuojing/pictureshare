package com.android.app;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends BaseActivity {
	private Timer mTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {

			@Override
			public void run() {
//				if (!SettingLoader.isLogin(WelcomeActivity.this)) {
//					Intent intent = new Intent(WelcomeActivity.this,
//							RegAndLoginActivity.class);
//					startActivity(intent);
//				} else {
					Intent intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
					startActivity(intent);
//				}
				finish();

			}
		}, 1000);
	}

	@Override
	protected void onStop() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		super.onStop();
	}
}
