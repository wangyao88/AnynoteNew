package com.sxkl.attendence.service;

import net.sf.json.JSONObject;

/**
 * 考勤服务接口
 * @author wangyao
 * @date 2015-10-29
 */
public interface AttendenceTimeService {

	/**
	 * 获取考勤时间标准信息
	 * @return 考勤时间标准信息
	 */
	public JSONObject getStanderAttendenceTimeInfo();

	/**
	 * 获取节假日信息
	 * @param limit 每页显示条数
	 * @param start 当前页开始条数
	 * @param atId 考勤时间ID
	 * @return 节假日信息
	 */
	public String getStanderAttendenceTimeHolidyInfo(int start, int limit, String atId);

	/**
	 * 保存节假日对象
	 * @param holidy 节假日对象
	 * @param atId 考勤时间对象
	 * @return
	 * @throws Exception 
	 */
	public String saveHolidyInfo(String holidy, String atId);

	/**
	 * 根据Id查询节假日信息
	 * @param hId 节假日ID
	 * @return 节假日信息
	 */
	public String getStanderAttendenceTimeHolidyInfoByhId(String hId);

	/**
	 * 根据Id删除节假日信息
	 * @param hIds 节假日ID
	 * @return 删除结果
	 */
	public String deleteHolidyInfoByIds(String hIds);

}
