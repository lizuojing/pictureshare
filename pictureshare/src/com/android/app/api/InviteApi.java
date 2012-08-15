package com.android.app.api;

/**
 * 邀请相关api
 * @author Administrator
 *
 */
public class InviteApi extends BaseApiImp{
	
	/**
	 * 获取邀请码
	 * @param params
	 * @return
	 */
	public ApiResult getInviteCode(InviteRequestParam params) {

		// 参数封装

		// 发送请求
		baseRequest(null, params);
		return null;
	}
	
}
