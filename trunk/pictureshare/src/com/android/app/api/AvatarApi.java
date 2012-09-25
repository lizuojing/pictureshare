package com.android.app.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;

import com.android.app.config.Config;
import com.android.app.entity.Avatar;
import com.android.app.net.HttpResultJson;
import com.android.app.net.NetService;

/**
 * 图片相关api
 * 
 * @author Administrator
 * 
 */
public class AvatarApi extends BaseApi {
	
	private static final String AVATAR_UPLOAD_URL = "007/uploadphoto";
	private static final String TAG = "AvatarApi";

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
	public void uploadAvatar(final Context context,final int requestCode,String filepath) {
		new AsyncTask<String, Integer, ApiResult<String>>() {
			@Override
			protected ApiResult<String> doInBackground(String... parameters) {
				String filepath = parameters[0];
				ApiResult<String> apiResult = new ApiResult<String>();
				apiResult.setResultCode(ApiResult.RESULT_FAIL);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("photo", "filepath"));
				params.add(new BasicNameValuePair("email", "leiyry"));
				
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
	 * 发送图片信息
	 * 
	 * @param params
	 * @return
	 */
	public void uploadAvatarInfo(AvatarRequestParam params) {

	}

	/**
	 *图片分享
	 * 
	 * @param params
	 * @return
	 */
	public void shareAvatar(AvatarRequestParam params) {

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
							// TODO Auto-generated catch block
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
