package com.sxkl.workManagement.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工作管理服务层接口
 * @author wangyao
 * @date 2015-11-17
 */
public interface WorkManageService {

	/**
	 * 分页获取工作日志信息
	 * @param start 开始位置
	 * @param limit 总共获取条数
	 * @param userId 等钱登录人ID
	 * @return 工作日志信息
	 */
	public String getWorkMainPage(int start, int limit, String userId);

	/**
	 * excel导入工作日志信息
	 * @param userId 当前登录人ID
	 * @param request 
	 * @param response 
	 * @return 操作结果
	 */
	public String addWorkMainByExcel(String userId, String title, String path);

	/**
	 * 删除excel工作日志信息
	 * @param path excel工作日志路径
	 */
	public void deleteExcel(String path);

	/**
	 * 根据主键获取工作日志信息
	 * @param wmId 工作日志主键
	 * @return 获取工作日志信息
	 */
	public String getWorkMainById(String wmId);

	/**
	 * 根据主键获取工作日志项信息
	 * @param wmId 工作日志主键
	 * @return 获取工作日志项信息
	 */
	public String getWorkItemsById(String wmId);

}
