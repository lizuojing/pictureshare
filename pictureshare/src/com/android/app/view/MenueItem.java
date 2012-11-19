package com.android.app.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.R;
import com.android.app.entity.Block;
import com.android.app.image.ImageLoaderManager;

public class MenueItem extends RelativeLayout {

	private static final int ID_IMAGE = 2012;
	private static final int ID_TITLE = 2016;
	private static final String TAG = "CellItem";
	private ImageView mLeftImage;
	private TextView mTitle;
	private TextView mTime;
	private ImageLoaderManager imageLoaderManager;

	public MenueItem(Context context, Block block,
			ImageLoaderManager imageLoaderManager) {
		super(context);
		this.imageLoaderManager = imageLoaderManager;
		createLayout(context);
		setItemData(block);
	}

	private void createLayout(Context context) {
		setBackgroundColor(getResources().getColor(
				R.color.color_list_item_default));
		RelativeLayout.LayoutParams titleLP = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		// titleLP.addRule(RelativeLayout.RIGHT_OF, ID_IMAGE);

		// titleLP.setMargins(Utils.dipToPixels(context, 10), 0, 0, 0);
		TextView title = new TextView(context);
		mTitle = title;
		title.setId(ID_TITLE);
		title.setTextSize(20);
		title.setTextColor(getResources().getColor(R.color.white));
		title.setBackgroundResource(R.drawable.backitem);
		addView(title, titleLP);

	}

	public void setItemData(Block block) {
		if (block != null) {
			String title = block.getTitle();
			if (title != null) {
				mTitle.setText(title != null ? title : "未命名");
			}
		}

	}

}
