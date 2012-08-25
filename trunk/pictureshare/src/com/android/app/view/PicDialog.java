package com.android.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.R;
import com.android.app.utils.Utils;

public class PicDialog extends Dialog implements OnClickListener {

	private Context context;

	private String title;
	private String text;
	private String okStr;
	private String cancleStr;

	private int id;

	private OnButtonClickListener buttonClickListener;

	private PicDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context, null);
	}

	private PicDialog(Context context, int theme) {
		super(context, theme);
		init(context, null);
	}

	public PicDialog(Context context, OnButtonClickListener buttonClickListener) {
		super(context);
		init(context, buttonClickListener);
	}

	private void init(Context context, OnButtonClickListener buttonClickListener) {
		this.context = context;
		this.buttonClickListener = buttonClickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.pic_dialog, null);
		TextView tvTitle = (TextView) viewGroup.findViewById(R.id.tv_title);
		TextView tvText = (TextView) viewGroup.findViewById(R.id.tv_text);
		LinearLayout divideLine = (LinearLayout) viewGroup.findViewById(R.id.divideLine);
		Button btnOk = (Button) viewGroup.findViewById(R.id.btn_ok);
		Button btnCancle = (Button) viewGroup.findViewById(R.id.btn_cancle);
		
		setContentView(viewGroup);
		
		if(!Utils.isNullOrEmpty(title)){
			tvTitle.setText(title);
			tvTitle.setVisibility(View.VISIBLE);
		}
		
		if(!Utils.isNullOrEmpty(text)){
			tvText.setText(text);
			tvText.setVisibility(View.VISIBLE);
		}
		
		if(!Utils.isNullOrEmpty(title)&&!Utils.isNullOrEmpty(text)){
			divideLine.setVisibility(View.VISIBLE);
		}
		
		if(!Utils.isNullOrEmpty(okStr)){
			btnOk.setText(okStr);
			btnOk.setVisibility(View.VISIBLE);
		}
		
		if(!Utils.isNullOrEmpty(cancleStr)){
			btnCancle.setText(cancleStr);
			btnCancle.setVisibility(View.VISIBLE);
		}
		
		btnOk.setOnClickListener(this);
		btnCancle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (buttonClickListener == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.btn_ok:
			buttonClickListener.onOkButtonClicked(this);
			break;
		case R.id.btn_cancle:
			buttonClickListener.onCancleButtonClicked(this);
			break;
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		this.title = title.toString();
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(context.getText(titleId));
	}

	public void setText(CharSequence text) {
		this.text = text.toString();
	}

	public void setText(int textId) {
		setText(context.getText(textId));
	}

	public void setOkButtonText(CharSequence okStr) {
		this.okStr=okStr.toString();
	}

	public void setOkButtonText(int okStrId) {
		setOkButtonText(context.getText(okStrId));
	}

	public String getCancleButtonText(){
		return this.cancleStr;
	}
	
	public void setCancleButtonText(CharSequence cancleStr) {
		this.cancleStr=cancleStr.toString();
	}

	public void setCancleButtonText(int cancleStrId) {
		setCancleButtonText(context.getText(cancleStrId));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public interface OnButtonClickListener {
		void onOkButtonClicked(PicDialog dialog);

		void onCancleButtonClicked(PicDialog dialog);
	}

}
