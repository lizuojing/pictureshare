package com.android.app.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.app.config.Config;
import com.android.app.entity.VersionInfo;
import com.android.app.net.HttpResult;
import com.android.app.net.HttpResultJson;
import com.android.app.net.NetService;

/**
 * 其他相关api
 */
public class OtherApi extends BaseApi {

	// 版本服务url
	private static final String VERSION_SERVICE_URL = "007/getversion";
    protected static final String TAG = "OtherApi";

	public OtherApi(Context context) {
		super(context);
	}

	/**
	 * 获取最新客户端版本信息
	 * @param returnResultListener
	 */
	public void getLatestAppVersion(final int requestCode) {
		new AsyncTask<Object, Integer, ApiResult<VersionInfo>>() {
			@Override
			protected ApiResult<VersionInfo> doInBackground(Object... strs) {
				ApiResult<VersionInfo> apiResult = new ApiResult<VersionInfo>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("params", "{'params':{'version':'1.0'}}"));
				HttpResultJson result = NetService.httpPostReturnJson(context, Config.Server_URL + VERSION_SERVICE_URL, params);

				if (result.getResultCode() == HttpResult.RESULT_OK) {
					apiResult.setResultCode(ApiResult.RESULT_OK);
//					VersionInfo version = null;
					
					Log.i(TAG, "result is " + result.getJson().toString());

				} else {
					apiResult.setFailCode(result.getFailCode());
					apiResult.setFailMessage(result.getFailMessage());
				}
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<VersionInfo> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,apiResult);
				}
			}
		}.execute("");
	}

	/**
	 * 异或编码
	 * @param s
	 * @param key
	 * @return
	 */
	public static String XORencode(String s, String key) {
		char[] cs = s.toCharArray();// 将此字符串转换为一个新的字符数组。
		for (int i = 0; i < cs.length; i++) {
			cs[i] = (char) (cs[i] ^ (char) key.toCharArray()[i % key.length()]);
		}
		String result = new String(cs);
		return result;
	}

}
