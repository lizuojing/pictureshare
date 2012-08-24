package com.android.app;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.app.service.PicService;
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
	private BMapManager mBMapMan;
	private MKLocationManager mLocationManager;
	private MapView mMapView;
	private Button deleteButton;
	private Button shareButton;
	private Button detalButton;
	private Button backToOneButton;
	private Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		PicService.allActivity.add(this);

		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("1F572AAE2DC844C03D5AF0C9A001026E03BD1618", new MKGeneralListener() {
		    @Override
		    public void onGetPermissionState(int iError) {
		        // TODO 返回授权验证错误，通过错误代码判断原因，MKEvent中常量值。
		    	Log.i(TAG, "permission error is " + iError);
		    }
		    @Override
		    public void onGetNetworkState(int iError) {
		        // TODO 返回网络错误，通过错误代码判断原因，MKEvent中常量值。
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
				
				if(MotionEvent.ACTION_UP==event.getAction()) {
					if((endX-startX)==0&&(endY-startY)==0) {
						MapView map = (MapView)v;
						Projection projection = map.getProjection();
						GeoPoint fromPixels = projection.fromPixels((int)endX, (int)endY);
						map.getOverlays().add(new MyOverlay(fromPixels));
					}
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
		
		initComponents();
	}

	private void initComponents() {
		backButton = (Button)findViewById(R.id.button1);
		deleteButton = (Button)findViewById(R.id.button2);
		shareButton = (Button)findViewById(R.id.button3);
		detalButton = (Button)findViewById(R.id.button4);
		backToOneButton = (Button)findViewById(R.id.button5);
		
		backButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		shareButton.setOnClickListener(this);
		detalButton.setOnClickListener(this);
		backToOneButton.setOnClickListener(this);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			super.onBackPressed();
			break;
		case R.id.button2:
			Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button3:
			Toast.makeText(this, "回到一级", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button4:
			Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
			break;
		case R.id.button5:
			Toast.makeText(this, "detal", Toast.LENGTH_SHORT).show();
			break;

			
		default:
			break;
		}
		
	}
}
