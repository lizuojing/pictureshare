package com.tencent.qzone;

import android.os.Bundle;
import android.util.Log;


public class TencentOpenAPI
{
  private static final String TAG = "TencentOpenAPI";

  public static void openid(String access_token, Callback callback)
  {
    String url = String.format("https://graph.qq.com/oauth2.0/me?access_token=%s", new Object[] { access_token });
    asyncRequest(url, new OpenIDListener(callback));
  }

  public static void userInfo(String access_token, String appid, String openid, Callback callback)
  {
    String url = String.format("https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s", new Object[] { access_token, appid, openid });
    asyncRequest(url, new UserInfoListener(callback));
  }

  public static void userProfile(String access_token, String appid, String openid, Callback callback)
  {
    String url = String.format("https://graph.qq.com/user/get_user_profile?access_token=%s&oauth_consumer_key=%s&openid=%s", new Object[] { access_token, appid, openid });
    asyncRequest(url, new UserProfileListener(callback));
  }

  public static void addShare(String access_token, String appid, String openid, Bundle parameters, Callback callback)
  {
    parameters.putString("format", "json");
    parameters.putString("source", "2");
    parameters.putString("access_token", access_token);
    parameters.putString("oauth_consumer_key", appid);
    parameters.putString("openid", openid);

    asyncPost("https://graph.qq.com/share/add_share", parameters, new AddShareListener(callback));
  }

  public static void addTopic(String access_token, String appid, String openid, Bundle parameters, Callback callback)
  {
    parameters.putString("format", "json");

    parameters.putString("access_token", access_token);
    parameters.putString("oauth_consumer_key", appid);
    parameters.putString("openid", openid);

    asyncPost("https://graph.qq.com/shuoshuo/add_topic", parameters, new AddTopicListener(callback));
  }

  public static void listAlbum(String access_token, String appid, String openid, Callback callback)
  {
    String url = String.format("https://graph.qq.com/photo/list_album?access_token=%s&oauth_consumer_key=%s&openid=%s", new Object[] { access_token, appid, openid });
    asyncRequest(url, new ListAlbumListener(callback));
  }

  public static void addAlbum(String access_token, String appid, String openid, Bundle parameters, Callback callback)
  {
    parameters.putString("format", "json");
    parameters.putString("access_token", access_token);
    parameters.putString("oauth_consumer_key", appid);
    parameters.putString("openid", openid);

    asyncPost("https://graph.qq.com/photo/add_album", parameters, new AddAlbumListener(callback));
  }

  public static void uploadPic(String access_token, String appid, String openid, Bundle parameters, Callback callback)
  {
    parameters.putString("format", "json");
    parameters.putString("access_token", access_token);
    parameters.putString("oauth_consumer_key", appid);
    parameters.putString("openid", openid);

    asyncPost("https://graph.qq.com/photo/upload_pic", parameters, new UploadPicListener(callback));
  }

  private static void asyncRequest(String url, IRequestListener listener) {
	  Log.i(TAG, url);
    new AsyncHttpRequestRunner().request(url, null, listener);
  }

  private static void asyncPost(String url, Bundle parameters, IRequestListener listener) {
	  Log.i(TAG, url);
    new AsyncHttpPostRunner().request(url, parameters, listener);
  }
}