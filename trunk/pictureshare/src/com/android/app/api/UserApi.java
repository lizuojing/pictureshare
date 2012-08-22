package com.android.app.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;

/**
 * 用户相关api
 * 
 * @author Administrator
 * 
 */
public class UserApi extends BaseApi {

	public UserApi(Context context) {
		super(context);
	}

	/**
	 * 用户注册
	 * 
	 * @param params
	 * @return
	 */
	public void register(final int requestCode, final OwnerRequestParam ownerParms) {

		new AsyncTask<Object, Integer, ApiResult<Object>>() {
			@Override
			protected ApiResult<Object> doInBackground(Object... strs) {
				ApiResult<Object> apiResult = new ApiResult<Object>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("oauth_consumer_key", ApiConfig.APP_KEY));
				params.add(new BasicNameValuePair("username", ownerParms.getUsername()));
				params.add(new BasicNameValuePair("password", ownerParms.getPassword()));
				params.add(new BasicNameValuePair("email", ownerParms.getEmail()));

//				String baseString = ApiConfig.APP_SECRET + "&" + ApiConfig.APP_KEY + "&" + username + "&" + password;
//				String signature = getSignature(baseString, ApiConfig.APP_SECRET);

//				params.add(new BasicNameValuePair("oauth_signature", signature));

//				HttpResultXml result = NetService.httpPostReturnXml(context,
//						context.getString(R.string.config_gozap_api_url) + REGISTER_URL, params);

//				if (result.getResultCode() == HttpResult.RESULT_OK) {
//					apiResult.setResultCode(ApiResult.RESULT_OK);
//				} else {
//					apiResult.setFailCode(result.getFailCode());
//					apiResult.setFailMessage(result.getFailMessage());
//				}
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<Object> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode, apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode, apiResult);
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
	public void login(final int requestCode, final OwnerRequestParam ownerParms) {
		new AsyncTask<Object, Integer, ApiResult<Object>>() {
			@Override
			protected ApiResult<Object> doInBackground(Object... strs) {
				ApiResult<Object> apiResult = new ApiResult<Object>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("x_auth_username", ownerParms.getUsername()));
				params.add(new BasicNameValuePair("x_auth_password", ownerParms.getPassword()));
				params.add(new BasicNameValuePair("x_auth_model", "client_auth"));
//				params.add(new BasicNameValuePair("oauth_consumer_key", ApiConfig.APP_KEY));
				params.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
				params.add(new BasicNameValuePair("oauth_timestamp", System.currentTimeMillis() / 1000 + ""));
				params.add(new BasicNameValuePair("oauth_nonce", UUID.randomUUID().toString()));
				params.add(new BasicNameValuePair("oauth_version", "1.0"));

				String httpMethod = "POST";
				String urlEncode = null;
				try {
					urlEncode = URLEncoder.encode("http://api.gozap.com/xauth/access_token", "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

//				String sortParams = sortAndEncodeParams(params);

//				String baseString = httpMethod + "&" + urlEncode + "&" + sortParams;
//				String signature = getSignature(baseString, ApiConfig.APP_SECRET);

//				params.add(new BasicNameValuePair("oauth_signature", signature));
//
//				HttpResultXml result = NetService.httpPostReturnXml(context,
//						context.getString(R.string.config_gozap_api_url) + LOGIN_URL, params);

//				if (result.getResultCode() == HttpResult.RESULT_OK) {
//					apiResult.setResultCode(ApiResult.RESULT_OK);
//
//					XmlPullParser parser = result.getParser();
//					try {
//						int eventType = parser.getEventType();
//						breakTag: while (eventType != XmlPullParser.END_DOCUMENT) {
//							switch (eventType) {
//							case XmlPullParser.START_DOCUMENT:
//								break;
//							case XmlPullParser.START_TAG:
//								if ("access_token".equalsIgnoreCase(parser.getName())) {
//									String access_token = parser.nextText();
//									saveAccessToken(access_token);
//									saveUsername(username);
//									break breakTag;
//								}
//								break;
//							case XmlPullParser.END_TAG:
//								break;
//							case XmlPullParser.END_DOCUMENT:
//								break;
//							}
//							eventType = parser.next();
//						}
//					} catch (XmlPullParserException e) {
//						CTLog.e(TAG, e);
//					} catch (IOException e) {
//						CTLog.e(TAG, e);
//					}
//
//				} else {
//					apiResult.setFailCode(result.getFailCode());
//					apiResult.setFailMessage(result.getFailMessage());
//				}
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<Object> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode, apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode, apiResult);
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
}
