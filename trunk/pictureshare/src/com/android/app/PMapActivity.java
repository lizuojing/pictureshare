package com.android.app;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import com.android.app.entity.Avatar;
import com.android.app.service.PicService;
import com.android.app.utils.Utils;
import com.android.app.view.OverItemT;
import com.android.app.view.PicDialog;
import com.android.app.view.SharePopupWindow;
import com.android.app.view.PicDialog.OnButtonClickListener;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.Projection;

public class PMapActivity extends MapActivity implements View.OnClickListener{
	protected static final String TAG = "PMapActivity";
	protected static final int ID_DELETE_OVERLAY = 2000;
	private BMapManager mBMapMan;
	private MKLocationManager mLocationManager;
	public static MapView mMapView;
	public static View mPopView = null;
	private Button deleteButton;
	private Button shareButton;
	private Button detalButton;
	private Button backToOneButton;
	private Button backButton;
	
	private boolean isDelete = false;
	private SharePopupWindow sharePopup;
	private OverItemT overitem;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		PicService.allActivity.add(this);
		

		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("1F572AAE2DC844C03D5AF0C9A001026E03BD1618", new MKGeneralListener() {
		    @Override
		    public void onGetPermissionState(int iError) {
		        //  返回授权验证错误，通过错误代码判断原因，MKEvent中常量值。
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
		
		mMapView.setOnTouchListener(new OnTouchListener() {
			
			private float startX;
			private float startY;
			private float endX;
			private float endY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.i(TAG, "start x is " + event.getX() + " start y is " + event.getY());
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					
					break;
				case MotionEvent.ACTION_UP:
					Log.i(TAG, "end x is " + event.getX() + " end y is " + event.getY());
					endX = event.getX();
					endY = event.getY();

					break;

				default:
					break;
				}
				
				if(!isDelete) {//新建
					if(MotionEvent.ACTION_UP==event.getAction()) {
						if((endX-startX)==0&&(endY-startY)==0) {
							MapView map = (MapView)v;
							Projection projection = map.getProjection();
							GeoPoint fromPixels = projection.fromPixels((int)endX, (int)endY);
							map.getOverlays().add(new MyOverlay(fromPixels));
						}
					}
				}else {
					//删除覆盖物
					showDialog(ID_DELETE_OVERLAY);
				}
				return false;
			}
		});
		

		MapController mMapController = mMapView.getController(); // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
				(int) (116.404 * 1E6)); // 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point); // 设置地图中心点
		mMapController.setZoom(12); // 设置地图zoom级别
//		mMapView.setSatellite(true);
		
		// 初始化Location模块
		mLocationManager = mBMapMan.getLocationManager();
		// 通过enableProvider和disableProvider方法，选择定位的Provider
		// mLocationManager.enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
		// mLocationManager.disableProvider(MKLocationManager.MK_GPS_PROVIDER);
		
		// 添加定位图层
		MyLocationOverlay mylocTest = new MyLocationOverlay(this, mMapView);
		mylocTest.enableMyLocation(); // 启用定位
		mylocTest.enableCompass();    // 启用指南针
		mMapView.getOverlays().add(mylocTest);
		
		createPopView();
		
		initComponents();
		updateUI();
	}

	private void createPopView() {
		if(PicApp.list!=null&&PicApp.list.size()<=0) {
			return;
		}
		
		// 添加ItemizedOverlay
		Drawable marker = getResources().getDrawable(R.drawable.iconmarka);  //得到需要标在地图上的资源
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
				.getIntrinsicHeight());   //为maker定义位置和边界
		
		overitem = new OverItemT(marker, this, PicApp.list);
		mMapView.getOverlays().add(overitem); //添加ItemizedOverlay实例到mMapView
		
		// 创建点击mark时的弹出泡泡
		mPopView=super.getLayoutInflater().inflate(R.layout.popview, null);
		mMapView.addView( mPopView,
                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                		null, MapView.LayoutParams.TOP_LEFT));
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
		if(!isDelete) {
			deleteButton.setText(getResources().getString(R.string.create));
		}else {
			deleteButton.setText(getResources().getString(R.string.delete));
		}
	}

	private void initComponents() {
		backButton = (Button)findViewById(R.id.btn_back);
		deleteButton = (Button)findViewById(R.id.btn_create_or_delete);
		shareButton = (Button)findViewById(R.id.btn_share);
		detalButton = (Button)findViewById(R.id.btn_detail);
		backToOneButton = (Button)findViewById(R.id.btn_goback);
		
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
	protected Dialog onCreateDialog(int id) {
		PicDialog dialog = null;

		switch (id) {
		case ID_DELETE_OVERLAY:
			dialog = new PicDialog(this, new OnButtonClickListener() {
				@Override
				public void onOkButtonClicked(PicDialog dialog) {
					dialog.cancel();
				}

				@Override
				public void onCancleButtonClicked(PicDialog dialog) {
					dialog.cancel();
				}
			});
			dialog.setTitle("确定删除吗？");
			dialog.setOkButtonText("确定");
			dialog.setCancleButtonText("取消");
			return dialog;

		default:
			break;
		}
		return super.onCreateDialog(id);
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
	
	public class MyOverlay extends Overlay {
		private GeoPoint geoPoint;
	    public MyOverlay(GeoPoint geoPoint) {
			super();
			this.geoPoint = geoPoint;
		}
	    Paint paint = new Paint();
	    @Override
	    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	        //在天安门的位置绘制一个String
	        Point point = mMapView.getProjection().toPixels(geoPoint, null);
//	        canvas.drawText("★这里是天安门", point.x, point.y, paint);
	        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.annotation), point.x, point.y, paint);
//	        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), null, paint);
	    }
	}

	private void showSharePopup() {
		String picUrl = null;//TODO 需要获取要分享的url
		if (sharePopup == null) {
			sharePopup = new SharePopupWindow(this,picUrl, R.drawable.bg_share_popup_1);
		}
		int[] btnShareLoc = new int[2];
		deleteButton.getLocationInWindow(btnShareLoc);
//		int offX = btnShareLoc[0] - Utils.dipToPixels(this, 6);
		int offY = Utils.getScreenHeight(this) - btnShareLoc[1] + Utils.dipToPixels(this, 5);
		int orientation = getResources().getConfiguration().orientation;
		sharePopup.show(deleteButton, orientation, 0, offY);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			super.onBackPressed();
			break;
		case R.id.btn_create_or_delete:
			isDelete = !isDelete;
			if(!isDelete) {
				deleteButton.setText(getResources().getString(R.string.create));
			}else {
				deleteButton.setText(getResources().getString(R.string.delete));
			}
//			Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_goback:
//			Toast.makeText(this, "回到一级", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_share:
			showSharePopup();
			break;
		case R.id.btn_detail:
			Toast.makeText(this, "detal", Toast.LENGTH_SHORT).show();
			//发送左上右下坐标
			break;

			
		default:
			break;
		}
		
	}
}
