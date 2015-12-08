package com.sxkl.attendence.dao;

import java.util.List;

import com.sxkl.attendence.model.AttendenceTime;
import com.sxkl.attendence.model.Holidy;

/**
 * 考勤时间持久层接口
 * @author wangyao
 * @date 2015-10-30
 */
public interface AttendenceTimeDao {

	/**
	 * 获取考勤时间
	 * @param hql 
	 * @return
	 */
	public AttendenceTime getStanderAttendenceTimeInfo(String hql);

	/**
	 * 查询对象
	 * @param hql
	 * @return
	 */
	public List<Holidy> find(String hql);

	/**
	 * 分页按条件查询
	 * @param hql
	 * @param params 条件
	 * @param start  开始
	 * @param limit  页数
	 * @return
	 */
	public List<Holidy> find(String hql, Object[] params, int start, int limit);

	/**
	 * 加载考勤时间对象
	 * @param class1
	 * @param atId
	 * @return
	 */
	public AttendenceTime load(Class<AttendenceTime> class1, String atId);

	/**
	 * 保存节假日对象
	 * @param holidy 节假日对象
	 * @throws Exception 
	 */
	public void saveHolidy(Holidy holidy) throws Exception;

	/**
	 * 更新节假日对象
	 * @param hody 节假日对象
	 * @throws Exception 
	 */
	public void updateHolidy(Holidy hody) throws Exception;

	/**
	 * 根据Id查询对象
	 * @param class1 对象类型
	 * @param hId 对象ID
	 * @return 待查询对象
	 */
	public Holidy findHolidyById(String hId);

	/**
	 * 根据Id删除节假日对象
	 * @param hIds 节假日对象
	 * @throws Exception 
	 */
	public int deleteHolidyInfoByIds(String hIds) throws Exception;

	/**
	 * 查询考勤时间
	 * @return 考勤时间信息
	 */
	public AttendenceTime findAttendenceTime() throws Exception;

	/**
	 * 获取所有节假日
	 * @return 所有节假日
	 */
	public List<Holidy> findAllHolidys();

}
