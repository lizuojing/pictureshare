package com.android.app;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.app.api.ApiResult;
import com.android.app.api.ApiReturnResultListener;
import com.android.app.api.AvatarApi;
import com.android.app.api.AvatarRequestParam;
import com.android.app.data.SettingLoader;
import com.android.app.entity.Avatar;
import com.android.app.entity.Location;
import com.android.app.image.ImageLoaderManager;
import com.android.app.service.PicService;
import com.android.app.utils.Utils;
import com.android.app.view.CellItem;

public class PicListActivity extends BaseActivity implements
		View.OnClickListener {

	protected static final String TAG = "PicListActivity";
	private ListView listView;
	private CheckBox editButton;
	public static ArrayList<Avatar> list;
	private Button backButton;
	private ProgressBar mProgresssBar;
	private ArrayList<String> pointlist;
	private String picTitle;
	private ListAdapter adapter;
	private TextView tiptext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piclist);
		initComponents();

		picTitle = getIntent().getStringExtra("picTitle");
		pointlist = getIntent().getStringArrayListExtra("list");
		uploadPhotoInfo();
	}

	private void uploadPhotoInfo() {
		String gpsProvider = LocationManager.GPS_PROVIDER;
		// String networkProvider = LocationManager.NETWORK_PROVIDER;
		Location location = getLocationInfoByGps(gpsProvider);
		// if(location==null) {
		// getCellLocation(networkProvider);
		// }

		AvatarRequestParam params = new AvatarRequestParam();
		params.setLocation(location);

		params.setPoints(pointlist);
		params.setLabel(picTitle);
		 params.setEmail(SettingLoader.getRegEmail(this));
		params.setPhotoid(SettingLoader.getPhotoId(this));
		params.setEmail("aaa@163.com");
		params.setLabel("1");
		params.setPhotoid("12345256");
		params.setTipsid("23453576");// 大头针

		AvatarApi api = new AvatarApi(this);
		api.setReturnResultListener(new ApiReturnResultListener() {

			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				Log.e(TAG, "onReturnSucceedResult is runing");
				list = (ArrayList<Avatar>) apiResult.getDataEntities();

				if (list != null) {
					mProgresssBar.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
					tiptext.setVisibility(View.GONE);

					adapter = new ListAdapter(list);
					listView.setAdapter(adapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Avatar avatar = list.get(arg2);
							PicService.currentItem = avatar.getImageItem();
							if (editButton.isChecked()) {
								Intent intent = new Intent(
										PicListActivity.this,
										RectActiivity.class);
								if (Utils.isNotNullOrEmpty(avatar.getPath())) {
									intent.putExtra("mCurrentFile", avatar
											.getPath());
									intent.putExtra("mFromPicList", true);
									startActivity(intent);
								} else {
									Toast.makeText(PicListActivity.this,
											"没有可编辑的图片哦！", Toast.LENGTH_SHORT)
											.show();
								}
							} else {
								Intent intent = new Intent(
										PicListActivity.this,
										DishActivity.class);
								Log.e("number", "arg2=" + arg2);
								intent.putExtra("number", arg2);
								startActivity(intent);
							}
						}
					});
				} else {
					mProgresssBar.setVisibility(View.GONE);
					listView.setVisibility(View.GONE);
					tiptext.setVisibility(View.VISIBLE);
					tiptext.setText("没有找到相关的信息哦！稍后再试吧！");
				}

			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				// TODO Auto-generated method stub
				mProgresssBar.setVisibility(View.GONE);
				listView.setVisibility(View.GONE);
				tiptext.setVisibility(View.VISIBLE);
				tiptext.setText("没有找到相关的信息哦！稍后再试吧！");
			}
		});
		api.sendAvatarInfo(1, params, 1, "25");

	}

	private Location getLocationInfoByGps(String provider) {
		Location location = null;
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		android.location.Location lastKnownLocation = locationManager
				.getLastKnownLocation(provider);
		if (lastKnownLocation != null) {
			double Latitude = lastKnownLocation.getLatitude();
			double Longitude = lastKnownLocation.getLongitude();
			if (Latitude != 0 && Longitude != 0) {
				location = new Location(Latitude + "", Longitude + "");
			}
		}
		return location;
	}

	/*
	 * //GSM和Cdma private Location getCellLocation(TelephonyManager manager) {
	 * CellLocation cellLocation = manager.getCellLocation(); if(true){
	 * GsmCellLocation gsm = ((GsmCellLocation) manager .getCellLocation()); //
	 * 获取基站id与地区码 if (gsm != null) { int cid = gsm.getCid(); int lac =
	 * gsm.getLac(); // locationInfo.setCellid(cid); //
	 * locationInfo.setLac(lac); } } else if (cellLocation instanceof
	 * CdmaCellLocation) { CdmaCellLocation cdma = ((CdmaCellLocation) manager
	 * .getCellLocation()); // 获取基站的经纬度 if (cdma != null) { // sid =
	 * cdma.getSystemId(); // bid = cdma.getBaseStationId(); // nid =
	 * cdma.getNetworkId(); // locationInfo.setBid(bid); //
	 * locationInfo.setNid(nid); // locationInfo.setSid(sid); } } return null; }
	 */

	private void initComponents() {
		backButton = (Button) findViewById(R.id.btn_back);
		backButton.setOnClickListener(this);
		editButton = (CheckBox) findViewById(R.id.btn_edit);

		mProgresssBar = (ProgressBar) findViewById(R.id.progressBar1);
		tiptext = (TextView) findViewById(R.id.textView2);
		listView = (ListView) findViewById(R.id.listView1);

		mProgresssBar.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
		tiptext.setVisibility(View.GONE);

		/*
		 * //测试数据 listView.setVisibility(View.VISIBLE); adapter = new
		 * ListAdapter(loadData()); listView.setAdapter(adapter);
		 * 
		 * listView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2, long arg3) { if(editButton.isChecked()) { Intent intent = new
		 * Intent(PicListActivity.this,RectActiivity.class); Avatar avatar =
		 * list.get(arg2); if(Utils.isNotNullOrEmpty(avatar.getPath())) {
		 * intent.putExtra("mCurrentFile",avatar.getPath());
		 * intent.putExtra("mFromPicList", true); startActivity(intent); }else {
		 * Toast.makeText(PicListActivity.this, "没有可编辑的图片哦！",
		 * Toast.LENGTH_SHORT).show(); } }else { Intent intent = new
		 * Intent(PicListActivity.this,DishActivity.class);
		 * startActivity(intent); } } });
		 */
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
			imageLoaderManager = new ImageLoaderManager(PicListActivity.this,
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
				item = new CellItem(PicListActivity.this, avatar,
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
		case R.id.btn_back:
			// finish();
			super.onBackPressed();
			break;

		default:
			break;
		}

	}

}
