package com.android.app;

import android.app.Activity;
import android.os.Bundle;

import com.android.app.service.PicService;

public class BaseActvity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PicService.allActivity.add(this);
		super.onCreate(savedInstanceState);
	}
}
