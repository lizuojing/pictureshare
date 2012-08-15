package com.android.app.api;

/**
 * 用户相关api
 * 
 * @author Administrator
 * 
 */
public class UserApi extends BaseApiImp {

	/**
	 * 用户注册
	 * 
	 * @param params
	 * @return
	 */
	public ApiResult register(OwnerRequestParam params) {

		// 参数封装

		// 发送请求
		baseRequest(null, params);

		return null;
	}

	/**
	 * 用户登录
	 * 
	 * @param params
	 * @return
	 */
	public ApiResult login(OwnerRequestParam params) {
		// 参数封装

		// 发送请求
		baseRequest(null, params);

		return null;
	}

	/**
	 * 用户信息更新
	 * 
	 * @param params
	 * @return
	 */
	public ApiResult updateUserInfo(OwnerRequestParam params) {
		// 参数封装

		// 发送请求
		baseRequest(null, params);

		return null;
	}

	/**
	 * 用户信息同步
	 * 
	 * @param params
	 * @return
	 */
	public ApiResult syncUserInfo(OwnerRequestParam params) {
		// 参数封装

		// 发送请求
		baseRequest(null, params);

		return null;
	}

	/**
	 * 修改密码
	 * 
	 * @param params
	 * @return
	 */
	public ApiResult reSetPassWord(OwnerRequestParam params) {
		// 参数封装

		// 发送请求
		baseRequest(null, params);

		return null;
	}

	/**
	 * 邀请码登录
	 * 
	 * @param params
	 * @return
	 */
	public ApiResult inviteCodeLogin(OwnerRequestParam params) {
		// 参数封装

		// 发送请求
		baseRequest(null, params);

		return null;
	}
	
	/**
	 * 分享
	 * 
	 * @param params
	 * @return
	 */
	public ApiResult addShare(OwnerRequestParam params) {
		// 参数封装

		// 发送请求
		baseRequest(null, params);

		return null;
	}
}
