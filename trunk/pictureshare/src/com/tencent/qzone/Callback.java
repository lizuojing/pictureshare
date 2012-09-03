package com.tencent.qzone;

public abstract interface Callback
{
  public abstract void onSuccess(Object paramObject);

  public abstract void onFail(int paramInt, String paramString);
}