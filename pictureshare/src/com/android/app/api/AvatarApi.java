package com.android.app.api;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.app.config.Config;
import com.android.app.entity.Avatar;
import com.android.app.utils.Utils;
import com.android.app.utils.Utils.APNData;

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
				postImage(context, fileInputStream);
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
		}.execute("");
	}
	
	public static String postImage(Context context, InputStream inputSteam)
	{
		String result = null;
		
		if (inputSteam == null)
		{
			return null;
		}
		
		String BOUNDARY = "---------------------------dakslfdafdfafdafdaf";
		
		StringBuffer sb = new StringBuffer();
		sb = sb.append("--");
		sb = sb.append(BOUNDARY);
		sb = sb.append("\r\n");
		sb = sb.append("Content-Disposition: form-data; name=\""+"upload"+"\"; filename=\""+"avatar.jpg"+"\"\r\n");
		sb = sb.append("Content-Type: Content-Type: image/jpeg\r\n\r\n");
		byte[] data = sb.toString().getBytes();
		byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
		
		try
		{			
			byte[] file=new byte[inputSteam.available()];
			inputSteam.read(file);
	        
			URL url = new URL(Config.Server_URL + AVATAR_UPLOAD_URL);
			
			HttpURLConnection connection = null;
			
			APNData apn = Utils.getPreferAPNData(context);
        	
        	if (apn != null && apn.hasProxy())
            {
                try
                {
                     Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(apn.proxy, apn.port));
                     connection = (HttpURLConnection) url.openConnection(proxy);
                }
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            } 
        	else
        	{
        		connection = (HttpURLConnection)url.openConnection();
            }
			
			connection.setDoOutput(true);
			connection.setDoInput(true);   
			connection.setRequestMethod("POST");   
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDARY);   
			connection.setRequestProperty("Content-Length", String.valueOf(data.length + file.length + end_data.length));   
	        connection.setUseCaches(false);   
	        connection.connect();   
	   
	        DataOutputStream out = new DataOutputStream(connection.getOutputStream());   
	        out.write(data);   
	        out.write(file);   
	        out.write(end_data);   
	   
	        out.flush();   
	        out.close();
	        
	        if (connection.getResponseCode() == 200) 
	        {
		        InputStream inStream = connection.getInputStream();
		        result = Utils.inputStream2String(inStream);
		        
		        Log.i(TAG, "uploaded image ---- " + result);
		        
		        if (result != null && (result.equalsIgnoreCase("3001") || result.equalsIgnoreCase("3002")))
		        {
		        	result = null;
		        }
	        }
	        else
	        {
	        	 Log.i(TAG, "connection.getResponseCode()=" + connection.getResponseCode());
	        }
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
//			DatoutieApp.showError(R.string.add_avatar_failed);
		}
		
		return result;
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
