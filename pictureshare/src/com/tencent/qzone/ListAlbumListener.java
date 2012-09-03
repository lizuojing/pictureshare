package com.tencent.qzone;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;



public class ListAlbumListener extends BaseRequestListener
{
  private static final String TAG = "ListAlbumListener";
  private Callback mCallback;

  public ListAlbumListener(Callback callback)
  {
    this.mCallback = callback;
  }

  public void onComplete(String response, Object state)
  {
    super.onComplete(response, state);
    try {
      JSONObject obj = ParseResoneJson.parseJson(response);

      int ret = 0;
      String msg = "";
      try {
        ret = obj.getInt("ret");
        msg = obj.getString("msg");
      }
      catch (JSONException localJSONException1)
      {
      }
      if (ret == 0) {
        int albumnum = obj.getInt("albumnum");
        JSONArray albumArray = obj.getJSONArray("album");
        ArrayList<Album> albums = new ArrayList<Album>();
        for (int i = 0; i < albumnum; i++) {
          JSONObject albumObj = albumArray.getJSONObject(i);
          Album album = new Album(albumObj.getString("albumid"), 
            albumObj.getInt("classid"), 
            albumObj.getLong("createtime"), 
            albumObj.getString("desc"), 
            albumObj.getString("name"), 
            albumObj.getInt("picnum"), 
            albumObj.getInt("priv"));
          albums.add(album);
        }
        this.mCallback.onSuccess(albums);
      } else {
        this.mCallback.onFail(ret, msg);
      }
    } catch (NumberFormatException e) {
      this.mCallback.onFail(-2147483648, e.getMessage());
      e.printStackTrace();
    } catch (JSONException e) {
      this.mCallback.onFail(-2147483648, e.getMessage());
      e.printStackTrace();
    } catch (CommonException e) {
      this.mCallback.onFail(-2147483648, e.getMessage());
      e.printStackTrace();
    }
    Log.i(TAG, response);
  }

  public void onFileNotFoundException(FileNotFoundException e, Object state)
  {
    this.mCallback.onFail(-2147483648, "Resource not found:" + e.getMessage());
  }

  public void onIOException(IOException e, Object state)
  {
    this.mCallback.onFail(-2147483648, "Network Error:" + e.getMessage());
  }
}