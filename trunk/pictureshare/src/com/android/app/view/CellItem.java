package com.android.app.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.R;
import com.android.app.entity.Avatar;
import com.android.app.entity.ImageItem;
import com.android.app.image.ImageLoaderManager;
import com.android.app.utils.Utils;

public class CellItem extends RelativeLayout {

	private static final int ID_IMAGE = 2012;
	private static final int ID_TITLE = 2016;
	private static final String TAG = "CellItem";
	private ImageView mLeftImage;
	private TextView mTitle;
	private TextView mTime;
	private ImageLoaderManager imageLoaderManager;

	public CellItem(Context context, Avatar avatar,
			ImageLoaderManager imageLoaderManager) {
		super(context);
		this.imageLoaderManager = imageLoaderManager;
		createLayout(context);
		setItemData(avatar);
	}

	private void createLayout(Context context) {
		setBackgroundColor(getResources().getColor(
				R.color.color_list_item_default));

		// RelativeLayout.LayoutParams imageLP = new
		// RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams imageLP = new RelativeLayout.LayoutParams(
				Utils.dipToPixels(context, 100),
				Utils.dipToPixels(context, 100));
		imageLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		imageLP.addRule(RelativeLayout.CENTER_VERTICAL);
		imageLP.setMargins(Utils.dipToPixels(context, 2),
				Utils.dipToPixels(context, 2), Utils.dipToPixels(context, 2),
				Utils.dipToPixels(context, 2));
		ImageView image = new ImageView(context);
		image.setId(ID_IMAGE);
		mLeftImage = image;
		addView(image, imageLP);

		RelativeLayout.LayoutParams titleLP = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titleLP.addRule(RelativeLayout.RIGHT_OF, ID_IMAGE);
		titleLP.setMargins(Utils.dipToPixels(context, 10), 0, 0, 0);
		TextView title = new TextView(context);
		mTitle = title;
		title.setId(ID_TITLE);
		title.setTextSize(20.f);
		title.setTextColor(getResources().getColor(
				R.color.color_list_item_text_default));
		addView(title, titleLP);

		RelativeLayout.LayoutParams timeLP = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		timeLP.addRule(RelativeLayout.RIGHT_OF, ID_IMAGE);
		timeLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		timeLP.addRule(RelativeLayout.BELOW, ID_TITLE);
		timeLP.setMargins(Utils.dipToPixels(context, 10), 0, 0, 0);
		TextView time = new TextView(context);
		mTime = time;
		time.setTextSize(18.f);
		title.setTextColor(getResources().getColor(
				R.color.color_list_item_text_default));
		addView(time, timeLP);
	}

	public void setItemData(Avatar avatar) {
		if(avatar!=null) {
			ImageItem imageItem = avatar.getImageItem();
			if(imageItem!=null) {
				String imageUrl = imageItem.getImageUrl();
				Log.e(TAG, "imageUrl is " + imageUrl);
				if (!Utils.isNullOrEmpty(imageUrl)) {
					mLeftImage.setImageBitmap(imageLoaderManager.getImage(
							imageUrl, R.drawable.android_default));
				} else {
					mLeftImage.setImageResource(R.drawable.android_default);
				}
				mTitle.setText(imageItem.getTag()!=null?imageItem.getTag().getTags()+imageItem.getTag().getOwners():"未命名");
				mTime.setText(imageItem.getTag()!=null?imageItem.getTag().getTime():"位置时间");
			}
		}
		
//		if (!Utils.isNullOrEmpty(avatar.getPath())) {
//			mLeftImage.setImageBitmap(imageLoaderManager.getImage(
//					avatar.getPath(), R.drawable.android_default));
//		} else {
//			mLeftImage.setImageResource(R.drawable.android_default);
//		}
//
//		mTitle.setText(avatar.getTitle());
//		mTime.setText(Utils.formatTime(avatar.getTime()));

	}

}
