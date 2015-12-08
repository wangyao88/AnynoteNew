package com.sxkl.workManagement.dao;

import java.util.List;

import com.sxkl.workManagement.model.WorkItem;
import com.sxkl.workManagement.model.WorkMain;

/**
 * 工作管理持久层接口
 * @author wangyao
 * @date 2015-11-17
 */
public interface WorkManageDao {

	/**
	 * 分页获取工作日志信息
	 * @param start 开始位置
	 * @param limit 总共获取条数
	 * @param userId 等钱登录人ID
	 * @return 工作日志信息
	 */
	public List<WorkMain> getWorkMainPage(int start, int limit, String userId);

	/**
	 * 获取工作日志总数
	 * @param userId 当前登陆人ID
	 * @return 工作日志总数
	 * @throws Exception 
	 */
	public int getWorkMainPageNum(String userId) throws Exception;

	/**
	 * excel导入工作日志信息
	 * @param workMain
	 * @throws Exception 
	 */
	public void addWorkMainByExcel(WorkMain workMain) throws Exception;

	/**
	 * 根据主键获取工作日志信息
	 * @param wmId 工作日志主键
	 * @return 工作日志信息
	 */
	public WorkMain getWorkMainById(String wmId);

	/**
	 * 根据主键获取工作日志项信息
	 * @param wmId 工作日志主键
	 * @return 工作日志项信息
	 */
	public List<WorkItem> getWorkItemsById(String wmId);

}
