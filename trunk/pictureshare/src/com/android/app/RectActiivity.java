package com.android.app;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.android.app.api.AvatarApi;
import com.android.app.data.SettingLoader;
import com.android.app.entity.MyView;
import com.android.app.entity.Point;

public class RectActiivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "RectActiivity";
	MyView myView;
	ImageView myImageView;
	private ImageView searchButton;
	private String filePath;
	private Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picedit_area);
		initComponents();

		filePath = getIntent().getStringExtra("mCurrentFile");

		myView = (MyView) findViewById(R.id.MyView_font);
		myView.setBitmap(filePath, this);
		
		uploadFile(filePath);

	}

	private void uploadFile(String filePath2) {
		AvatarApi api = new AvatarApi(this);
		String email = SettingLoader.getRegEmail(this);
		api.uploadAvatar(this, 1, filePath, email!=null?email:"111@163.com");
	}


	private void initComponents() {
		searchButton = (ImageView) findViewById(R.id.searchButton);
		backButton = (Button) findViewById(R.id.button1);
		searchButton.setOnClickListener(this);
		backButton.setOnClickListener(this);

	}
	
	@Override
	protected void onStop() {
		Log.i(TAG, "onstop is running");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.i(TAG, "onDestroy is running");
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myView.setBitmap(filePath, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchButton:
			Point lt, // 左上
			lb, // 左下
			rb, // 右下
			rt; // 右上
			lt = myView.getLeft_top().getCenterPoint();
			lb = myView.getLeft_bottom().getCenterPoint();
			rb = myView.getRight_bottom().getCenterPoint();
			rt = myView.getRight_top().getCenterPoint();
			
			ArrayList<String> list = new ArrayList<String>();
			list.add(lt.toString());
			list.add(lb.toString());
			list.add(rb.toString());
			list.add(rt.toString());

			Intent intent = new Intent(this, PicTitleActvity.class);
			intent.putStringArrayListExtra("list", list);
			intent.putExtra("mCurrentFile", filePath);
			intent.putExtra("mFromPicList",getIntent().getBooleanExtra("mFromPicList", false));
			startActivity(intent);
			break;
		case R.id.button1:
			super.onBackPressed();
			break;

		default:
			break;
		}

	}
}
