package com.android.app;

import android.os.Bundle;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;

public class PMapActivity extends MapActivity {
	private BMapManager mBMapMan;
	private MKLocationManager mLocationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("1F572AAE2DC844C03D5AF0C9A001026E03BD1618", null);
		super.initMapActivity(mBMapMan);

		MapView mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件

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
}
