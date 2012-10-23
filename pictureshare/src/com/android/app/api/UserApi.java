package com.android.app.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.app.config.Config;
import com.android.app.entity.User;
import com.android.app.entity.VersionInfo;
import com.android.app.net.HttpResult;
import com.android.app.net.HttpResultJson;
import com.android.app.net.NetService;
import com.android.app.utils.Base64;

/**
 * 用户相关api
 * 
 * @author Administrator
 * 
 */
public class UserApi extends BaseApi {

	// 版本服务url
	private static final String URL_REG = "007/userreg";
	private static final String URL_LOGIN = "007/userlogin";
	private static final String URL_USERMODIFY = "/007/usermodify";

	protected static final String TAG = "RegeditApi";

	/**
	 * 用户注册
	 * 
	 * @param returnResultListener
	 */
	public void regeditUser(final int requestCode, final User user) {
		new AsyncTask<Object, Integer, ApiResult<VersionInfo>>() {
			@Override
			protected ApiResult<VersionInfo> doInBackground(Object... strs) {
				ApiResult<VersionInfo> apiResult = new ApiResult<VersionInfo>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				Log.e("params:", user.ToRegeditString());
				params.add(new BasicNameValuePair("params", user
						.ToRegeditString()));
				HttpResultJson result = NetService.httpPostReturnJson(context,
						Config.Server_URL + URL_REG, params);

				if (result.getResultCode() == HttpResult.RESULT_OK) {
					apiResult.setResultCode(ApiResult.RESULT_OK);
					// VersionInfo version = null;

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
					returnResultListener.onReturnSucceedResult(requestCode,
							apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,
							apiResult);
				}
			}
		}.execute("");
	}

	public UserApi(Context context) {
		super(context);
	}

