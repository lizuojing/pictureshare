package com.android.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.android.app.entity.Avatar;
import com.android.app.image.ImageLoaderManager;
import com.android.app.service.PicService;
import com.android.app.utils.Utils;
import com.android.app.view.MainItem;

/**
 * 主页面
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends BaseActvity implements View.OnClickListener {

	protected static final int GET_IMAGE = 2000;
	protected static final String TAG = "MainActivity";
	private ListView listView;
	private Button editButton;
	private ImageView mapImage;
	private ImageView takePicImage;
	private ImageView setImage;
	private LinearLayout tab_share_layout;
	private LinearLayout tab_share_mask;
	private Button takePhoto;
	private Button sharePhoto;
	private LinearLayout tab_share_content;
	protected File mCurrentPhotoFile;
	private EditText search_edit;
	private InputMethodManager inputMethodManager;

	private ArrayList<Avatar> list = null;
	private Cursor photoCursor;
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initComponents();
		initShareTab();
		registerButton();

	}

	private void initShareTab() {
		tab_share_layout = (LinearLayout) findViewById(R.id.tab_share_layout);
		tab_share_mask = (LinearLayout) findViewById(R.id.tab_share_mask);
		tab_share_content = (LinearLayout) findViewById(R.id.tab_share_content);
		tab_share_layout.getBackground().setAlpha(100);

		// LinearLayout topLayout=new LinearLayout(this);
		// LinearLayout.LayoutParams topLayoutLP= new
		// LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,Utils.dipToPixels(this,
		// 3));
		// topLayout.setBackgroundColor(0xffff4000);
		// tab_share_mask.addView(topLayout,0,topLayoutLP);

		LinearLayout.LayoutParams takePhotoLP = new LinearLayout.LayoutParams(
				Utils.dipToPixels(this, 200), LayoutParams.WRAP_CONTENT);
		takePhotoLP.gravity = Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL;
		takePhotoLP.setMargins(Utils.dipToPixels(this, 20), Utils.dipToPixels(
				this, 15), Utils.dipToPixels(this, 20), 0);
		takePhoto = new Button(this);
		takePhoto.setText(getResources().getString(R.string.take_photo));

		LinearLayout.LayoutParams sharePhotoLP = new LinearLayout.LayoutParams(
				Utils.dipToPixels(this, 200), LayoutParams.WRAP_CONTENT);
		sharePhotoLP.gravity = Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL;
		sharePhotoLP.setMargins(Utils.dipToPixels(this, 20), 0, Utils
				.dipToPixels(this, 20), Utils.dipToPixels(this, 15));
		sharePhoto = new Button(this);
		sharePhoto.setText(getResources().getString(R.string.pick_photo));

		tab_share_content.addView(takePhoto, takePhotoLP);
		tab_share_content.addView(sharePhoto, sharePhotoLP);
		registerShareButton();
	}

	private void showShare() {
		float shareAnimationY = Utils.getScreenHeight(this) / 3;
		boolean b = tab_share_layout.getVisibility() == View.VISIBLE;
		tab_share_layout.setVisibility(View.VISIBLE);
		TranslateAnimation shareAnimation = new TranslateAnimation(0, 0,
				shareAnimationY, 0);
		shareAnimation.setDuration(400);
		if (!b) {
			tab_share_mask.startAnimation(shareAnimation);
		}
	}

	private void hideShare() {
		float shareAnimationY = Utils.getScreenHeight(this) / 3;
		TranslateAnimation hideShareAnimation = new TranslateAnimation(0, 0, 0,
				shareAnimationY);
		hideShareAnimation.setDuration(400);
		hideShareAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				tab_share_layout.setVisibility(View.GONE);
			}
		});
		tab_share_mask.startAnimation(hideShareAnimation);
	}

	private void registerShareButton() {
		if (takePhoto != null) {
			takePhoto.setOnClickListener(new OnClickListener() {

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

		if (sharePhoto != null) {
			sharePhoto.setOnClickListener(new OnClickListener() {

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
				Toast.makeText(MainActivity.this, "positon is " + arg2,
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,
						PicListActivity.class);
				startActivity(intent);

			}
		});

	}

	private void initComponents() {
		editButton = (Button) findViewById(R.id.button1);
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

		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 滚动到最顶端
				Log.i("LazyScroll", "Scroll to top");
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// 滚动到最低端
			}
		});

		if (list == null) {
			list = new ArrayList<Avatar>();
		}
		adapter = new ListAdapter();
		listView.setAdapter(adapter);

		loadPicData(this);
		// 第一次加载
//		AddItemToContainer(current_page, page_count);
	}


	public void loadPicData(Context context) {

		String[] projection = new String[] { Media._ID, Media.MIME_TYPE,
				Media.TITLE, Media.ORIENTATION, Media.LONGITUDE,
				Media.LATITUDE, Media.DISPLAY_NAME, Media.DATE_TAKEN,
				Media.DATA, Media.SIZE };
		// 4.0.4系统，手机闪存是mnt/sdcard（原本是sd卡的路径），sd卡变成了mnt/external1
		String selection = Media.DATA + " LIKE  " + "\'%/sdcard%/DCIM/%\'"
				+ " OR " + Media.DATA + " LIKE  " + "\'%/sdcard%/Pictures/%\'"
				+ " OR " + Media.DATA + " LIKE  " + "\'%/sdcard%/Images/%\'"
				+ " OR " + Media.DATA + " LIKE  " + "\'%/sdcard%/Camera/%\'"
				+ " OR " + Media.DATA + " LIKE  " + "\'%mnt/external%/DCIM/%\'"
				+ " OR " + Media.DATA + " LIKE  "
				+ "\'%mnt/external%/Pictures/%\'" + " OR " + Media.DATA
				+ " LIKE  " + "\'%mnt/external%/Images/%\'" + " OR "
				+ Media.DATA + " LIKE  " + "\'%mnt/external%/Camera/%\'";
		photoCursor = context.getContentResolver().query(
				Media.EXTERNAL_CONTENT_URI, projection, selection, null,
				"_id" + " ASC");
		// int idIndex = photoCursor.getColumnIndexOrThrow(Media._ID);
		// int titleIndex = photoCursor.getColumnIndexOrThrow(Media.TITLE);
		// int orientationIndex =
		// photoCursor.getColumnIndexOrThrow(Media.ORIENTATION);
		// int longitudeIndex =
		// photoCursor.getColumnIndexOrThrow(Media.LONGITUDE);
		// int latitudeIndex =
		// photoCursor.getColumnIndexOrThrow(Media.LATITUDE);
		// int nameIndex =
		// photoCursor.getColumnIndexOrThrow(Media.DISPLAY_NAME);
		
		int dataIndex = photoCursor.getColumnIndexOrThrow(Media.DATA); // filepath
		// int datetokenIndex =
		// photoCursor.getColumnIndexOrThrow(Media.DATE_TAKEN);
		// int mimetypeIndex =
		// photoCursor.getColumnIndexOrThrow(Media.MIME_TYPE);
		// int sizeIndex = photoCursor.getColumnIndexOrThrow(Media.SIZE);

		Avatar avatar = null;

		for (photoCursor.moveToFirst(); !photoCursor.isAfterLast(); photoCursor.moveToNext()) {
			avatar = new Avatar();
			avatar.setPath(photoCursor.getString(dataIndex));
			avatar.setTitle("我的工作证");
			avatar.setTime(System.currentTimeMillis());
			list.add(avatar);
			photoCursor.moveToNext();
		}
		        
		 if( photoCursor != null ) {
			 photoCursor.close();
			 photoCursor= null;
		 }

	}


	class ListAdapter extends BaseAdapter {

		private ImageLoaderManager imageLoaderManager;

		public ListAdapter() {
			imageLoaderManager = new ImageLoaderManager(MainActivity.this,new Handler(), this);
		}

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
				item = new MainItem(MainActivity.this, avatar,imageLoaderManager);
				convertView = item;
			} else {
				item = (MainItem) convertView;
				item.setItemData(avatar);
			}

			return convertView;
		}

	}
	
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		return super.onCreateDialog(id);
//	}
	
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("确定退出吗？").setPositiveButton("确定", new  DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				PicService.promptExitApp(MainActivity.this);
				dialog.dismiss();
			}
		}).setNegativeButton("取消", new  DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
//		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent editIntent = new Intent(this, RectActiivity.class);
			startActivity(editIntent);
			break;
		case R.id.imageView2:
			Intent mapIntent = new Intent(this, PMapActivity.class);
			startActivity(mapIntent);
			break;
		case R.id.imageView3:
			// Intent picIntent = new Intent(this,PicTakeActivity.class);
			// startActivity(picIntent);
			if (tab_share_layout.getVisibility() != View.VISIBLE) {
				showShare();
			} else {
				hideShare();
			}

			break;
		case R.id.imageView4:
			Intent settingIntent = new Intent(this, SettingActivity.class);
			startActivity(settingIntent);
			break;

		default:
			break;
		}

	}
}
