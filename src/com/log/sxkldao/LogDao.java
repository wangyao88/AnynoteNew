package com.log.sxkldao;

import com.log.sxkl.model.Log;

/**
 * 操作日志持久层接口
 * @author wangyao
 * @date 2015-11-19
 */
public interface LogDao {
	
	/**
	 * 添加操作日志
	 * @param log 操作日志
	 * @throws Exception
	 */
	public void addLog(Log log) throws Exception;

}
