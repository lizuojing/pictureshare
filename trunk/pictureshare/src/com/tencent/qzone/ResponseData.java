package com.tencent.qzone;

public final class ResponseData
{
  private int statusCode;
  private byte[] responseBody;

  public ResponseData(int statusCode, byte[] responseBody)
  {
    this.statusCode = statusCode;
    this.responseBody = responseBody;
  }

  public int getStatusCode()
  {
    return this.statusCode;
  }

  public byte[] getResponseBody() {
    return this.responseBody;
  }
}