package com.android.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
/**
 * 设置页面
 * @author Administrator
 *
 */
public class SettingActivity extends Activity implements OnClickListener{
	private RelativeLayout svrSetting;
	private RelativeLayout personalSetting;
	private RelativeLayout picSetting;
	private RelativeLayout valuationSetting;
	private RelativeLayout commonSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		initComponents();
	}

	private void initComponents() {
		svrSetting = (RelativeLayout)findViewById(R.id.svr_setting);
		personalSetting = (RelativeLayout)findViewById(R.id.personal_setting);
		picSetting = (RelativeLayout)findViewById(R.id.pic_setting);
		valuationSetting = (RelativeLayout)findViewById(R.id.valuation_setting);
		commonSetting = (RelativeLayout)findViewById(R.id.common_setting);
		
		svrSetting.setOnClickListener(this);
		personalSetting.setOnClickListener(this);
		picSetting.setOnClickListener(this);
		valuationSetting.setOnClickListener(this);
		commonSetting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.svr_setting:
			Toast.makeText(this, "服务器设置", Toast.LENGTH_SHORT).show();
			break;
		case R.id.personal_setting:
			Toast.makeText(this, "个人中心", Toast.LENGTH_SHORT).show();
			break;
		case R.id.pic_setting:
			Toast.makeText(this, "图片设置", Toast.LENGTH_SHORT).show();
			break;
		case R.id.valuation_setting:
			Toast.makeText(this, "评价", Toast.LENGTH_SHORT).show();
			break;
		case R.id.common_setting:
			Toast.makeText(this, "通用", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
	}
}
