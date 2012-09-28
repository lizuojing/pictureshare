package com.android.app;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 * 设置页面
 * @author Administrator
 *
 */
public class SettingActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout personalSetting;
	private RelativeLayout valuationSetting;
	private Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		initComponents();
	}

	private void initComponents() {
		
		backButton = (Button)findViewById(R.id.button1);
		personalSetting = (RelativeLayout)findViewById(R.id.personal_setting);
		valuationSetting = (RelativeLayout)findViewById(R.id.valuation_setting);
		
		backButton.setOnClickListener(this);
		personalSetting.setOnClickListener(this);
		valuationSetting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			super.onBackPressed();
			break;
		case R.id.personal_setting:
			Toast.makeText(this, "个人中心", Toast.LENGTH_SHORT).show();
			break;
		case R.id.valuation_setting:
			Toast.makeText(this, "评价", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
	}
}
