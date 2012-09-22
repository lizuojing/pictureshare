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

import android.content.Context;
import android.os.AsyncTask;

import com.android.app.utils.Base64;

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
	private String computeHmac(String baseString, String key) throws NoSuchAlgorithmException, InvalidKeyException,
			IllegalStateException, UnsupportedEncodingException {
		// Mac类提供 消息验证码（Message Authentication Code，MAC）算法的功能。
		// getInstance返回实现指定 MAC 算法的 Mac对象。
		Mac mac = Mac.getInstance("HmacSHA1");

		SecretKeySpec secret = new SecretKeySpec(key.getBytes(), mac.getAlgorithm());
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
				encodedValue = value != null ? URLEncoder.encode(value, "utf-8") : "";
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