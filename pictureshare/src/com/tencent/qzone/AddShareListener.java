package com.tencent.qzone;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;



public class AddShareListener extends BaseRequestListener
{
  private static final String TAG = "AddShareListener";
  private Callback mCallback;

  public AddShareListener(Callback callback)
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
      if (ret == 0)
        this.mCallback.onSuccess(obj.getString("share_id"));
      else
        this.mCallback.onFail(ret, msg);
    }
    catch (NumberFormatException e) {
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