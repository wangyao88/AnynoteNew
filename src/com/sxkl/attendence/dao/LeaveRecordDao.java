package com.sxkl.attendence.dao;

import java.util.List;

import com.sxkl.attendence.model.LeaveRecord;

/**
 * 请假持久层接口
 * @author wangyao
 * @date 2015-11-12
 */
public interface LeaveRecordDao {

	/**
	 * 添加请假记录
	 * @param leaveRecord 请假记录
	 */
	public void addLeaveRecord(LeaveRecord leaveRecord);

	/**
	 * 获取当前登陆人所有的请假记录
	 * @param userId 当前登陆人ID
	 * @return 当前登陆人所有的请假记录
	 */
	public List<LeaveRecord> findAllLeaveRecord(String userId);

}
