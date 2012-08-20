package com.android.app;

import com.android.app.entity.MyView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class RectActiivity extends Activity {
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
