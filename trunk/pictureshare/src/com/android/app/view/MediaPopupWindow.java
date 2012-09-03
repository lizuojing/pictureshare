package com.android.app.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.android.app.R;

public class MediaPopupWindow extends PopupWindow {

	private LayoutInflater inflater;

	private int offX = 10;
	private int offY = 10;

	private ViewGroup rootView;

	private Button takePhoto;
	private Button pickPhoto;

	public MediaPopupWindow(Context context, int backgroundRes) {
		super(context);
		this.setFocusable(true);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView = (ViewGroup) inflater.inflate(R.layout.media_popup, null);
		setContentView(rootView);
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.FILL_PARENT);
		setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.translucence)));
		setAnimationStyle(R.style.main_menu_style);
		initLayout();

		rootView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				dismiss();
				return false;
			}
		});
	}

	private void refreshLocation() {
		rootView.setPadding(rootView.getPaddingRight(), rootView.getPaddingTop(), rootView.getPaddingRight(), offY < 0 ? 0
				: offY);
	}

	public void show(View parent, int orientation, int offX, int offY) {
		this.offX = offX;
		this.offY = offY;
		refreshLocation();
		super.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}

	public void initLayout() {
		takePhoto = (Button) rootView.findViewById(R.id.btn_take_photo);
		pickPhoto = (Button) rootView.findViewById(R.id.btn_pick_photo);
	}
	
	public Button getTakePhotoButton() {
		return takePhoto;
	}

	public Button getPickPhotoButton() {
		return pickPhoto;
	}
	
	/**
	 * 如果offX 和 offY
	 * 
	 * @param newConfig
	 * @param offX
	 * @param offY
	 */
	public void onConfigurationChanged(Configuration newConfig, int offX, int offY) {
		if (offX > 0) {
			this.offX = offX;
		}
		if (offY > 0) {
			this.offY = offY;
		}
		refreshLocation();
	}


	public int getOffX() {
		return offX;
	}

	public void setOffX(int offX) {
		this.offX = offX;
	}

	public int getOffY() {
		return offY;
	}

	public void setOffY(int offY) {
		this.offY = offY;
	}
}
