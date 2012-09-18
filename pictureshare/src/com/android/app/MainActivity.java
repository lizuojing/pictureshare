package com.android.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.app.api.ApiResult;
import com.android.app.api.ApiReturnResultListener;
import com.android.app.api.AvatarApi;
import com.android.app.api.OtherApi;
import com.android.app.entity.Avatar;
import com.android.app.entity.VersionInfo;
import com.android.app.service.PicService;
import com.android.app.utils.Utils;
import com.android.app.view.MainItem;
import com.android.app.view.MediaPopupWindow;
import com.android.app.view.PicDialog;
import com.android.app.view.PicDialog.OnButtonClickListener;

/**
 * 主页面
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

	protected static final int GET_IMAGE = 2000;
	protected static final String TAG = "MainActivity";
	private static final int ID_EXIT = 2012;
	private ListView listView;
	private CheckBox editButton;
	private ImageView mapImage;
	private ImageView takePicImage;
	private ImageView setImage;
	protected File mCurrentPhotoFile;
	private EditText search_edit;
	private InputMethodManager inputMethodManager;

	private ArrayList<Avatar> list = null;
	private ListAdapter adapter;
	private MediaPopupWindow mediaPopup;
	private LoadImagesFromSDCard loadTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initComponents();
		registerButton();

	}


	private void registerShareButton() {
		if (mediaPopup!=null&&mediaPopup.getTakePhotoButton() != null) {
			mediaPopup.getTakePhotoButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mCurrentPhotoFile = new File(Utils.getCacheDir(),
							getPhotoFileName());
					Intent captureImage = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					captureImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri
							.fromFile(mCurrentPhotoFile));

					startActivityForResult(captureImage,
							Activity.DEFAULT_KEYS_DIALER);
				}
			});
		}

		if (mediaPopup!=null&&mediaPopup.getPickPhotoButton() != null) {
			mediaPopup.getPickPhotoButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent getImage = new Intent(Intent.ACTION_GET_CONTENT); 
                    getImage.addCategory(Intent.CATEGORY_OPENABLE);
                    //getImage.setType("image/jpeg"); 
                    getImage.setType("image/*"); 
                    getImage.putExtra("crop", "true");
                    
                    getImage.putExtra("aspectX", 1); 
                    getImage.putExtra("aspectY", 1);
                    getImage.putExtra("outputX", 320);
                    getImage.putExtra("outputY", 320);
                    getImage.putExtra("scale",           true);
                    getImage.putExtra("noFaceDetection", true);
                    getImage.putExtra("setWallpaper",    false);
                    getImage.putExtra("return-data", true);

                    startActivityForResult(Intent.createChooser(getImage, getResources().getString(R.string.pick_image)), GET_IMAGE);

				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (GET_IMAGE == requestCode) {
				if (data != null) {
					final Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap bitmap = extras.getParcelable("data");
						String path = (String) extras.get("filePath");
						if (bitmap == null && path != null) {
							bitmap = BitmapFactory.decodeFile(path);
						} else if (bitmap == null && path == null) {

						}
						String filepath = new File(Utils.getCacheDir(),getPhotoFileName()).getAbsolutePath();
						
						boolean writeResult = writeImage(filepath, bitmap);
						
						if (writeResult)
						{
							Intent intent = new Intent(this, RectActiivity.class);
							intent.putExtra("mCurrentFile", filepath);
							startActivity(intent);
							return;
						}
					}

				}

			} else {
				Intent intent = new Intent();
				if (mCurrentPhotoFile != null) {
					intent.putExtra("mCurrentFile", mCurrentPhotoFile
							.getAbsolutePath());
				}
				intent.setClass(this, RectActiivity.class);
				startActivity(intent);
			}
		}

	}

	
	private boolean writeImage(String path, Bitmap bitmap)
	{
		boolean result = false;
		
        try 
        { // catches IOException below 
        	if(!Utils.isNullOrEmpty(path)&&bitmap!=null) {
                File f = new File(path); 
                FileOutputStream osf = new FileOutputStream(f);
                result = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, osf);
                osf.flush();
        	}
        } 
        catch (IOException ioe) 
        {
        	Log.e(TAG,ioe.toString());
        	result = false;
        }
        
        return result;
    }
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMddHHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	private void registerButton() {
		editButton.setOnClickListener(this);
		mapImage.setOnClickListener(this);
		takePicImage.setOnClickListener(this);
		setImage.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(editButton.isChecked()) {
					Intent intent = new Intent(MainActivity.this,RectActiivity.class);
					Avatar avatar = list.get(arg2);
					if(Utils.isNotNullOrEmpty(avatar.getPath())) {
						intent.putExtra("mCurrentFile",avatar.getPath());
						startActivity(intent);
					}else {
						Toast.makeText(MainActivity.this, "没有可编辑的图片哦！", Toast.LENGTH_SHORT).show();
					}
				}else {
					Intent intent = new Intent(MainActivity.this,PicListActivity.class);
					startActivity(intent);
				}
				
//				File directory = Environment.getExternalStorageDirectory();
//				String filepath = directory.getAbsoluteFile()+"/Camera/683kb.jpg";
//				uploadpicture(filepath);
			}
		});

	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		PicDialog dialog = null;
		switch (id) {
		case ID_EXIT:
			dialog = new PicDialog(this, new OnButtonClickListener() {
				@Override
				public void onOkButtonClicked(PicDialog dialog) {
					PicService.promptExitApp(MainActivity.this);
					dialog.cancel();
				}

				@Override
				public void onCancleButtonClicked(PicDialog dialog) {
					dialog.cancel();
				}
			});
			dialog.setTitle("确定退出吗？");
			dialog.setOkButtonText("确定");
			dialog.setCancleButtonText("取消");
			return dialog;
		default:
			break;
		}
		return dialog;
	}

	private void initComponents() {
		editButton = (CheckBox) findViewById(R.id.button1);
		mapImage = (ImageView) findViewById(R.id.imageView2);
		takePicImage = (ImageView) findViewById(R.id.imageView3);
		setImage = (ImageView) findViewById(R.id.imageView4);

		search_edit = (EditText) findViewById(R.id.editText1);
		inputMethodManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		search_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				search_edit.setFocusable(true);
				search_edit.setFocusableInTouchMode(true);
				search_edit.requestFocus();
				inputMethodManager.showSoftInput(search_edit, 0);
			}
		});

		listView = (ListView) findViewById(R.id.listView1);
		if (list == null) {
			list = new ArrayList<Avatar>();
		}
		adapter = new ListAdapter();
		listView.setAdapter(adapter);

		loadPicData(this);
		// 第一次加载
	}


	public void loadPicData(Context context) {
		loadTask = new LoadImagesFromSDCard();
		loadTask.execute();
	}


	class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Avatar avatar = list.get(position);
			MainItem item = null;
			if (convertView == null) {
				item = new MainItem(MainActivity.this, avatar);
				convertView = item;
			} else {
				item = (MainItem) convertView;
				item.setItemData(avatar);
			}

			return convertView;
		}

	}
	
	/**
	 * Async task for loading the images from the SD card.
	 * 有关具体的缩略图可以通过
	 * getThumbnail(ContentResolver cr, long origId, int kind, BitmapFactory.Options options)或
	 * getThumbnail(ContentResolver cr, long origId, long groupId, int kind, BitmapFactory.Options options) 
	 * 方法获取，这两种方法返回Bitmap类型，而缩略图的分辨率可以从HEIGHT和WIDTH两个字段提取，
	 * 在Android上缩略图分为两种，通过读取KIND字段来获得，分别为MICRO_KIND和MINI_KIND 分别为微型和迷你两种缩略模式，
	 * 前者的分辨率更低。
	 * @author Mihai Fonoage
	 */
	class LoadImagesFromSDCard extends AsyncTask<Object, Avatar, Object> {

		/**
		 * Load images from SD Card in the background, and display each image on
		 * the screen.
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Object doInBackground(Object... params) {
			// setProgressBarIndeterminateVisibility(true);
			
			String selection = Media.DATA + " like  " + "\'%/sdcard/dcim/%\'" + " or " + Media.DATA + " like  "
					+ "\'%/sdcard/Pictures/%\'" + " or " + Media.DATA + " like  " + "\'%/sdcard/Images/%\'" + " or "
					+ Media.DATA + " like  " + "\'%/sdcard/Camera/%\'" + " or " + Media.DATA + " like  "
					+ "\'%/sdcard/Image/%\'" + " or " + Media.DATA + " like  " + "\'%/sdcard/Picture/%\'" + " or "
					+ Media.DATA + " like  " + "\'%/sdcard/Photo/%\'" + " or " + Media.DATA + " like  "
					+ "\'%/sdcard/Photos/%\'" + " or " + Media.DATA + " like  "
					+ "\'%/sdcard/%MEDIA/%\'" ;
			String[] projection = {Media._ID,Media.DATA,Media.ORIENTATION};
			Cursor cursor = getApplicationContext().getContentResolver()
					.query(Media.EXTERNAL_CONTENT_URI, projection, selection, null, "_id" + " DESC");
			if(cursor==null){
				return null;
			}
//			int idIndex=cursor.getColumnIndexOrThrow(Media._ID);
			int dataIndex=cursor.getColumnIndexOrThrow(Media.DATA);
			int orientationIndex=cursor.getColumnIndexOrThrow(Media.ORIENTATION);
			int count=cursor.getCount();
			if (count > 0) {
				for(int i=0;i<count;i++){
					cursor.moveToPosition(i);
					Avatar loadedImage = new Avatar();
					loadedImage.setTitle("我的工作证");
					loadedImage.setTime(System.currentTimeMillis());
					loadedImage.setPath(cursor.getString(dataIndex));
					loadedImage.orientation=cursor.getInt(orientationIndex);
					File file=new File(loadedImage.getPath());
					if(file!=null&&file.exists()&&isImagePath(loadedImage.getPath())){
						list.add(loadedImage);
					}
				}
			}
			cursor.close();
			return null;
		}

		private boolean isImagePath(String photoPath) {
			if(Utils.isNullOrEmpty(photoPath)){
				return false;
			}
			if(photoPath.toLowerCase().endsWith(".jpg")||photoPath.toLowerCase().endsWith(".jpeg")||photoPath.toLowerCase().endsWith(".png")
					||photoPath.toLowerCase().endsWith(".bmp")||photoPath.toLowerCase().endsWith(".svg")||photoPath.toLowerCase().endsWith(".gif")){
				return true;
			}
			return false;
		}

		/**
		 * Set the visibility of the progress bar to false.
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Object result) {
			adapter.notifyDataSetChanged();
			loadTask = null;
		}
	}
	
	@Override
	protected void onResume() {
		if(mediaPopup!=null) {
			mediaPopup.dismiss(); 
		}
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		Log.i(TAG, "onBackPressed is running");
		if(mediaPopup!=null) {
			mediaPopup.dismiss(); 
		}
		showDialog(ID_EXIT);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView2:
			Intent mapIntent = new Intent(this, PMapActivity.class);
			startActivity(mapIntent);
			break;
		case R.id.imageView3:
			showSharePopup();
			break;
		case R.id.imageView4:
			Intent settingIntent = new Intent(this, SettingActivity.class);
			startActivity(settingIntent);
			break;

		default:
			break;
		}

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
		avatarApi.uploadAvatar(MainActivity.this,0,filepath);
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
		otherApi.getLatestAppVersion(0);
	}

	private void showSharePopup() {
		if (mediaPopup == null) {
			mediaPopup = new MediaPopupWindow(this, R.drawable.bg_share_popup_1);
		}
		int[] btnShareLoc = new int[2];
		takePicImage.getLocationInWindow(btnShareLoc);
		int offY = Utils.getScreenHeight(this) - btnShareLoc[1] + Utils.dipToPixels(this, 5);
		int orientation = getResources().getConfiguration().orientation;
		mediaPopup.show(takePicImage, orientation, 0, offY);
		registerShareButton();
	}
}
