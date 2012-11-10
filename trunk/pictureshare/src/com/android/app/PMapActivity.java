package com.android.app;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import android.widget.Button;
import android.widget.Toast;

import com.android.app.api.ApiResult;
import com.android.app.api.ApiReturnResultListener;
import com.android.app.api.AvatarApi;
import com.android.app.api.OtherApi;
import com.android.app.entity.Avatar;
import com.android.app.entity.Detail;
import com.android.app.entity.Point;
import com.android.app.service.PicService;
import com.android.app.utils.Utils;
import com.android.app.view.OverItemT;
import com.android.app.view.SharePopupWindow;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;

public class PMapActivity extends MapActivity implements View.OnClickListener {
	protected static final String TAG = "PMapActivity";
	protected static final int ID_DELETE_OVERLAY = 2000;
	public static final int STATE_CREATE = 0;
	public static final int STATE_DEL = 1;
	public static final int STATE_POINT = 2;
	private BMapManager mBMapMan;
	public static MapView mMapView;
	public static View mPopView = null;
	private Button deleteButton;
	private Button shareButton;
	private Button detalButton;
	private Button backToOneButton;
	private Button backButton;

	private SharePopupWindow sharePopup;
	private OverItemT overitem;

	public static int currentStatus = 2;// 0代表新建 1 代表删除

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		PicService.allActivity.add(this);

		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("1F572AAE2DC844C03D5AF0C9A001026E03BD1618",
				new MKGeneralListener() {
					@Override
					public void onGetPermissionState(int iError) {
						// 返回授权验证错误，通过错误代码判断原因，MKEvent中常量值。
						Log.i(TAG, "permission error is " + iError);
					}

					@Override
					public void onGetNetworkState(int iError) {
						// 返回网络错误，通过错误代码判断原因，MKEvent中常量值。
						Log.i(TAG, "Network error is " + iError);
					}
				});
		super.initMapActivity(mBMapMan);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		mMapView.setDoubleClickZooming(false);


		MapController mMapController = mMapView.getController(); // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		mMapController.setZoom(12); // 设置地图zoom级别
	
		createPopView();

		initComponents();
		updateUI();
	}

	private ArrayList<Avatar> handleList(ArrayList<Avatar> list) {
		for(int i=list.size()-1;i>=0;i--) {
			Avatar loadedImage = list.get(i);
			if(loadedImage.getLatitude()<=0||loadedImage.getLongitude()<=0) {
				list.remove(loadedImage);
			}
		}
		Log.i(TAG, "list size is " + list.size());
		
		return list;
	}

	
	private void createPopView() {
		if (PicApp.list != null && PicApp.list.size() <= 0) {
			return;
		}

		// 添加ItemizedOverlay
		Drawable marker = getResources().getDrawable(R.drawable.iconmarka); // 得到需要标在地图上的资源
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
				.getIntrinsicHeight()); // 为maker定义位置和边界

		overitem = new OverItemT(marker, this, handleList(PicApp.list));
		mMapView.getOverlays().add(overitem); // 添加ItemizedOverlay实例到mMapView
		
		// 创建点击mark时的弹出泡泡
		mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
		mMapView.addView(mPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.TOP_LEFT));
		mPopView.setVisibility(View.GONE);
	}

	@Override
	protected void onStart() {
		if (sharePopup != null) {
			sharePopup.refreshWeiboButton();
		}
		super.onStart();
	}

	private void updateUI() {
		if (currentStatus == STATE_CREATE) {
			deleteButton.setText(getResources().getString(R.string.create));
		} else if(currentStatus == STATE_DEL){
			deleteButton.setText(getResources().getString(R.string.delete));
		}else{
			deleteButton.setText(getResources().getString(R.string.nomal));
		}
	}

	private void initComponents() {
		backButton = (Button) findViewById(R.id.btn_back);
		deleteButton = (Button) findViewById(R.id.btn_create_or_delete);
		shareButton = (Button) findViewById(R.id.btn_share);
		detalButton = (Button) findViewById(R.id.btn_detail);
		backToOneButton = (Button) findViewById(R.id.btn_goback);

		backButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		shareButton.setOnClickListener(this);
		detalButton.setOnClickListener(this);
		backToOneButton.setOnClickListener(this);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}


	@Override
	protected void onDestroy() {
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

	private void showSharePopup() {
		String picUrl = null;// TODO 需要获取要分享的url
		if (sharePopup == null) {
			sharePopup = new SharePopupWindow(this, picUrl,
					R.drawable.bg_share_popup_1);
		}
		int[] btnShareLoc = new int[2];
		deleteButton.getLocationInWindow(btnShareLoc);
		// int offX = btnShareLoc[0] - Utils.dipToPixels(this, 6);
		int offY = Utils.getScreenHeight(this) - btnShareLoc[1]
				+ Utils.dipToPixels(this, 5);
		int orientation = getResources().getConfiguration().orientation;
		sharePopup.show(deleteButton, orientation, 0, offY);
	}

	public static int getCurrentStatus() {
		return currentStatus;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			super.onBackPressed();
			break;
		case R.id.btn_create_or_delete:
			if(currentStatus==2) {
				currentStatus = -1;
			}
			currentStatus++;
			updateUI();
			break;
		case R.id.btn_goback:
			// Toast.makeText(this, "回到一级", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_share:
			showSharePopup();
			break;
		case R.id.btn_detail:
//			Toast.makeText(this, "detal", Toast.LENGTH_SHORT).show();
			// 发送左上右下坐标
			OtherApi otherapi = new OtherApi(this);
			otherapi.setReturnResultListener(new ApiReturnResultListener() {
				@SuppressWarnings("unchecked")
				@Override
				public <T> void onReturnSucceedResult(int requestCode,
						ApiResult<T> apiResult) {
					ArrayList<Object> infos = (ArrayList<Object>) apiResult
							.getEntities();
					
					Log.e(TAG, "detail result is " + infos);
					if (infos == null || infos.size() <= 0) {
						Toast.makeText(PMapActivity.this, "没有可显示的图片", Toast.LENGTH_SHORT).show();
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
			detail.lbPoint = new Point(mMapView.getLeft(),mMapView.getTop());
			detail.ruPoint = new Point(mMapView.getRight(), mMapView.getBottom());
			otherapi.getDetil(1, detail);
			break;

		default:
			break;
		}

	}
}
