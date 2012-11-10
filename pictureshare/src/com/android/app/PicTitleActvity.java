package com.android.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.app.utils.ImageUtil;
import com.android.app.utils.Utils;

public class PicTitleActvity extends BaseActivity implements OnClickListener{
	private static final String TAG = "PicTitleActvity";
	private Button backButton;
	private EditText picTitle;
	private ImageView picture;
	private ImageView confirmButton;
	private String filePath;
	private Bitmap bitmap;
	private ArrayList<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictitle);
		
		filePath = getIntent().getStringExtra("mCurrentFile");
		list = getIntent().getStringArrayListExtra("list");
		
		initComponents();
		updateUI();
	}

	private void updateUI() {
		bitmap = getBitmap(filePath);
		if(bitmap!=null) {
			picture.setImageBitmap(bitmap);//TODO 图片处理
		}else {
//			picture.setImageResource(R.drawable.startup);
			picture.setImageResource(R.drawable.android_default);
		}
	}

	private Bitmap getBitmap(String filePath) {
		int width=Utils.getScreenHeight(this)/4;
		int photoOrientation=0;
		try {
			ExifInterface exifInterface=new ExifInterface(filePath);
			photoOrientation=exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
			switch (photoOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_180:
				photoOrientation=180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				photoOrientation=270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				photoOrientation=90;
				break;
			default:
				photoOrientation=0;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return ImageUtil.getThumbnail(filePath,width, width,photoOrientation);
		} catch (OutOfMemoryError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
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
			boolean mFromPicList = getIntent().getBooleanExtra("mFromPicList", false);
			if(mFromPicList) {
				//拷贝图片
				String savepath = new File(Utils.getCacheDir(),filePath).getAbsolutePath();
				boolean writeImage = ImageUtil.writeImage(savepath, bitmap);
				Log.i(TAG, "writeImage is " + writeImage);
			}
			String title = picTitle.getText().toString();
			//title 处理
			Intent intent = new Intent(this,PicListActivity.class);
			intent.putExtra("picTitle", title);
			intent.putExtra("mCurrentFile", filePath);
			intent.putStringArrayListExtra("list", list);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}