	/**
	 * 用户注册
	 * 
	 * @param params
	 * @return
	 */
	public void register(final int requestCode,
			final OwnerRequestParam ownerParms) {

		new AsyncTask<Object, Integer, ApiResult<Object>>() {
			@Override
			protected ApiResult<Object> doInBackground(Object... strs) {
				ApiResult<Object> apiResult = new ApiResult<Object>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// params.add(new BasicNameValuePair("oauth_consumer_key",
				// ApiConfig.APP_KEY));
				params.add(new BasicNameValuePair("username", ownerParms
						.getUsername()));
				params.add(new BasicNameValuePair("password", ownerParms
						.getPassword()));
				params.add(new BasicNameValuePair("email", ownerParms
						.getEmail()));

				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<Object> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,
							apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,
							apiResult);
				}
			}
		}.execute("");

	}

	/**
	 * 用户登录
	 * 
	 * @param params
	 * @return
	 */
	public void login(final int requestCode, final User user) {
		new AsyncTask<Object, Integer, ApiResult<Object>>() {
			@Override
			protected ApiResult<Object> doInBackground(Object... strs) {

				ApiResult<Object> apiResult = new ApiResult<Object>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("params", user
						.toLoginString()));
				Log.e("params", user.toLoginString());
				HttpResultJson result = NetService.httpPostReturnJson(context,
						Config.Server_URL + URL_LOGIN, params);
				if (result.getResultCode() == HttpResult.RESULT_OK) {
					apiResult.setResultCode(ApiResult.RESULT_OK);
					JSONObject jsonObject = result.getJson();
					String opeResult = "opeResult";
					try {
						String opeResults = jsonObject.getString(opeResult);
						if (opeResults.equals("0")) {
							Log.e("", "登录失败");
						} else {
							Log.e("", "登录成功");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					apiResult.setFailCode(result.getFailCode());
					apiResult.setFailMessage(result.getFailMessage());
				}
				return apiResult;

			}

			@Override
			protected void onPostExecute(ApiResult<Object> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,
							apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,
							apiResult);
				}
			}
		}.execute("");
	}

	/**
	 * 用户登录
	 * 
	 * @param params
	 * @return
	 */
	public void reName(final int requestCode, final User user) {
		new AsyncTask<Object, Integer, ApiResult<Object>>() {
			@Override
			protected ApiResult<Object> doInBackground(Object... strs) {

				ApiResult<Object> apiResult = new ApiResult<Object>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("params", user
						.toReNameString()));

				Log.e("params", user.toReNameString());

				HttpResultJson result = NetService.httpPostReturnJson(context,
						Config.Server_URL + URL_USERMODIFY, params);

				if (result.getResultCode() == HttpResult.RESULT_OK) {
					apiResult.setResultCode(ApiResult.RESULT_OK);
					// VersionInfo version = null;

				} else {
					apiResult.setFailCode(result.getFailCode());
					apiResult.setFailMessage(result.getFailMessage());
				}
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<Object> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,
							apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,
							apiResult);
				}
			}
		}.execute("");
	}

	/**
	 * 用户信息更新
	 * 
	 * @param params
	 * @return
	 */
	public void updateUserInfo(OwnerRequestParam params) {

	}

	/**
	 * 用户信息同步
	 * 
	 * @param params
	 * @return
	 */
	public void syncUserInfo(OwnerRequestParam params) {

	}

	/**
	 * 修改密码
	 * 
	 * @param params
	 * @return
	 */
	public void reSetPassWord(OwnerRequestParam params) {

	}

	/**
	 * 邀请码登录
	 * 
	 * @param params
	 * @return
	 */
	public void inviteCodeLogin(OwnerRequestParam params) {

	}

	/**
	 * 分享
	 * 
	 * @param params
	 * @return
	 */
	public void addShare(OwnerRequestParam params) {

	}

	/**
	 * 生成签名
	 * 
	 * @param baseString
	 * @param oauth_consumer_secret
	 * @return Signature
	 */
	public String getSignature(String baseString, String oauth_consumer_secret) {
		String key = oauth_consumer_secret + "&";
		String signature = null;
		try {
			signature = computeHmac(baseString, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signature;
	}

	/**
	 * 用 HmacSHA1 算法加密
	 * 
	 * @param baseString
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws UnsupportedEncodingException
	 */
	private String computeHmac(String baseString, String key)
			throws NoSuchAlgorithmException, InvalidKeyException,
			IllegalStateException, UnsupportedEncodingException {
		// Mac类提供 消息验证码（Message Authentication Code，MAC）算法的功能。
		// getInstance返回实现指定 MAC 算法的 Mac对象。
		Mac mac = Mac.getInstance("HmacSHA1");

		SecretKeySpec secret = new SecretKeySpec(key.getBytes(),
				mac.getAlgorithm());
		mac.init(secret);
		byte[] digest = mac.doFinal(baseString.getBytes());
		return getBase64String(digest);
	}

	/**
	 * Base64编码
	 * 
	 * @param bytes
	 * @return
	 */
	private String getBase64String(byte[] bytes) {
		byte[] encoded = Base64.encodeBase64(bytes);
		return new String(encoded);
	}

	/**
	 * 对baseString排序并encode编码(encode两次)
	 * 
	 * @param params
	 * @return
	 */
	private String sortAndEncodeParams(List<NameValuePair> params) {
		Collections.sort(params, new Comparator<NameValuePair>() {
			@Override
			public int compare(NameValuePair lhs, NameValuePair rhs) {
				if (lhs.getName().compareTo(rhs.getName()) != 0) {
					return lhs.getName().compareTo(rhs.getName());
				} else {
					return rhs.getValue().compareTo(lhs.getValue());
				}
			}
		});
		StringBuffer sb = new StringBuffer();

		for (NameValuePair pair : params) {
			String encodedName = null;
			try {
				encodedName = URLEncoder.encode(pair.getName(), "utf-8");
				encodedName = URLEncoder.encode(encodedName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			final String value = pair.getValue();
			String encodedValue = null;
			try {
				encodedValue = value != null ? URLEncoder
						.encode(value, "utf-8") : "";
				encodedValue = URLEncoder.encode(encodedValue, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (sb.length() > 0) {
				sb.append("%26");
			}
			sb.append(encodedName);
			sb.append("%3D");
			sb.append(encodedValue);
		}
		return sb.toString();
	}
}
