package com.log.sxkl.service;

import com.log.sxkl.model.Log;

/**
 * 操作日志服务接口
 * @author wangyao
 * @date 2015-11-19
 */
public interface LogService {
	
	/**
	 * 天假操作日志
	 * @param log 操作日志
	 */
	public void addLog(Log log);
	
	/**
	 * 获取登陆人Id
	 * @return 登陆人Id
	 */
	public String getUserId();

}
