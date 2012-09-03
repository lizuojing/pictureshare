package com.android.app;

import android.app.Activity;
import android.os.Bundle;

import com.android.app.service.PicService;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PicService.allActivity.add(this);
		super.onCreate(savedInstanceState);
	}
}
