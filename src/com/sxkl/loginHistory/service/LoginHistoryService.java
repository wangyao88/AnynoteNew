package com.sxkl.loginHistory.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.sxkl.loginHistory.model.LoginHistory;

/**
 * 登陆历史处理类
 * @author wangyao
 * @date 2015-10-21
 */
public interface LoginHistoryService {

	/**
	 * 添加登陆记录
	 * @param userId 登陆人ID
	 */
	public String addLoginHistory(String userId);

	/**
	 * 设置登出时间及单次总登陆时间
	 * @param loginHistoryId 
	 */
	public void updateLoginOutTime(String loginHistoryId);

	/**
	 * 获取登陆历史记录数据
	 * @param pageSize 
	 * @param pageNo 
	 * @return 登陆历史记录数据
	 */
	public String getLoginHistory(int pageNo, int pageSize);

	/**
	 * 获取当前登录人登录信息概况
	 * @param userId 当前登录人ID
	 * @return 当前登录人登录信息概况
	 */
	public String getLoginHistoryInfos(String userId);

	/**
	 * 获取当前扥鼓楼人当月登陆信息曲线图
	 * @param userId 当前登录人ID
	 * @return
	 */
	public String getCurrentMonthLoginHistory(String userId);

}
