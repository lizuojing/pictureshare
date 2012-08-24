package com.android.app;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
	private ImageView imageView;
	private String filePath;
	private LinearLayout eachLayout;
	private FanShapedView fanShapedView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		filePath = getIntent().getStringExtra("mCurrentFile");
		Log.i(TAG, "filePath is " + filePath);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.dish);
		
		PicService.allActivity.add(this);
		

		eachLayout = (LinearLayout) findViewById(R.id.each_layout);
		fanShapedView = (FanShapedView) findViewById(R.id.fanshaped);

		eachLayout.addView(getLocalActivityManager().startActivity(
				"contact",
				new Intent(this, PicTakeActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());

		setListener();

		// initComponents();
		// updateUI();

	}

	private void setListener() {
		fanShapedView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fanShapedView.isStartActivity()) {
					String module = fanShapedView.getFirstItem();
					Intent intent = new Intent();
					intent.setAction(PicTakeActivity.ACTION_3D);
					intent.putExtra("orientation", FanShapedView.orient);
					intent.putExtra("module", module);
					sendBroadcast(intent);
				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		fanShapedView.recycle();
	}

	private void updateUI() {
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		imageView.setImageBitmap(bitmap);
	}

	private void initComponents() {
		// imageView = (ImageView)findViewById(R.id.media_image);
	}
}
