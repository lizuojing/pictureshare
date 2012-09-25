package com.android.app.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.app.config.Config;
import com.android.app.entity.VersionInfo;
import com.android.app.net.HttpResult;
import com.android.app.net.HttpResultXml;
import com.android.app.net.NetService;
import com.android.app.utils.Utils;

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
//				params.add(new BasicNameValuePair("method", "sys.versionCtl.get"));

				//params={"params":{"version":"1.0"}}
				// 后期要将IP_50 替换为 Utils.getPlatform()
//				String value = "<item>" + "<name>xinrebang</name>" + "<type>" + "AR_21" + "</type>" + "<page>1</page>"
//						+ "<pages>1</pages>" + "</item>";
//
//				value = XORencode(value, ApiConfig.APP_SECRET);
				params.add(new BasicNameValuePair("params", "{'params':{'version':'1.0'}}"));
//				params.add(new BasicNameValuePair("oauth_consumer_key", ApiConfig.APP_KEY));
//
//				String baseString = ApiConfig.APP_SECRET + "&" + ApiConfig.APP_KEY + "&sys.versionCtl.get&" + value;
//				String signature = new UserApi(context).getSignature(baseString, ApiConfig.APP_SECRET);
//				params.add(new BasicNameValuePair("oauth_signature", signature));

				HttpResultXml result = NetService.httpPostReturnXml(context,
						Config.Server_URL + VERSION_SERVICE_URL, params);

				if (result.getResultCode() == HttpResult.RESULT_OK) {
					apiResult.setResultCode(ApiResult.RESULT_OK);
					VersionInfo version = null;
					XmlPullParser parser = result.getParser();
					try {
						int eventType = parser.getEventType();
						breakTag: while (eventType != XmlPullParser.END_DOCUMENT) {
							switch (eventType) {
							case XmlPullParser.START_DOCUMENT:
								break;
							case XmlPullParser.START_TAG:
								if ("item".equalsIgnoreCase(parser.getName())) {
									version = new VersionInfo();
								} else if ("versionId".equalsIgnoreCase(parser.getName())) {
									version.setVersionId(parser.nextText());
								} else if ("name".equalsIgnoreCase(parser.getName())) {
									version.setName(parser.nextText());
								} else if ("type".equalsIgnoreCase(parser.getName())) {
									version.setType(parser.nextText());
								} else if ("version".equalsIgnoreCase(parser.getName())) {
									version.setVersion(parser.nextText());
								} else if ("buildno".equalsIgnoreCase(parser.getName())) {
									version.setBuildno(parser.nextText());
								} else if ("url".equalsIgnoreCase(parser.getName())) {
									version.setUrl(parser.nextText());
								} else if ("wurl".equalsIgnoreCase(parser.getName())) {
									version.setWurl(parser.nextText());
								} else if ("ssize".equalsIgnoreCase(parser.getName())) {
									version.setSsize(parser.nextText());
								} else if ("describe".equalsIgnoreCase(parser.getName())) {
									version.setDescribe(parser.nextText());
								} else if ("createTime".equalsIgnoreCase(parser.getName())) {
									String createStr = parser.nextText();
									long createTime = Long.parseLong(createStr);
									version.setCreateTime(createTime);
								}
								break;
							case XmlPullParser.END_TAG:
								if ("item".equalsIgnoreCase(parser.getName())) {
									ArrayList<VersionInfo> entities = new ArrayList<VersionInfo>();
									VersionInfo.UpdateType updateType=Utils.getAppVersionUpdateType(context,version.getVersion());
									version.setUpdateType(updateType);
									entities.add(version);
									apiResult.setEntities(entities);
									break breakTag;
								}
								break;
							case XmlPullParser.END_DOCUMENT:
								break;
							}
							eventType = parser.next();
						}
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

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
