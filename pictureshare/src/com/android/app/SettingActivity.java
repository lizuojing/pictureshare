package com.android.app;

import java.util.ArrayList;

import com.android.app.api.ApiResult;
import com.android.app.api.ApiReturnResultListener;
import com.android.app.api.OtherApi;
import com.android.app.api.UserApi;
import com.android.app.entity.Detail;
import com.android.app.entity.Point;
import com.android.app.entity.User;
import com.android.app.entity.VersionInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 设置页面
 * 
 * @author Administrator
 * 
 */
public class SettingActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout personalSetting;
	private RelativeLayout valuationSetting;
	private RelativeLayout yonghuzhuceLayout;
	private RelativeLayout yonghudenglu;
	private RelativeLayout yonghurename;
	private RelativeLayout DetailMap;
	private RelativeLayout addtips;
	private RelativeLayout refreshlist;
	private Button backButton;

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
			Toast.makeText(this, "添加大头针", Toast.LENGTH_SHORT).show();
			refreshlist();
			break;
		default:
			break;
		}

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
