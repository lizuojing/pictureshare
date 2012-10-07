package com.android.app.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

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
	private Drawable marker;
	private Context mContext;
	private MapView mapView;
	private ArrayList<Avatar> list;

	/**
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
		this.list = list;

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Avatar avatar = list.get(i);
				double lat = avatar.getLatitude();
				double lon = avatar.getLongitude();
				// Log.i(TAG, "Latitude is " + lat + " longitude is " + lon);

				if (lat != 0 && lon != 0) {
					// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
					GeoPoint p1 = new GeoPoint((int) (lat * 1E6),
							(int) (lon * 1E6));
					// 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
					mGeoList.add(new OverlayItem(p1, "", "point1"));
				}
			}
		}
		populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	public void updateOverlay() {
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mGeoList.get(i);
	}

	@Override
	public int size() {
		Log.i(TAG, "mGeoList.size() is " + mGeoList.size());
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
							Log.i(TAG, "mGeoList is " + mGeoList.size());
							if (list.size() > 0)
								list.remove(i);
							mapView.getOverlays().clear();
							OverItemT overitem = new OverItemT(marker,
									mContext, list);
							mapView.getOverlays().add(overitem);
							mapView.invalidate();
							Log.i(TAG, "mGeoList is " + mGeoList.size());
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
		}

		return super.onTap(i);
	}

	public void setType() {

	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView mapView) {
		this.mapView = mapView;
		Log.i(TAG, "onTap is running ----------" + PMapActivity.currentStatus);
		// 消去弹出的气泡
		PMapActivity.mPopView.setVisibility(View.GONE);

		if (PMapActivity.currentStatus == PMapActivity.STATE_CREATE) {
			mapView.getOverlays().clear();

			Projection projection = mapView.getProjection();
			Point point = projection.toPixels(arg0, null);
			GeoPoint fromPixels = projection.fromPixels(point.x, point.y);

			Avatar avatar = new Avatar();
			avatar.setLatitude(fromPixels.getLatitudeE6() / 1E6);
			avatar.setLongitude(fromPixels.getLongitudeE6() / 1E6);
			list.add(avatar);

			Log.i(TAG, "lat is " + fromPixels.getLatitudeE6() / 1E6
					+ " long is " + fromPixels.getLongitudeE6() / 1E6);

			// OverItemT overItem = new
			// OverItemT(mContext.getResources().getDrawable(R.drawable.annotation),
			// mContext, list);
			OverItemT overItem = new OverItemT(marker, mContext, list);
			mapView.getOverlays().add(overItem);
			mapView.invalidate();
		}

		Log.i(TAG,
				"lat i " + arg0.getLatitudeE6() + " lon is "
						+ arg0.getLongitudeE6());
		return super.onTap(arg0, mapView);
	}

}
