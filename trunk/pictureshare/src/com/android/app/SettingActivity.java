package com.android.app;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.app.api.ApiResult;
import com.android.app.api.ApiReturnResultListener;
import com.android.app.api.AvatarApi;
import com.android.app.api.AvatarRequestParam;
import com.android.app.api.OtherApi;
import com.android.app.api.UserApi;
import com.android.app.entity.Detail;
import com.android.app.entity.Location;
import com.android.app.entity.Point;
import com.android.app.entity.User;
import com.android.app.entity.VersionInfo;

/**
 * 设置页面
 * 
 * @author Administrator
 * 
 */
public class SettingActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = "SettingActivity";
	private RelativeLayout personalSetting;
	private RelativeLayout valuationSetting;
	private RelativeLayout yonghuzhuceLayout;
	private RelativeLayout yonghudenglu;
	private RelativeLayout yonghurename;
	private RelativeLayout DetailMap;
	private RelativeLayout addtips;
	private RelativeLayout refreshlist;
	private Button backButton;
	private RelativeLayout sendpicLayout;
	private RelativeLayout sendpicinfoLayout;
	private RelativeLayout supportLayout;
	private RelativeLayout picshareLayout;
	private RelativeLayout latestversionLayout;
	private RelativeLayout qrcodeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		initComponents();
	}

	private void initComponents() {

		backButton = (Button) findViewById(R.id.button1);
		personalSetting = (RelativeLayout) findViewById(R.id.personal_setting);
		valuationSetting = (RelativeLayout) findViewById(R.id.valuation_setting);
		yonghuzhuceLayout = (RelativeLayout) findViewById(R.id.yonghuzhuce);
		yonghudenglu = (RelativeLayout) findViewById(R.id.yonghudenglu);
		yonghurename = (RelativeLayout) findViewById(R.id.yonghurename);
		DetailMap = (RelativeLayout) findViewById(R.id.DetailMap);
		addtips = (RelativeLayout) findViewById(R.id.addtips);
		refreshlist = (RelativeLayout) findViewById(R.id.refreshlist);
	
		backButton.setOnClickListener(this);
		personalSetting.setOnClickListener(this);
		valuationSetting.setOnClickListener(this);
		yonghuzhuceLayout.setOnClickListener(this);
		yonghudenglu.setOnClickListener(this);
		yonghurename.setOnClickListener(this);
		DetailMap.setOnClickListener(this);
		addtips.setOnClickListener(this);
		refreshlist.setOnClickListener(this);
		
		//协议测试
		sendpicinfoLayout = (RelativeLayout) findViewById(R.id.sendpicinfo);
		supportLayout = (RelativeLayout) findViewById(R.id.support);
		picshareLayout = (RelativeLayout) findViewById(R.id.picshare);
		latestversionLayout = (RelativeLayout) findViewById(R.id.latestversion);
		sendpicLayout = (RelativeLayout) findViewById(R.id.sendpic);
		qrcodeLayout = (RelativeLayout) findViewById(R.id.qrcode);
		
		sendpicinfoLayout.setOnClickListener(this);
		supportLayout.setOnClickListener(this);
		picshareLayout.setOnClickListener(this);
		latestversionLayout.setOnClickListener(this);
		sendpicLayout.setOnClickListener(this);
		qrcodeLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			super.onBackPressed();
			break;
		case R.id.personal_setting:
			Toast.makeText(this, "个人中心", Toast.LENGTH_SHORT).show();
			break;
		case R.id.valuation_setting:
			Toast.makeText(this, "评价", Toast.LENGTH_SHORT).show();
			break;
		case R.id.yonghuzhuce:
			Toast.makeText(this, "用户注册", Toast.LENGTH_SHORT).show();
			uerRegedit();
			break;
		case R.id.yonghudenglu:
			Toast.makeText(this, "用户登录", Toast.LENGTH_SHORT).show();
			uerLogin();
			break;
		case R.id.yonghurename:
			Toast.makeText(this, "用户改名", Toast.LENGTH_SHORT).show();
			userRename();
			break;
		case R.id.DetailMap:
			Toast.makeText(this, "地图 Detail", Toast.LENGTH_SHORT).show();
			detailMap();
			break;
		case R.id.addtips:
			Toast.makeText(this, "添加大头针", Toast.LENGTH_SHORT).show();
			addtips();
			break;
		case R.id.refreshlist:
			Toast.makeText(this, "二级列表更新", Toast.LENGTH_SHORT).show();
			refreshlist();
			break;
		case R.id.sendpicinfo:
			Toast.makeText(this, "发送图片信息", Toast.LENGTH_SHORT).show();
			sendPicMessage();
			break;
		case R.id.support:
			Toast.makeText(this, "赞", Toast.LENGTH_SHORT).show();
			File exFile = Environment.getExternalStorageDirectory();
			String path = exFile.getAbsolutePath()+"/Camera/691kb.jpg";
			Log.i(TAG, "path is " + path);
			supportPic(path,"111@163.com","+1");
			break;
		case R.id.picshare:
			Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
			String filepath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Camera/691kb.jpg";
			Log.i(TAG, "path is " + filepath);
			sharePic(filepath,"443@163.com");
			break;
		case R.id.latestversion:
			Toast.makeText(this, "最新版本", Toast.LENGTH_SHORT).show();
			checkAppUpdate();
			break;
		case R.id.sendpic:
			Toast.makeText(this, "发送图片", Toast.LENGTH_SHORT).show();
			String picpath =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/Camera/691kb.jpg";
			Log.i(TAG, "path is " + picpath);
			uploadpicture(picpath);
			break;
		case R.id.qrcode:
			Toast.makeText(this, "二维码", Toast.LENGTH_SHORT).show();
			scanqrcode("alksjdljjasdg","1233@163.com");
			break;
		default:
			break;
		}

	}


	private void scanqrcode(String photoid,String email) {
		AvatarApi avatarApi = new AvatarApi(this);
		avatarApi.setReturnResultListener(new ApiReturnResultListener() {
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				Log.i(TAG, "apiResult is " + apiResult.getResultCode());
			
			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		avatarApi.qrcodeAuth(this,1,photoid,email);
	}

	private void sharePic(String filepath,String email) {
		AvatarApi avatarApi = new AvatarApi(this);
		avatarApi.setReturnResultListener(new ApiReturnResultListener() {
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				Log.i(TAG, "apiResult is " + apiResult.getResultCode());
			
			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		avatarApi.shareAvatar(this, 1, filepath, email);
	}

	private void supportPic(String path,String email,String support) {
		AvatarApi avatarApi = new AvatarApi(this);
		avatarApi.setReturnResultListener(new ApiReturnResultListener() {
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				Log.i(TAG, "apiResult is " + apiResult.getResultCode());
			
			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		avatarApi.likeAvatar(this, 1, path, email, support);
	}

	private void uploadpicture(String filepath) {
		AvatarApi avatarApi = new AvatarApi(this);
		avatarApi.setReturnResultListener(new ApiReturnResultListener() {
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				Log.i(TAG, "apiResult is " + apiResult.getResultCode());
			
			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		avatarApi.uploadAvatar(SettingActivity.this,0,filepath,"adsfa@163.com");
	}
	
	private void checkAppUpdate() {
		OtherApi otherApi = new OtherApi(this);
		otherApi.setReturnResultListener(new ApiReturnResultListener() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				Log.i(TAG, "apiResult is " + apiResult.getResultCode());
				ArrayList<VersionInfo> infos = (ArrayList<VersionInfo>) apiResult
						.getEntities();
				if (infos == null || infos.size() <= 0) {
					return;
				}
				VersionInfo newVersionInfo = infos.get(0);
				if (newVersionInfo.getUpdateType() != VersionInfo.UpdateType.NO_UPDATE) {

				} else {

				}
			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		otherApi.getLatestAppVersion(0,"1.0");
	}
	
	private void sendPicMessage() {
		AvatarApi api = new AvatarApi(this);
		api.setReturnResultListener(new ApiReturnResultListener() {
			
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
			}
			
			@Override
			public <T> void onReturnFailResult(int requestCode, ApiResult<T> apiResult) {
				// TODO Auto-generated method stub
				
			}
		});
		int[] a = new int[]{50,50};
		AvatarRequestParam params = new AvatarRequestParam();
		Location location = new Location("39.90923","116.357428");
		params.setLocation(location);
		ArrayList<Point> list = new ArrayList<Point>();
		for(int i=0;i<4;i++) {
			Point point = new Point(50,50);
			list.add(point);
		}
		params.setPoints(list);
		params.setLabel("杭州西湖");
		params.setEmail("aaa@163.com");
		params.setPhotoid("12345256");
		params.setTipsid("23453576");
		api.sendAvatarInfo(1, params, 1, "25");
		
	}

	private void uerRegedit() {
		UserApi userApi = new UserApi(this);
		userApi.setReturnResultListener(new ApiReturnResultListener() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				ArrayList<Object> infos = (ArrayList<Object>) apiResult
						.getEntities();
				if (infos == null || infos.size() <= 0) {
					return;
				}

			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		User user = new User();
		user.setAddress("beiing");
		user.setEmail("iamwxy@126.com");
		user.setBirthday("320226534158515744");
		user.setPassword("111111");
		user.setSex("1");
		user.setTel("13333333333");
		user.setUsername("android");
		userApi.regeditUser(1, user);
	}

	private void uerLogin() {
		UserApi userApi = new UserApi(this);
		userApi.setReturnResultListener(new ApiReturnResultListener() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				ArrayList<Object> infos = (ArrayList<Object>) apiResult
						.getEntities();
				if (infos == null || infos.size() <= 0) {
					return;
				}

			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		User user = new User();
		user.setEmail("iamwxy@126.com");
		user.setPassword("111111");
		userApi.login(1, user);
	}

	private void userRename() {
		UserApi userApi = new UserApi(this);
		userApi.setReturnResultListener(new ApiReturnResultListener() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				ArrayList<Object> infos = (ArrayList<Object>) apiResult
						.getEntities();
				if (infos == null || infos.size() <= 0) {
					return;
				}

			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		User user = new User();
		user.setEmail("iamwxy@126.com");
		user.setUsername("newName");
		userApi.reName(1, user);
	}

	private void detailMap() {
		OtherApi otherapi = new OtherApi(this);
		otherapi.setReturnResultListener(new ApiReturnResultListener() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				ArrayList<Object> infos = (ArrayList<Object>) apiResult
						.getEntities();
				if (infos == null || infos.size() <= 0) {
					return;
				}

			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		Detail detail = new Detail();
		detail.lbPoint = new Point(121.123f, 123.123123123f);
		detail.ruPoint = new Point(121.123f, 123.123123123f);

		otherapi.getDetil(1, detail);
	}

	private void addtips() {
		OtherApi otherapi = new OtherApi(this);
		otherapi.setReturnResultListener(new ApiReturnResultListener() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				ArrayList<Object> infos = (ArrayList<Object>) apiResult
						.getEntities();
				if (infos == null || infos.size() <= 0) {
					return;
				}

			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();

			}
		});
		otherapi.addtips(1, null);
	}

	private void refreshlist() {
		OtherApi otherapi = new OtherApi(this);
		otherapi.setReturnResultListener(new ApiReturnResultListener() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> void onReturnSucceedResult(int requestCode,
					ApiResult<T> apiResult) {
				ArrayList<Object> infos = (ArrayList<Object>) apiResult
						.getEntities();
				if (infos == null || infos.size() <= 0) {
					return;
				}
			}

			@Override
			public <T> void onReturnFailResult(int requestCode,
					ApiResult<T> apiResult) {
				int failCode = apiResult.getFailCode();
			}
		});
		otherapi.refreshlist(1, null);
	}
}
