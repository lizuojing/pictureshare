package com.android.app;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.app.service.PicService;
import com.android.app.view.FanShapedView;

/**
 * 拍照页面
 * 
 * @author Administrator
 * 
 */
public class DishActivity extends ActivityGroup {
	private static final String TAG = "PicTakeActivity";
	private String filePath;
	private LinearLayout eachLayout;
	private FanShapedView fanShapedView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		filePath = getIntent().getStringExtra("mCurrentFile");
		Log.i(TAG, "filePath is " + filePath);

		setContentView(R.layout.dish);
		
		PicService.allActivity.add(this);
		

		eachLayout = (LinearLayout) findViewById(R.id.each_layout);
		fanShapedView = (FanShapedView) findViewById(R.id.fanshaped);
		Intent intent = new Intent(this, PicTakeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("mCurrentFile", filePath);
		eachLayout.addView(getLocalActivityManager().startActivity("contact",intent).getDecorView());
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		fanShapedView.recycle();
	}
}
