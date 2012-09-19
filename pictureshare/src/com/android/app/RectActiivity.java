package com.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.android.app.entity.MyView;

public class RectActiivity extends BaseActivity implements OnClickListener {
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
		myView.setBitmap(filePath,this);

	}

	

	private void initComponents() {
		searchButton = (ImageView) findViewById(R.id.searchButton);
		backButton = (Button)findViewById(R.id.button1);
		searchButton.setOnClickListener(this);
		backButton.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myView.setBitmap(filePath,this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchButton:
			Intent intent = new Intent(this, PicTitleActvity.class);
			intent.putExtra("mCurrentFile", filePath);
			intent.putExtra("mFromPicList", getIntent().getBooleanExtra("mFromPicList", false));
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
