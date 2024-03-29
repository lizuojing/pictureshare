package com.android.app;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.app.entity.Avatar;
import com.android.app.image.ImageLoaderManager;
import com.android.app.view.CellItem;

public class EditActivity extends BaseActivity implements View.OnClickListener {
	private static final String TAG = "EditActivity";
	private ListView listView;
	private Button finishButton;
	private InputMethodManager inputMethodManager;
	private EditText search_edit;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);

		inputMethodManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		initComponents();

		registerButton();

	}

	private void registerButton() {
		finishButton.setOnClickListener(this);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(EditActivity.this, "positon is " + arg2,
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	private void initComponents() {
		finishButton = (Button) findViewById(R.id.button1);

		listView = (ListView) findViewById(R.id.listView1);

		search_edit = (EditText) findViewById(R.id.editText1);
		search_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				search_edit.setFocusable(true);
				search_edit.setFocusableInTouchMode(true);
				search_edit.requestFocus();
				inputMethodManager.showSoftInput(search_edit, 0);
			}
		});

		ListAdapter adapter = new ListAdapter(loadData());
		listView.setPadding(13, 0, 13, 0);
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
		return list;
	}

	class ListAdapter extends BaseAdapter {

		private ArrayList<Avatar> list;
		private ImageLoaderManager imageLoaderManager;

		public ListAdapter(ArrayList<Avatar> list) {
			imageLoaderManager = new ImageLoaderManager(EditActivity.this,
					new Handler(), this);
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
				item = new CellItem(EditActivity.this, avatar,
						imageLoaderManager);
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
		case R.id.button1:
			Log.i(TAG, "edit finish");
			// finish();
			super.onBackPressed();
			break;

		default:
			break;
		}

	}
}
