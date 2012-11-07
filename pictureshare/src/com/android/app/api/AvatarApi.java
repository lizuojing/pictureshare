package com.android.app.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.app.SettingActivity;
import com.android.app.config.Config;
import com.android.app.entity.Avatar;
import com.android.app.entity.Point;
import com.android.app.net.HttpResultJson;
import com.android.app.net.NetService;

/**
 * 图片相关api
 * 
 * @author Administrator
 * 
 */
public class AvatarApi extends BaseApi {
	
	private static final String TAG = "AvatarApi";
	private static final String AVATAR_UPLOAD_URL = "007/uploadphoto";
	private static final String AVATAR_LIKE_URL = "007/like";
	protected static final String AVATAR_INFO_URL = "/007/uploadphotoinfo";
	protected static final String AVATAR_SHAEE_URL = "007/photoshare";

	public AvatarApi(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	/**
	 * 获取单张图片详细信息
	 * 
	 * @param params
	 * @return
	 */
	public void getAvatarInfo(AvatarRequestParam params) {

	}
	
	
	/**
	 * 获取图片集合
	 * 
	 * @param params
	 * @return
	 */
	public void getAvatarList(AvatarRequestParam params) {

	}

	/**
	 * 发送图片
	 * 
	 * @param params
	 * @return
	 */
	public void uploadAvatar(final Context context,final int requestCode,String filepath,final String email) {
		new AsyncTask<String, Integer, ApiResult<String>>() {
			@Override
			protected ApiResult<String> doInBackground(String... parameters) {
				String filepath = parameters[0];
				ApiResult<String> apiResult = new ApiResult<String>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				JSONObject json = null;
				JSONObject jsonparam = null;
				try {
					json = new JSONObject();
					jsonparam = new JSONObject();
					json.put("photo", filepath);
					json.put("email", email);
					jsonparam.put("params", json.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				params.add(new BasicNameValuePair("params", jsonparam.toString()));
				Log.e(TAG, "uploadAvatar params is " + jsonparam.toString());
				
				NameValuePair fileNVPair = new BasicNameValuePair("photo", filepath);
				HttpResultJson result = NetService.updateFile(context,
						Config.Server_URL + AVATAR_UPLOAD_URL, params, fileNVPair);
				
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<String> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,apiResult);
				}
			}
		}.execute(filepath);
	}
	
	/**
	 * 图片赞
	 * 
	 * @param params
	 * @return
	 */
	public void likeAvatar(final Context context,final int requestCode,final String photoid,final String email,final String like) {
		new AsyncTask<Void, Integer, ApiResult<String>>() {
			@Override
			protected ApiResult<String> doInBackground(Void... param) {
				ApiResult<String> apiResult = new ApiResult<String>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				
				JSONObject json = null;
				JSONObject jsonparam = null;
				try {
					json = new JSONObject();
					jsonparam = new JSONObject();
					json.put("email", email);
					json.put("photoid", photoid);
					json.put("like", like);
					jsonparam.put("params", json.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("params", jsonparam.toString()));
				Log.e(TAG, "like params is " + jsonparam.toString());
				HttpResultJson result = NetService.httpPostReturnJson(context, Config.Server_URL + AVATAR_LIKE_URL, params);
				
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<String> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,apiResult);
				}
			}
		}.execute();
	}
	

	/**
	 * 发送图片信息
	 * 
	 * @param params
	 * @return
	 */
	public void sendAvatarInfo(final int requestCode,final AvatarRequestParam avatarparams,final int page,final String pages) {
		new AsyncTask<Void, Integer, ApiResult<String>>() {
			@Override
			protected ApiResult<String> doInBackground(Void... param) {
				ApiResult<String> apiResult = new ApiResult<String>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("params", avatarparams.toJsonString(page+"",pages)));
				
				Log.e(TAG, "sendpicinfo params is " + avatarparams.toJsonString(page+"",pages));
				NetService.httpPostReturnJson(context, Config.Server_URL + AVATAR_INFO_URL, params);
				
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<String> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,apiResult);
				}
			}
		}.execute();
	}

	/**
	 *图片分享
	 * 
	 * @param params
	 * @return
	 */
	public void shareAvatar(final Context context,final int requestCode,final String filepath,String email) {
		new AsyncTask<Void, Integer, ApiResult<String>>() {
			@Override
			protected ApiResult<String> doInBackground(Void... param) {
				ApiResult<String> apiResult = new ApiResult<String>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				JSONObject json = null;
				JSONObject jsonparam = null;
				try {
					json = new JSONObject();
					jsonparam = new JSONObject();
					json.put("email", filepath);
					json.put("photoid", "asdfag");//photoid 由上传完图片获得 
					jsonparam.put("params", json.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("params", jsonparam.toString()));
				Log.e(TAG, jsonparam.toString());
				HttpResultJson result = NetService.httpPostReturnJson(context, Config.Server_URL + AVATAR_SHAEE_URL, params);
				
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<String> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,apiResult);
				}
			}
		}.execute();
	}
	
	
	public void qrcodeAuth(SettingActivity settingActivity, final int requestCode,
			final String photoid, final String email) {
		new AsyncTask<Void, Integer, ApiResult<String>>() {
			@Override
			protected ApiResult<String> doInBackground(Void... param) {
				ApiResult<String> apiResult = new ApiResult<String>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				JSONObject json = null;
				JSONObject jsonparm = null;
				try {
					jsonparm = new JSONObject();
					json = new JSONObject();
					json.put("email", email);
					json.put("photoid", photoid);
					jsonparm.put("params", json.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				params.add(new BasicNameValuePair("params", jsonparm.toString()));
				Log.e(TAG, "qrcode params is " + jsonparm.toString());
				HttpResultJson result = NetService.httpPostReturnJson(context, Config.Server_URL + AVATAR_SHAEE_URL, params);
				
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<String> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,apiResult);
				}
			}
		}.execute();
	}
	
	/**
	 *地图详细图片api
	 * 
	 * @param params
	 * @return
	 */
	public void mapAvatar(AvatarRequestParam params) {
		
	}
	
	/**
	 *大头钉api
	 * 
	 * @param params
	 * @return
	 */
	public void tacks(AvatarRequestParam params) {
		
	}


	public void uploadAvatarList(final Context context, final int requestCode,
			final ArrayList<Avatar> list) {
		/*new AsyncTask<Void, Integer, ApiResult<String>>() {
			@Override
			protected ApiResult<String> doInBackground(Void... params) {
				ApiResult<String> apiResult = new ApiResult<String>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				if(list!=null&&list.size()>0) {
					for(Avatar avatar : list) {
						String filepath = avatar.getPath();
						File f = new File(filepath);
						FileInputStream fileInputStream = null;
						try 
						{
							fileInputStream = new FileInputStream(f);
						} 
						catch (FileNotFoundException e)
						{
							e.printStackTrace();
						}
						String postImage = postImage(context, fileInputStream);
						Log.i(TAG, "postImage is " + postImage);
					}
				}
				return apiResult;
			}

			@Override
			protected void onPostExecute(ApiResult<String> apiResult) {
				if (returnResultListener == null) {
					return;
				}
				if (apiResult.getResultCode() == ApiResult.RESULT_OK) {
					returnResultListener.onReturnSucceedResult(requestCode,apiResult);
				} else {
					returnResultListener.onReturnFailResult(requestCode,apiResult);
				}
			}
		}.execute();*/
	}



}
