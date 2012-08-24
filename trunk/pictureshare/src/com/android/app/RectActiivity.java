package com.android.app;

import android.os.Bundle;
import android.widget.ImageView;

import com.android.app.entity.MyView;

public class RectActiivity extends BaseActvity {
	MyView myView;
	ImageView myImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picedit2);
		myView = (MyView) findViewById(R.id.MyView_font);
	}

}
