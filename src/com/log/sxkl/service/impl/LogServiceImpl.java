package com.log.sxkl.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.log.sxkl.model.Log;
import com.log.sxkl.service.LogService;
import com.log.sxkldao.LogDao;

@Service("logServiceImpl")
public class LogServiceImpl implements LogService{
	
	@Autowired
	@Qualifier("logDaoImpl")
	private LogDao logDaoImpl;
	private Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

	public void addLog(Log log) {
		try {
			this.logDaoImpl.addLog(log);
			logger.info("添加操作日志成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加操作日志失败!:{}",e.getLocalizedMessage());
		}
	}

	public String getUserId() {
		return "admin";
	}

}
