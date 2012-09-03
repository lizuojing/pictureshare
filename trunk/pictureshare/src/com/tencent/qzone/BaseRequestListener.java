package com.tencent.qzone;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.util.Log;


public class BaseRequestListener
  implements IRequestListener
{
  private static final String TAG = "BaseRequestListener";

  public void onComplete(String response, Object state)
  {
    response = response + "ddddd";
  }

  public void onFileNotFoundException(FileNotFoundException e, Object state)
  {
	  Log.i(TAG, "Resource not found:" + e.getMessage());
  }

  public void onIOException(IOException e, Object state)
  {
	  Log.i(TAG, "Network Error:" + e.getMessage());
  }
}