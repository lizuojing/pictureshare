package com.android.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.R;
import com.android.app.entity.Avatar;
import com.android.app.utils.Utils;

public class MainItem extends RelativeLayout {
	

	private static final int ID_IMAGE = 2012;
	private static final int ID_TITLE = 2016;
	private ImageView mLeftImage;
	private TextView mTitle;
	private TextView mTime;
	private ImageView mRightImage;
	private LoadBitmapTask loadBitmapTask;


	public MainItem(Context context, Avatar avatar) {
		super(context);
		createLayout(context);
		setItemData(avatar);
	}
	
	private void createLayout(Context context) {
		setBackgroundColor(getResources().getColor(R.color.color_list_item_default));
		
//		RelativeLayout.LayoutParams imageLP = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams imageLP = new RelativeLayout.LayoutParams(Utils.dipToPixels(context, 100),Utils.dipToPixels(context, 100));
		imageLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		imageLP.addRule(RelativeLayout.CENTER_VERTICAL);
		imageLP.setMargins(Utils.dipToPixels(context, 2), Utils.dipToPixels(context, 2), Utils.dipToPixels(context, 2), Utils.dipToPixels(context, 2));
		ImageView image = new ImageView(context);
		image.setId(ID_IMAGE);
		mLeftImage = image;
		addView(image,imageLP);
		
		RelativeLayout.LayoutParams titleLP = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		titleLP.addRule(RelativeLayout.RIGHT_OF,ID_IMAGE);
		titleLP.setMargins(Utils.dipToPixels(context, 10), 0, 0, 0);
		TextView title = new TextView(context);
		mTitle = title;
		title.setId(ID_TITLE);
		title.setTextSize(18.f);
		title.setTextColor(getResources().getColor(R.color.color_list_item_text_default));
		addView(title,titleLP);
		
		RelativeLayout.LayoutParams timeLP = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		timeLP.addRule(RelativeLayout.RIGHT_OF,ID_IMAGE);
//		timeLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		timeLP.addRule(RelativeLayout.BELOW,ID_TITLE);
		timeLP.setMargins(Utils.dipToPixels(context, 10), 0, 0, 0);
		TextView time = new TextView(context);
		mTime = time;
		title.setTextColor(getResources().getColor(R.color.color_list_item_text_default));
		addView(time,timeLP);
		
		/*RelativeLayout.LayoutParams rightLP = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		rightLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rightLP.addRule(RelativeLayout.CENTER_VERTICAL);
		rightLP.setMargins(Utils.dipToPixels(context, 10), 0, Utils.dipToPixels(context, 10), 0);
		ImageView rightView = new ImageView(context);
		rightView.setImageResource(R.drawable.android_default);
		mRightImage = rightView;
		addView(rightView,rightLP);*/
	}

	public void setItemData(Avatar avatar) {
		mLeftImage.setImageResource(R.drawable.android_default);
		if (loadBitmapTask != null) {
			loadBitmapTask.cancel(true);
			loadBitmapTask = null;
		}
		loadBitmapTask = new LoadBitmapTask();
		if(avatar!=null){
			loadBitmapTask.execute(avatar);
		}
		
		mTitle.setText(avatar.getTitle());
		mTime.setText(Utils.formatTime(avatar.getTime()));
		
	}

	class LoadBitmapTask extends AsyncTask<Avatar, Object, Bitmap>{
		@Override
		protected Bitmap doInBackground(Avatar... params) {
			return params[0].getThumbnail();
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			mLeftImage.setImageBitmap(result);
			super.onPostExecute(result);
		}
		
	}
}
