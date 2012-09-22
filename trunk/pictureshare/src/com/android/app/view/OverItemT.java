package com.android.app.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.android.app.PMapActivity;
import com.android.app.R;
import com.android.app.entity.Avatar;
import com.android.app.view.PicDialog.OnButtonClickListener;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;

public class OverItemT extends ItemizedOverlay<OverlayItem> {
	private static final String TAG = "OverItemT";
	public List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	public List<MyOverlay> mMyGeoList = new ArrayList<MyOverlay>();
	private Drawable marker;
	private Context mContext;
	private MapView mapView;

	/*
	 * private double mLat1 = 39.90923; // point1纬度 private double mLon1 =
	 * 116.357428; // point1经度
	 * 
	 * private double mLat2 = 39.90923; private double mLon2 = 116.397428;
	 * 
	 * private double mLat3 = 39.90923; private double mLon3 = 116.437428;
	 */

	public OverItemT(Drawable marker, Context context, ArrayList<Avatar> list) {
		super(boundCenterBottom(marker));

		this.marker = marker;
		this.mContext = context;

		for (int i = 0; i < list.size(); i++) {
			Avatar avatar = list.get(i);
			double lat = avatar.getLatitude();
			double lon = avatar.getLongitude();
			Log.i(TAG, "Latitude is " + lat + " longitude is " + lon);

			if (lat != 0 && lon != 0) {
				// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
				GeoPoint p1 = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
				// 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
				mGeoList.add(new OverlayItem(p1, "P1", "point1"));
			}
		}
		/*
		 * // 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6) GeoPoint p1 = new GeoPoint((int)
		 * (mLat1 * 1E6), (int) (mLon1 * 1E6)); GeoPoint p2 = new GeoPoint((int)
		 * (mLat2 * 1E6), (int) (mLon2 * 1E6));
		 * 
		 * // 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段 mGeoList.add(new
		 * OverlayItem(p1, "P1", "point1")); mGeoList.add(new OverlayItem(p2,
		 * "P2", "point2")); if(list.size() == 3) { GeoPoint p3 = new
		 * GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6)); mGeoList.add(new
		 * OverlayItem(p3, "P3", "point3")); }
		 */
		populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	public void updateOverlay() {
		populate();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		// Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
		Projection projection = mapView.getProjection();
		for (int index = size() - 1; index >= 0; index--) { // 遍历mGeoList
			OverlayItem overLayItem = getItem(index); // 得到给定索引的item

			String title = overLayItem.getTitle();
			// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
			Point point = projection.toPixels(overLayItem.getPoint(), null);

			// 可在此处添加您的绘制代码
			Paint paintText = new Paint();
			paintText.setColor(Color.BLUE);
			paintText.setTextSize(15);
			canvas.drawText(title, point.x - 30, point.y, paintText); // 绘制文本
		}

		super.draw(canvas, mapView, shadow);
		// 调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
		boundCenterBottom(marker);
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mGeoList.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mGeoList.size();
	}

	@Override
	// 处理当点击事件
	protected boolean onTap(final int i) {
		Log.i(TAG, "onTap is running");
		if (PMapActivity.currentStatus == PMapActivity.STATE_DEL) {
			PicDialog dialog = new PicDialog(mContext,
					new OnButtonClickListener() {
						@Override
						public void onOkButtonClicked(PicDialog dialog) {
							mapView.getOverlays().clear();
							populate();
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
			dialog.show();
		} else {
			setFocus(mGeoList.get(i));
			// 更新气泡位置,并使之显示
			GeoPoint pt = mGeoList.get(i).getPoint();
			PMapActivity.mMapView.updateViewLayout(PMapActivity.mPopView,
					new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, pt,
							MapView.LayoutParams.BOTTOM_CENTER));
			PMapActivity.mPopView.setVisibility(View.VISIBLE);
			Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(),
					Toast.LENGTH_SHORT).show();
		}

		return super.onTap(i);
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView mapView) {
		this.mapView = mapView;
		Log.i(TAG, "onTap is running ----------" + PMapActivity.currentStatus);
		// 消去弹出的气泡
		PMapActivity.mPopView.setVisibility(View.GONE);

		if (PMapActivity.currentStatus == PMapActivity.STATE_CREATE) {
			Projection projection = mapView.getProjection();
			Point point = projection.toPixels(arg0, null);
			GeoPoint fromPixels = projection.fromPixels(point.x, point.y);
			MyOverlay myOverlay = new MyOverlay(fromPixels);
			mMyGeoList.add(myOverlay);
			mapView.getOverlays().add(myOverlay);
		}

		Log.i(TAG, "lat i " + arg0.getLatitudeE6() + " lon is "
				+ arg0.getLongitudeE6());
		return super.onTap(arg0, mapView);
	}

	private class MyOverlay extends Overlay {
		private GeoPoint geoPoint;

		public MyOverlay(GeoPoint geoPoint) {
			super();
			this.geoPoint = geoPoint;
		}

		Paint paint = new Paint();

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			// 在天安门的位置绘制一个String
			Point point = mapView.getProjection().toPixels(geoPoint, null);
			// canvas.drawText("★这里是天安门", point.x, point.y, paint);
			canvas.drawBitmap(BitmapFactory.decodeResource(mContext
					.getResources(), R.drawable.annotation), point.x, point.y,
					paint);
			// canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
			// R.drawable.ic_launcher), null, paint);
		}
	}
}
