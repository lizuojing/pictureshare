package com.android.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PicTitleActvity extends BaseActvity implements OnClickListener{
	private Button backButton;
	private EditText picTitle;
	private ImageView picture;
	private ImageView confirmButton;
	private String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictitle);
		
		filePath = getIntent().getStringExtra("mCurrentFile");
		
		initComponents();
		updateUI();
	}

	private void updateUI() {
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		if(bitmap!=null) {
			picture.setImageBitmap(bitmap);//TODO 图片处理
		}else {
			picture.setImageResource(R.drawable.startup);
//			picture.setImageResource(R.drawable.android_default);
		}
	}

	private void initComponents() {
		backButton = (Button)findViewById(R.id.button1);
		picTitle = (EditText)findViewById(R.id.pictitle);
		picture = (ImageView)findViewById(R.id.imageView1);
		confirmButton = (ImageView)findViewById(R.id.confirm);
		
		backButton.setOnClickListener(this);
		confirmButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			finish();
			break;
		case R.id.confirm:
			String title = picTitle.getText().toString();
			//title 处理
			Intent intent = new Intent(this,DishActivity.class);
			intent.putExtra("picTitle", title);
			intent.putExtra("mCurrentFile", filePath);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
}
