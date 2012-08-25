package com.android.app;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.android.app.entity.Avatar;
import com.android.app.image.ImageLoaderManager;
import com.android.app.view.MainItem;

/**
 * 拍照页面
 * 
 * @author Administrator
 * 
 */
public class PicTakeActivity extends BaseActvity implements OnClickListener{
	private static final String TAG = "PicTakeActivity";
	protected static final String ACTION_3D = "action_3d";
	private Button btn_back;
	private ImageView imageView;
	private String filePath;
	private ListView listView;
	
	private ArrayList<Avatar> list = null;
	private ListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.picedit);
		filePath = getIntent().getStringExtra("mCurrentFile");
		Log.i(TAG, "filePath is " + filePath);
		
		list = loadData();
		initComponents();
		updateUI();
		
	}
	
	private ArrayList<Avatar> loadData() {
		ArrayList<Avatar> list = new ArrayList<Avatar>();
		Avatar avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的工作证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);

		avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的贵宾证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);

		avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的志愿者证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);

		avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的贵宾证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);

		avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的志愿者证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);

		avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的贵宾证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);

		avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的志愿者证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);
		return list;
	}


	private void updateUI() {
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			imageView.setImageBitmap(bitmap);
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initComponents() {
		btn_back = (Button)findViewById(R.id.button1);
		imageView = (ImageView)findViewById(R.id.imageView4);
		listView = (ListView)findViewById(R.id.listView1);
		
		btn_back.setOnClickListener(this);
		
//		listAdapter = new ListAdapter();
//		listView.setAdapter(listAdapter);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			super.onBackPressed();
			break;

		default:
			break;
		}
		
	}
	
	class ListAdapter extends BaseAdapter {

		private ImageLoaderManager imageLoaderManager;

		public ListAdapter() {
			imageLoaderManager = new ImageLoaderManager(PicTakeActivity.this,new Handler(), this);
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
			Avatar avatar = list.get(position);
			MainItem item = null;
			if (convertView == null) {
				item = new MainItem(PicTakeActivity.this, avatar,imageLoaderManager);
				convertView = item;
			} else {
				item = (MainItem) convertView;
				item.setItemData(avatar);
			}

			return convertView;
		}

	}
	
}
