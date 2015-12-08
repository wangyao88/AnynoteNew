package com.sxkl.attendence.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 考勤记录服务接口
 * @author wangyao
 * @date 205-11-03
 */
public interface AttendenceTimeRecordService {

	/**
	 * 分页查询考勤记录
	 * @param start 开始条数
	 * @param limit 一页条数
	 * @param userId 当前登录人ID
	 * @return 考勤记录
	 */
	public String getAttendenceTimeRecordsPage(int start, int limit, String userId);

	/**
	 * 添加打卡记录
	 * @param request 
	 * @param userId 当前登陆人ID
	 * @param a 
	 * @param attendenceTimeRecord 打卡类型
	 * @return 保存返回结果 {"result":"{0}","attendenceTimeRecord":"attendenceTimeRecord"} {0} 取值为:early.late,success
	 */
	public String addAttendenceTimeRecord(HttpServletRequest request, String atrId,String userId);

	/**
	 * 添加缺失打卡记录
	 * @param userId 当前登陆人ID
	 * @return 操作结果
	 */
	public String addDeficiencyAttendenceTimeRecord(String userId);

}
