package com.android.app;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.app.entity.Block;
import com.android.app.image.ImageLoaderManager;
import com.android.app.service.PicService;
import com.android.app.utils.ImageUtil;
import com.android.app.utils.Utils;
import com.android.app.view.MenueItem;

/**
 * 拍照页面
 * 
 * @author Administrator
 * 
 */
public class PicTakeActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "PicTakeActivity";
	protected static final String ACTION_3D = "action_3d";
	private Button btn_back;
	private ImageView imageView;
	private String filePath;
	private ListView listView;
	
	private ArrayList<Block> list = null;
	private ListAdapter listAdapter;
	private ImageView bottom_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.picedit);
		filePath = getIntent().getStringExtra("mCurrentFile");
		Log.i(TAG, "filePath is " + filePath);
		
		list = PicService.getCurrrentBlocks();
		initComponents();
		updateUI();
		
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
	

	private void updateUI() {
		try {
			Bitmap bitmap = null;
			if(Utils.isNotNullOrEmpty(filePath)) {
				 bitmap = getBitmap(filePath);
			}else {
				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android_default);
			}
			imageView.setImageBitmap(bitmap);
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComponents() {
		btn_back = (Button)findViewById(R.id.btn_back);
		imageView = (ImageView)findViewById(R.id.imageView4);
		bottom_button = (ImageView)findViewById(R.id.imageView1);
		listView = (ListView)findViewById(R.id.listView1);
		
		btn_back.setOnClickListener(this);
		bottom_button.setOnClickListener(this);
		
		listAdapter = new ListAdapter(list);
		listView.setAdapter(listAdapter);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView1:
//			Intent intent = new Intent(this,PMapActivity.class);
//			startActivity(intent);

			//TODO 用浏览器打开指定的url
			Intent viewIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.baidu.com"));
			startActivity(viewIntent);

			break;
			
		case R.id.btn_back:
			super.onBackPressed();
			break;
		default:
			break;
		}
		
	}
	
	class ListAdapter extends BaseAdapter {

		private ImageLoaderManager imageLoaderManager;
		private ArrayList<Block> list;

		public ListAdapter(ArrayList<Block> list) {
			imageLoaderManager = new ImageLoaderManager(PicTakeActivity.this,new Handler(), this);
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Block block = list.get(position);
			MenueItem item = null;
			if (convertView == null) {
				item = new MenueItem(PicTakeActivity.this, block,imageLoaderManager);
				convertView = item;
			} else {
				item = (MenueItem) convertView;
				item.setItemData(block);
			}

			return convertView;
		}

	}
	
}
