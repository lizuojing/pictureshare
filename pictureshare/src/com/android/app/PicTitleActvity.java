package com.android.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.app.api.AvatarApi;
import com.android.app.api.AvatarRequestParam;
import com.android.app.data.SettingLoader;
import com.android.app.entity.Location;
import com.android.app.entity.Point;
import com.android.app.utils.ImageUtil;
import com.android.app.utils.Utils;

public class PicTitleActvity extends BaseActivity implements OnClickListener{
	private static final String TAG = "PicTitleActvity";
	private Button backButton;
	private EditText picTitle;
	private ImageView picture;
	private ImageView confirmButton;
	private String filePath;
	private Bitmap bitmap;
	private LocationManager locationManager;
	private ArrayList<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictitle);
		
		filePath = getIntent().getStringExtra("mCurrentFile");
		list = getIntent().getStringArrayListExtra("list");
		
		
		initComponents();
		updateUI();
	}

	private void updateUI() {
		bitmap = getBitmap(filePath);
		if(bitmap!=null) {
			picture.setImageBitmap(bitmap);//TODO 图片处理
		}else {
//			picture.setImageResource(R.drawable.startup);
			picture.setImageResource(R.drawable.android_default);
		}
	}

	private Bitmap getBitmap(String filePath) {
		int width=Utils.getScreenHeight(this)/4;
		int photoOrientation=0;
		try {
			ExifInterface exifInterface=new ExifInterface(filePath);
			photoOrientation=exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
			switch (photoOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_180:
				photoOrientation=180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				photoOrientation=270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				photoOrientation=90;
				break;
			default:
				photoOrientation=0;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return ImageUtil.getThumbnail(filePath,width, width,photoOrientation);
		} catch (OutOfMemoryError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	private void initComponents() {
		backButton = (Button)findViewById(R.id.button1);
		picTitle = (EditText)findViewById(R.id.pictitle);
		picture = (ImageView)findViewById(R.id.imageView1);
		confirmButton = (ImageView)findViewById(R.id.confirm);
		
		backButton.setOnClickListener(this);
		confirmButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			finish();
			break;
		case R.id.confirm:
			boolean mFromPicList = getIntent().getBooleanExtra("mFromPicList", false);
			if(mFromPicList) {
				//拷贝图片
				String savepath = new File(Utils.getCacheDir(),filePath).getAbsolutePath();
				boolean writeImage = ImageUtil.writeImage(savepath, bitmap);
				Log.i(TAG, "writeImage is " + writeImage);
			}
			String title = picTitle.getText().toString();
			//title 处理
			Intent intent = new Intent(this,PicListActivity.class);
			intent.putExtra("picTitle", title);
			intent.putExtra("mCurrentFile", filePath);
			startActivity(intent);
			
			uploadPhotoInfo();
			break;

		default:
			break;
		}
		
	}

	private void uploadPhotoInfo() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String gpsProvider = LocationManager.GPS_PROVIDER;
		String networkProvider = LocationManager.NETWORK_PROVIDER;
		Location location = getLocationInfoByGps(gpsProvider);
//		if(location==null) {
//			getCellLocation(networkProvider);
//		}
		
		String label = picTitle.getText().toString();
		if(Utils.isNullOrEmpty(label)) {
			Toast.makeText(this, "输入一个标题吧！亲。", Toast.LENGTH_SHORT).show();
			return;
		}
		
		AvatarRequestParam params = new AvatarRequestParam();
		params.setLocation(location);
		
		params.setPoints(list);
		params.setLabel(label);
		params.setEmail(SettingLoader.getRegEmail(this));
		params.setPhotoid(SettingLoader.getPhotoId(this));
//		params.setEmail("aaa@163.com");
//		params.setPhotoid("12345256");
//		params.setTipsid("23453576");//大头针
		
		AvatarApi api = new AvatarApi(this);
		api.sendAvatarInfo(1, params, 1, "25");
		
	}
	
	private Location getLocationInfoByGps(String provider) {
		Location location = null;
		double Latitude = locationManager.getLastKnownLocation(provider).getLatitude();
		double Longitude = locationManager.getLastKnownLocation(provider).getLongitude();
		if(Latitude!=0&&Longitude!=0) {
			location = new Location(Latitude+"", Longitude+"");
		}
	    return location;
	}
	
   //GSM和Cdma
	private Location getCellLocation(TelephonyManager manager) {
		 CellLocation cellLocation = manager.getCellLocation();
		 if(true){
				GsmCellLocation gsm = ((GsmCellLocation) manager
						.getCellLocation());
				// 获取基站id与地区码
				if (gsm != null) {
					int cid = gsm.getCid();
					int lac = gsm.getLac();
//					locationInfo.setCellid(cid);
//					locationInfo.setLac(lac);
				}
			} else if (cellLocation instanceof CdmaCellLocation) {
				CdmaCellLocation cdma = ((CdmaCellLocation) manager
						.getCellLocation());
				// 获取基站的经纬度
				if (cdma != null) {
//					sid = cdma.getSystemId();
//					bid = cdma.getBaseStationId();
//					nid = cdma.getNetworkId();
//					locationInfo.setBid(bid);
//					locationInfo.setNid(nid);
//					locationInfo.setSid(sid);
				}
			}
		return null;
	}
}
