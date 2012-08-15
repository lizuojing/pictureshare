package com.android.app.api;

public class BaseApiImp implements BaseApi {

	/**
	 * 创建连接
	 * @param method
	 * @param params
	 * @return
	 */
	@Override
	public ApiResult connect(String method, BaseRequestParam params) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 网络请求接口
	 * @param method
	 * @param params
	 * @return
	 */
	@Override
	public ApiResult baseRequest(String method, BaseRequestParam params) {//TODO 需要考虑反复创建断开的性能
		//创建连接 
		
		//发送请求
		
		//断开连接 
		return null;
	}

	
	/**
	 * 断开连接
	 * @param method
	 * @param params
	 * @return
	 */
	@Override
	public ApiResult disconnect(String method, BaseRequestParam params) {
		// TODO Auto-generated method stub
		return null;
	}

}
