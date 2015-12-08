package com.sxkl.loginHistory.dao;

import java.util.List;
import java.util.Map;

import com.sxkl.loginHistory.model.LoginHistory;

/**
 * 登陆历史持久层接口
 * @author wangyao
 * @date 2015-10-21
 */
public interface LoginHistoryDao {

	/**
	 * 添加登陆历史记录
	 * @param loginHistory 登录人ID
	 */
	public void addLoginHistory(LoginHistory loginHistory);

	/**
	 * 查询登录人姓名
	 * @param userId 登录人ID
	 * @return 登录人姓名
	 */
	public String getUserNameByUserId(String userId);

	/**
	 * 查询无退出时间的登陆历史记录
	 * @return无退出时间的登陆历史记录
	 */
	public List<LoginHistory> getLoginHistoryWithoutLoginOutTime();

	/**
	 * 获取登陆历史记录数据
	 * @return 登陆历史记录数据
	 */
	public List<LoginHistory> getLoginHistory();

	/**
	 * 设置登出时间
	 * @param loginHistory 登陆ID
	 */
	public void setLoginOutTime(LoginHistory loginHistory);

	/**
	 * 查询当前登陆人登陆信息概况
	 * @param userId 当前登录人ID
	 * @return 当前登陆人登录信息概况
	 */
	public List<Map<String, String>> getLoginHistoryInfos(String userId);

	/**
	 * 获取当前扥鼓楼人当月登陆信息曲线图
	 * @param userId 当前登录人ID
	 * @return
	 */
	public List<LoginHistory> getCurrentMonthLoginHistory(String userId);

}
