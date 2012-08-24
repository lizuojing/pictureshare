package com.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.android.app.entity.MyView;

public class RectActiivity extends BaseActvity implements OnClickListener {
	MyView myView;
	ImageView myImageView;
	private ImageView searchButton;
	private String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picedit_area);

		initComponents();

		filePath = getIntent().getStringExtra("mCurrentFile");

		myView = (MyView) findViewById(R.id.MyView_font);
//		myView.setBitmap(filePath);

	}

	private void initComponents() {
		searchButton = (ImageView) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myView.setBitmap(filePath);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchButton:
			Intent intent = new Intent(this, DishActivity.class);
			intent.putExtra("mCurrentFile", filePath);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

}
