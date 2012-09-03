package com.tencent.qzone;

import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AsyncHttpPostRunner
{
  public void request(String url, Bundle parameters, IRequestListener listener)
  {
    request(url, parameters, "POST", listener, null);
  }

  public void request(final String url, final Bundle parameters, final String httpMethod, final IRequestListener listener, final Object state)
  {
      new Thread() {
          @Override
          public void run() {
              try {
                  String resp = ClientHttpRequest.openUrl(url, httpMethod, parameters);
                  listener.onComplete(resp, state);
                } catch (FileNotFoundException e) {
                  listener.onFileNotFoundException(e, state);
                } catch (IOException e) {
                  listener.onIOException(e, state);
                }
          }
      }.start();
      
  }
}