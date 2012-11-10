package com.android.app;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.app.api.ApiResult;
import com.android.app.api.ApiReturnResultListener;
import com.android.app.api.OwnerRequestParam;
import com.android.app.api.UserApi;
import com.android.app.entity.User;
import com.android.app.utils.Utils;
import com.android.app.view.PicDialogProgress;
import com.android.app.view.TabContent;

public class RegAndLoginActivity extends BaseActivity implements
		OnFocusChangeListener, OnClickListener, OnEditorActionListener,
		OnCheckedChangeListener {

	private static final int ID_DIALOG_PROGRESS = 1;
	private static final int REQ_CODE_REG = 1;
	private static final int REQ_CODE_LOGIN = 2;
	public static final String INTENT_TO_PAGE = "intent_to_page";
	public static final String INTENT_PAGE_REG = "intent_page_reg";
	public static final String INTENT_PAGE_LOGIN = "intent_page_login";

	private TextView ivTitle;
	private TabContent tabContent;

	private EditText editLoginUserName;
	private EditText editLoginPassword;
	private EditText editRegUserName;
	private EditText editRegPassword;
	private EditText editRegEmail;

	private Button btnGoReg;
	private Button btnReg;
	private Button btnLogin;

	private int currentPageIndex;
	private UserApi userApi;

	private String regUsername;
	private String regPassword;

	private String intentToPage;
	private boolean firstIntoActivity;
	private PicDialogProgress progressDialog;
	public User user;
	private RadioButton rb_man, rb_woman;
	private int sexflag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_and_login);
		initLayout();
		userApi = new UserApi(this);
		userApi.setReturnResultListener(apiReturnResultListener);
		intentToPage = getIntent().getStringExtra(INTENT_TO_PAGE);
		firstIntoActivity = true;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (firstIntoActivity) {
			firstIntoActivity = false;
			if (INTENT_PAGE_REG.equalsIgnoreCase(intentToPage)) {
				goToRegPage();
			} else {
				goToLoginPage();
			}
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				tabContent.scrollToPage(currentPageIndex);
			}
		}, 100);
	}

	private void initLayout() {
		ivTitle = (TextView) findViewById(R.id.reg_login_title);
		tabContent = (TabContent) findViewById(R.id.content);
		tabContent.setSlideEnabled(false);

		btnGoReg = (Button) findViewById(R.id.btn_go_reg);
		btnReg = (Button) findViewById(R.id.btn_reg);
		btnLogin = (Button) findViewById(R.id.btn_login);
		rb_man = (RadioButton) findViewById(R.id.edit_sex_man);
		rb_woman = (RadioButton) findViewById(R.id.edit_sex_woman);
		editLoginUserName = (EditText) findViewById(R.id.edit_login_username);
		editLoginPassword = (EditText) findViewById(R.id.edit_login_password);
		editRegUserName = (EditText) findViewById(R.id.edit_reg_username);
		editRegPassword = (EditText) findViewById(R.id.edit_reg_password);
		editRegEmail = (EditText) findViewById(R.id.edit_reg_email);
		editRegUserName.setHintTextColor(0xFFcccccc);
		editRegPassword.setHintTextColor(0xFFcccccc);
		editRegEmail.setHintTextColor(0xFFcccccc);

		editLoginUserName.setOnFocusChangeListener(this);
		editLoginPassword.setOnFocusChangeListener(this);
		editRegUserName.setOnFocusChangeListener(this);
		editRegPassword.setOnFocusChangeListener(this);
		editRegEmail.setOnFocusChangeListener(this);
		editLoginPassword.setOnEditorActionListener(this);
		editRegEmail.setOnEditorActionListener(this);

		btnGoReg.setOnClickListener(this);
		btnReg.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		rb_man.setOnCheckedChangeListener(this);
		rb_woman.setOnCheckedChangeListener(this);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case ID_DIALOG_PROGRESS:
			// 进度条
			progressDialog = new PicDialogProgress(this);
			return progressDialog;
		}

		return super.onCreateDialog(id);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
	}

	ApiReturnResultListener apiReturnResultListener = new ApiReturnResultListener() {

		@Override
		public <T> void onReturnSucceedResult(int requestCode,
				ApiResult<T> apiResult) {
			if (progressDialog != null) {
				progressDialog.cancel();
			}
			if (requestCode == REQ_CODE_LOGIN) {
				PicApp.getApp(RegAndLoginActivity.this).getService()
						.registerHeartBeatCheckAlarm();
				Toast.makeText(RegAndLoginActivity.this, "登录成功",
						Toast.LENGTH_SHORT).show();
				finish();
			} else if (requestCode == REQ_CODE_REG) {
				Toast.makeText(RegAndLoginActivity.this, "注册成功",
						Toast.LENGTH_SHORT).show();
				OwnerRequestParam params = new OwnerRequestParam();
				params.setUsername(regUsername.toLowerCase());
				params.setPassword(regPassword);
				userApi.login(REQ_CODE_LOGIN, getUser());
				showDialog(ID_DIALOG_PROGRESS);
			}
		}

		@Override
		public <T> void onReturnFailResult(int requestCode,
				ApiResult<T> apiResult) {
			if (progressDialog != null) {
				progressDialog.cancel();
			}
			if (requestCode == REQ_CODE_LOGIN) {
				Toast.makeText(RegAndLoginActivity.this, "登录失败",
						Toast.LENGTH_SHORT).show();
			} else if (requestCode == REQ_CODE_REG) {
				Toast.makeText(RegAndLoginActivity.this, "注册失败",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_go_reg:
			goToRegPage();
			break;
		case R.id.btn_reg:
			regUsername = editRegUserName.getText().toString().trim();
			regPassword = editRegPassword.getText().toString().trim();
			String email = editRegEmail.getText().toString().trim();
			if (validateRegUserName(regUsername)
					&& validatePassword(regPassword) && validateEmail(email)) {
				User user = new User();
				user.setEmail(email);
				user.setUsername(regUsername);
				user.setPassword(regPassword);
				userApi.regeditUser(REQ_CODE_REG, user);
				showDialog(ID_DIALOG_PROGRESS);
			}
			break;
		case R.id.btn_login:
			String loginUsername = editLoginUserName.getText().toString()
					.trim();
			String loginPassword = editLoginPassword.getText().toString()
					.trim();

			if (user == null) {
				user = new User();
			}
			user.setEmail(loginUsername);
			user.setPassword(loginPassword);
			if (validateLoginUserName(loginUsername)
					&& validatePassword(loginPassword)) {
				userApi.login(REQ_CODE_LOGIN, getUser());
				showDialog(ID_DIALOG_PROGRESS);
			}
			break;
		}
	}

	public User getUser() {

		return user;
	}

	private boolean validateEmail(String email) {
		if (Utils.isNullOrEmpty(email)) {
			Toast.makeText(this, "您还没有输入联系邮箱", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!Utils.isEmail(email)) {
			Toast.makeText(this, "您请输入的邮箱格式不正确", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private boolean validatePassword(String password) {
		if (Utils.isNullOrEmpty(password)) {
			Toast.makeText(this, "你还没有输入密码", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!Utils.isPassword(password)) {
			Toast.makeText(this, "密码为6–16位字符", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private boolean validateRegUserName(String username) {
		if (Utils.isNullOrEmpty(username)) {
			Toast.makeText(this, "您还没有输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!Utils.isEmail(username)) {
			Toast.makeText(this, "用户名为5–20位字母/数字/下划线或组合", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private boolean validateLoginUserName(String username) {
		if (Utils.isNullOrEmpty(username)) {
			Toast.makeText(this, "您还没有输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!Utils.isEmail(username)) {
			Toast.makeText(this, "用户名为5–20位字母/数字/下划线或组合", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private void goToRegPage() {
		ivTitle.setText(getResources().getString(R.string.reg_title));
		tabContent.scrollToPage(1);
		currentPageIndex = 1;
		editRegUserName.setText(editLoginUserName.getText());
		editRegPassword.setText(editLoginPassword.getText());
	}

	private void goToLoginPage() {
		ivTitle.setText(getResources().getString(R.string.login_title));
		tabContent.scrollToPage(0);
		currentPageIndex = 0;
	}

	@Override
	public void onBackPressed() {
		if (!INTENT_PAGE_REG.equalsIgnoreCase(intentToPage)
				&& currentPageIndex == 1) {
			goToLoginPage();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (v.getId()) {
		case R.id.edit_login_password:
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				String loginUsername = editLoginUserName.getText().toString()
						.trim();
				String loginPassword = editLoginPassword.getText().toString()
						.trim();
				if (validateLoginUserName(loginUsername)
						&& validatePassword(loginPassword)) {
					// userApi.login(REQ_CODE_LOGIN, loginUsername.toLowerCase()
					// + "@gozap.com", loginPassword);
					showDialog(ID_DIALOG_PROGRESS);
				}
			}
			break;
		case R.id.edit_reg_email:
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				regUsername = editRegUserName.getText().toString().trim();
				regPassword = editRegPassword.getText().toString().trim();
				String email = editRegEmail.getText().toString().trim();
				if (validateRegUserName(regUsername)
						&& validatePassword(regPassword)
						&& validateEmail(email)) {
					// userApi.register(REQ_CODE_REG, regUsername.toLowerCase()
					// + "@gozap.com", regPassword, email);
					showDialog(ID_DIALOG_PROGRESS);
				}
			}
			break;
		}
		return false;
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if (arg0.equals(rb_man) && arg1) {
			sexflag = 1;
			Log.e("sex", sexflag + "");
		} else if (arg0.equals(rb_woman) && arg1) {
			sexflag = 0;
			Log.e("sex", sexflag + "");
		}

	}

}
