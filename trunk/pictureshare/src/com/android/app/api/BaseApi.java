package com.android.app.api;

import android.content.Context;
/**
 * api
 * @author Administrator
 *
 */
public class BaseApi {
	protected Context context;
	protected ApiReturnResultListener returnResultListener;

	public BaseApi(Context context) {
		super();
		this.context = context;
	}

	public ApiReturnResultListener getReturnResultListener() {
		return returnResultListener;
	}

	public void setReturnResultListener(ApiReturnResultListener returnResultListener) {
		this.returnResultListener = returnResultListener;
	}
}
