package com.sxkl.attendence.dao;

import java.util.List;

import com.sxkl.attendence.model.AttendenceTimeRecord;

/**
 * 考勤记录持久层接口
 * @author wangyao
 * @date 2015-11-03
 */
public interface AttendenceTimeRecordDao {

	/**
	 * 分页查询考勤记录
	 * @param start 开始条数
	 * @param limit 一页条数
	 * @param userId 当前登录人ID
	 * @return 考勤记录
	 */
	public List<AttendenceTimeRecord> getAttendenceTimeRecordsPage(int start,int limit, String userId);

	/**
	 * 获取考勤记录总数
	 * @param userId 当前登录人ID
	 * @return 考勤记录总数
	 * @throws Exception 
	 */
	public int getAttendenceTimeRecordNum(String userId) throws Exception;

	/**
	 * 添加考勤记录
	 * @param attendenceTimeRecord 考勤记录信息
	 * @throws Exception 
	 */
	public void addAttendenceTimeRecord(AttendenceTimeRecord attendenceTimeRecord) throws Exception;

	/**
	 * 根据模板查询对象
	 * @param queryBean
	 * @return 打卡记录
	 */
	public AttendenceTimeRecord findAttendenceTimeRecordByBean(AttendenceTimeRecord queryBean);

	/**
	 * 更新
	 * @param attendenceTimeRecord
	 */
	public void updateAttendenceTimeRecord(AttendenceTimeRecord attendenceTimeRecord);

	/**
	 * 获取打卡记录集合
	 * @param attendenceTimeRecord 查询条件
	 * @return
	 */
	public List<AttendenceTimeRecord> findAttendenceTimeRecordsByBean(String hql, String userId, String date);

}
