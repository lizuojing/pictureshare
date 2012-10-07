package com.android.app.view;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.app.R;
import com.android.app.utils.Utils;

public class RotateView extends RelativeLayout {

	// private static final int ID_BOTTOM = 2000;
	// private static final int ID_TOP = 3000;
	private ImageView mCenterButton;
	private ImageView mTopButton;
	private ImageView mBottomButton;

	private ImageView m1;
	private ImageView m2;

	private Context context;

	private LinkedList<Bitmap> homeItem = new LinkedList<Bitmap>();

	public RotateView(Context context) {
		super(context);
		initLayot(context);
	}

	public RotateView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initLayot(context);
	}

	public RotateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayot(context);
	}

	private void initLayot(Context context) {
		this.context = context;
		initItems();
		createLayout();
		updateView();

	}

	private void updateView() {
		mTopButton.setImageBitmap(homeItem.get(0));
		mCenterButton.setImageBitmap(homeItem.get(1));
		mBottomButton.setImageBitmap(homeItem.get(2));
		m1.setImageBitmap(homeItem.get(3));
	}

	private void initItems() {
		homeItem.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.turntable_icon_one));
		homeItem.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.turntable_icon_two));
		homeItem.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.turntable_icon_three));
		homeItem.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.turntable_icon_four));
		homeItem.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.turntable_icon_five));
		homeItem.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.turntable_icon_six));
		homeItem.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.turntable_icon_seven));
		homeItem.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.turntable_icon_eight));

	}

	public void refreshView(int orient) {
		// 0 向下 1 向上

		// this.setVisibility(View.GONE);

		mCenterButton.setVisibility(View.GONE);
		mTopButton.setVisibility(View.GONE);
		mBottomButton.setVisibility(View.GONE);
		m1.setVisibility(View.GONE);
		m2.setVisibility(View.GONE);
		if (0 == orient) {
			Bitmap last = homeItem.getLast();
			homeItem.remove(last);
			homeItem.add(0, last);
		} else {
			Bitmap first = homeItem.getFirst();
			homeItem.remove(first);
			homeItem.add(homeItem.size(), first);
		}
		//
		// mTopButton.setDrawingCacheEnabled(false);
		// mCenterButton.setDrawingCacheEnabled(false);
		// mBottomButton.setDrawingCacheEnabled(false);
		// this.refreshDrawableState();

		mTopButton.setImageBitmap(homeItem.get(0));
		mCenterButton.setImageBitmap(homeItem.get(1));
		mBottomButton.setImageBitmap(homeItem.get(2));
		m1.setImageBitmap(homeItem.get(3));
		m2.setImageBitmap(homeItem.get(4));

		// this.setVisibility(View.VISIBLE);
		mCenterButton.setVisibility(View.VISIBLE);
		mTopButton.setVisibility(View.VISIBLE);
		mBottomButton.setVisibility(View.VISIBLE);
		m1.setVisibility(View.VISIBLE);
		m2.setVisibility(View.VISIBLE);
	}

	private void createLayout() {
		// 顶部button
		RelativeLayout.LayoutParams buttonTopLP = new RelativeLayout.LayoutParams(
				Utils.dipToPixels(context, 40), Utils.dipToPixels(context, 40));
		buttonTopLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		buttonTopLP.setMargins(Utils.dipToPixels(context, 160),
				Utils.dipToPixels(context, 15), 0, 0);

		ImageView topButton = new ImageView(context);
		mTopButton = topButton;
		// topButton.setId(ID_TOP);
		addView(topButton, buttonTopLP);

		// 中间的button
		RelativeLayout.LayoutParams buttonCenterLP = new RelativeLayout.LayoutParams(
				Utils.dipToPixels(context, 40), Utils.dipToPixels(context, 40));
		buttonCenterLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		buttonCenterLP.setMargins(Utils.dipToPixels(context, 65),
				Utils.dipToPixels(context, 65), 0, 0);

		ImageView centerButton = new ImageView(context);
		mCenterButton = centerButton;
		addView(centerButton, buttonCenterLP);

		// 底部button
		RelativeLayout.LayoutParams buttonBottomLP = new RelativeLayout.LayoutParams(
				Utils.dipToPixels(context, 40), Utils.dipToPixels(context, 40));
		buttonBottomLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		buttonBottomLP.setMargins(Utils.dipToPixels(context, 17),
				Utils.dipToPixels(context, 160), 0, 0);

		ImageView bootomButton = new ImageView(context);
		mBottomButton = bootomButton;
		// bootomButton.setId(ID_BOTTOM);
		addView(bootomButton, buttonBottomLP);

		// button
		RelativeLayout.LayoutParams m1LP = new RelativeLayout.LayoutParams(
				Utils.dipToPixels(context, 40), Utils.dipToPixels(context, 40));
		m1LP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		m1LP.setMargins(Utils.dipToPixels(context, 65),
				Utils.dipToPixels(context, 260), 0, 0);

		ImageView m1Button = new ImageView(context);
		m1 = m1Button;
		// bootomButton.setId(ID_BOTTOM);
		addView(m1Button, m1LP);

		// button
		RelativeLayout.LayoutParams m2LP = new RelativeLayout.LayoutParams(
				Utils.dipToPixels(context, 40), Utils.dipToPixels(context, 40));
		m2LP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		m2LP.setMargins(Utils.dipToPixels(context, 260),
				Utils.dipToPixels(context, 65), 0, 0);

		ImageView m2Button = new ImageView(context);
		m2 = m2Button;
		// bootomButton.setId(ID_BOTTOM);
		addView(m2Button, m2LP);

	}

	/**
	 * 获取顶部按钮
	 * 
	 * @return
	 */
	public ImageView getTopButton() {
		return mTopButton;
	}

	/**
	 * 获取中间按钮
	 * 
	 * @return
	 */
	public ImageView getCenterButton() {
		return mCenterButton;
	}

	/**
	 * 获取底部按钮
	 * 
	 * @return
	 */
	public ImageView getBottomButton() {
		return mBottomButton;
	}

}
