package com.android.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.app.utils.Utils;

public class PicDialogProgress extends Dialog {

	private Context context;

	private int id;

	private PicDialogProgress(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	private PicDialogProgress(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	public PicDialogProgress(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		LinearLayout linearLayout = new LinearLayout(context);
//		linearLayout.setBackgroundResource(R.drawable.bg_toast);
		linearLayout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		ProgressBar progressBar = new ProgressBar(context);
		linearLayout.addView(progressBar,LP);
		int LPH = Utils.dipToPixels(context, 46);
		setContentView(linearLayout, new ViewGroup.LayoutParams(LPH, LPH));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
