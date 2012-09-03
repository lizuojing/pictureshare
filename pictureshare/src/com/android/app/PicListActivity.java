package com.android.app;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.app.entity.Avatar;
import com.android.app.image.ImageLoaderManager;
import com.android.app.view.MainItem;

public class PicListActivity extends BaseActivity implements View.OnClickListener{
	
	private Button backButton;
	private EditText search_edit;
	private InputMethodManager inputMethodManager;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piclist);
		initComponents();
	}
	
	private void initComponents() {
		backButton = (Button)findViewById(R.id.button1);
		backButton.setOnClickListener(this);
		search_edit = (EditText)findViewById(R.id.editText1);
		inputMethodManager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);  
		
		search_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				search_edit.setFocusable(true);
				search_edit.setFocusableInTouchMode(true);
				search_edit.requestFocus();
				inputMethodManager.showSoftInput(search_edit, 0);
			}
		});
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(PicListActivity.this,DishActivity.class);
				startActivity(intent);

			}
		});
		ListAdapter adapter = new ListAdapter(loadData());
		listView.setAdapter(adapter);

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
			MainItem item = null;
			if (convertView == null) {
				item = new MainItem(PicListActivity.this, avatar,imageLoaderManager);
				convertView = item;
			} else {
				item = (MainItem) convertView;
				item.setItemData(avatar);
			}

			return convertView;
		}

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
//			finish();
			super.onBackPressed();
			break;

		default:
			break;
		}
		
	}

}
