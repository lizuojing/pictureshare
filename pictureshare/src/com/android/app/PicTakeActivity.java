package com.android.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 拍照页面
 * 
 * @author Administrator
 * 
 */
public class PicTakeActivity extends Activity {
	private static final String TAG = "PicTakeActivity";
	private Button btn_back;
	private ImageView imageView;
	private String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picedit);
		
		filePath = getIntent().getStringExtra("mCurrentFile");
		Log.i(TAG, "filePath is " + filePath);
		
		initComponents();
		updateUI();
		
	}

	private void updateUI() {
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		imageView.setImageBitmap(bitmap);
	}

	private void initComponents() {
		btn_back = (Button)findViewById(R.id.button1);
		imageView = (ImageView)findViewById(R.id.imageView4);
		
	}
}
