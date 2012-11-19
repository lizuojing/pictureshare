package com.android.app.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.utils.Utils;

public class TabWidget extends LinearLayout implements Checkable {
	private Context context;
	private TextView tabTitle;
	private ImageView tabIcon;
	private int[] iconRes;
	private String title;
	
	private boolean checked=false;

	public TabWidget(Context context, int[] iconRes, String title) {
		super(context);
		this.context = context;
		this.iconRes = iconRes;
		this.title = title;
		init();
	}

	private void init() {
		this.setGravity(Gravity.CENTER);
		this.setOrientation(HORIZONTAL);
		this.setFocusable(true);
		this.setClickable(true);
		this.setPadding(Utils.dipToPixels(context, 10), 0, Utils.dipToPixels(context, 10), 0);
		tabIcon = new ImageView(context);
		if(iconRes.length>0){
			tabIcon.setBackgroundResource(iconRes[0]);
		}
		LinearLayout.LayoutParams iconLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		iconLP.setMargins(0, 0, Utils.dipToPixels(context, 6), 0);
		iconLP.gravity=Gravity.CENTER;
		
		tabTitle = new TextView(context);
		tabTitle.setGravity(Gravity.CENTER);
		tabTitle.setShadowLayer(0.1f, 0.0f, 2.0f, Color.WHITE);
		tabTitle.setText(title);
		
		this.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				setChecked(hasFocus);
			}
		});
		
		this.addView(tabIcon, iconLP);
		this.addView(tabTitle);

	}

	@Override
	public void setChecked(boolean checked) {
		if(this.checked!=checked){
			tabIcon.setBackgroundResource(iconRes.length>0&&iconRes.length<2?iconRes[0]:checked?iconRes[1]:iconRes[0]);
			this.checked=checked;
		}
	}

	@Override
	public boolean isChecked() {
		return checked;
	}

	@Override
	public void toggle() {
		setChecked(!checked);
	}

}
