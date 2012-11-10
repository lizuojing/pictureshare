package com.android.app;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.app.entity.Avatar;
import com.android.app.image.ImageLoaderManager;
import com.android.app.utils.Utils;
import com.android.app.view.CellItem;

public class PicListActivity extends BaseActivity implements View.OnClickListener{
	
	private ListView listView;
	private CheckBox editButton;
	private ArrayList<Avatar> list;
	private Button backButton;
	private DataLoaderTask loadDataTask;
	private ProgressBar mProgresssBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piclist);
		initComponents();
		
		if(loadDataTask!=null) {
			loadDataTask.cancel(true);
			loadDataTask = null;
		}
		loadDataTask = new DataLoaderTask();
	}
	
	final class DataLoaderTask extends AsyncTask<Void, Integer, Long> {

		@Override
		protected Long doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			mProgresssBar.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			
			
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			mProgresssBar.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
			super.onPreExecute();
		}
		
		
		
	}


	
	private void initComponents() {
		backButton = (Button)findViewById(R.id.btn_back);
		backButton.setOnClickListener(this);
		editButton = (CheckBox)findViewById(R.id.btn_edit);
		
		mProgresssBar = (ProgressBar)findViewById(R.id.progressBar1);
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(editButton.isChecked()) {
					Intent intent = new Intent(PicListActivity.this,RectActiivity.class);
					Avatar avatar = list.get(arg2);
					if(Utils.isNotNullOrEmpty(avatar.getPath())) {
						intent.putExtra("mCurrentFile",avatar.getPath());
						intent.putExtra("mFromPicList", true);
						startActivity(intent);
					}else {
						Toast.makeText(PicListActivity.this, "没有可编辑的图片哦！", Toast.LENGTH_SHORT).show();
					}
				}else {
					Intent intent = new Intent(PicListActivity.this,DishActivity.class);
					startActivity(intent);
				}
			}
		});
		ListAdapter adapter = new ListAdapter(loadData());
		listView.setAdapter(adapter);

	}
	
	private ArrayList<Avatar> loadData() {
		list = new ArrayList<Avatar>();
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
		
		avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的志愿者证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);
		
		avatar = new Avatar();
		avatar.setPath("");
		avatar.setTitle("我的志愿者证");
		avatar.setTime(System.currentTimeMillis());
		list.add(avatar);
		return list;
	}
	class ListAdapter extends BaseAdapter {

		private ArrayList<Avatar> list;
		private ImageLoaderManager imageLoaderManager;

		public ListAdapter(ArrayList<Avatar> list) {
			imageLoaderManager = new ImageLoaderManager(PicListActivity.this,new Handler(), this);
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Avatar avatar = list.get(position);
			CellItem item = null;
			if (convertView == null) {
				item = new CellItem(PicListActivity.this, avatar,imageLoaderManager);
				convertView = item;
			} else {
				item = (CellItem) convertView;
				item.setItemData(avatar);
			}

			return convertView;
		}

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
//			finish();
			super.onBackPressed();
			break;

		default:
			break;
		}
		
	}

}
