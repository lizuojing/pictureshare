package com.android.app.api;

/**
 * api接口
 * @author Administrator
 *
 */
public interface BaseApi {
	public ApiResult connect(String method,BaseRequestParam params);
	public ApiResult baseRequest(String method,BaseRequestParam params);
	public ApiResult disconnect(String method,BaseRequestParam params);

}
