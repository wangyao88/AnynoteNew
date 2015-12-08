package com.sxkl.attendence.service;

/**
 * 请假服务接口
 * @author wangyao
 * @date 2015-11-12
 */
public interface LeaveRecordService {

	/**
	 * 添加请假记录
	 * @param leaveRecordData 请假信息
	 * @param userId 当前登录人ID
	 * @return 操作结果
	 */
	public String addLeaveRecord(String leaveRecordData, String userId);

}
