package com.sxkl.loginHistory.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sxkl.common.dao.BaseDAO;
import com.sxkl.common.utils.DateUtils;
import com.sxkl.common.utils.IDUtils;
import com.sxkl.common.utils.IPUtils;
import com.sxkl.common.utils.JsonConfigUtils;
import com.sxkl.loginHistory.dao.LoginHistoryDao;
import com.sxkl.loginHistory.model.LoginHistory;
import com.sxkl.loginHistory.service.LoginHistoryService;

@Service("loginHistoryServiceImpl")
public class LoginHistoryServiceImpl implements LoginHistoryService{
	
	@Autowired
	@Qualifier("loginHistoryDaoImpl")
	private LoginHistoryDao loginHistoryDaoImpl;
	
	@Autowired
	@Qualifier("baseDaoImpl")
	private BaseDAO<LoginHistory> baseDao;
	
	public String addLoginHistory(String userId) {
		String loginIP = IPUtils.getCurrentIp();
		String id = IDUtils.getUUID().substring(0,32);
		String loginUserName = "";
		if(!StringUtils.isEmpty(userId)){
			loginUserName = loginHistoryDaoImpl.getUserNameByUserId(userId);
		}
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setId(id);
		loginHistory.setLoginUserId(userId);
		loginHistory.setLoginUserName(loginUserName);
		loginHistory.setLoginTime(new Date());
		loginHistory.setLoginIP(loginIP);
		loginHistoryDaoImpl.addLoginHistory(loginHistory);
		return id;
	}

	public void updateLoginOutTime(String loginHistoryId) {
		String hql = "from LoginHistory l where l.id = ?";
		List<LoginHistory> loginHistories = this.baseDao.find(hql, new String[]{loginHistoryId});
		if(loginHistories.size() > 0){
			LoginHistory loginHistory = loginHistories.get(0);
			Date loginOutTime = new Date();
			loginHistory.setLoginOut(loginOutTime);
			long loginSumTime = loginOutTime.getTime() - loginHistory.getLoginTime().getTime();
			loginHistory.setLoginSumTime(loginSumTime);
			loginHistoryDaoImpl.setLoginOutTime(loginHistory);
		}
	}

	@SuppressWarnings("unchecked")
	public String getLoginHistory(int pageNo,int pageSize) {
		String hql = "from LoginHistory lh order by lh.loginTime desc";
		List<LoginHistory> loginHistories = baseDao.find(hql,new ArrayList(),pageNo,pageSize);
		String hqlNum = "select new LoginHistory(id) from LoginHistory";
		List<LoginHistory> loginHistorySize = baseDao.find(hqlNum,new ArrayList());
		JSONObject json = new JSONObject();
		JSONArray data = JSONArray.fromObject(loginHistories,JsonConfigUtils.getDateJsonConfig("yyyy-MM-dd HH:mm:ss.SS"));
		json.put("datas", data);
		json.put("results", loginHistorySize.size());
		json.put("start", pageNo);
		json.put("limit", pageSize);
		return json.toString();
	}

	public String getLoginHistoryInfos(String userId) {
//		登录人
//		登陆次数
//		常用IP
//		登陆总时长
		List<Map<String,String>> infos = this.loginHistoryDaoImpl.getLoginHistoryInfos(userId);
		Map<String,String> dataMap = infos.get(0);
		JSONObject data = new JSONObject();
		data.put("loginUserName", dataMap.get("userName"));
		data.put("loginSumNum", dataMap.get("num"));
		data.put("loginIP", dataMap.get("ip"));
		Object sumTime = dataMap.get("sumTime");
		long time = Long.valueOf(sumTime.toString());
		String sunTime = DateUtils.formatDuring(time);
		data.put("loginSumTime", sunTime);
		return data.toString();
	}

	public String getCurrentMonthLoginHistory(String userId) {
		List<LoginHistory> currentMonthLoginHistory = this.loginHistoryDaoImpl.getCurrentMonthLoginHistory(userId);
		JSONObject json = new JSONObject();
		JSONArray data = JSONArray.fromObject(currentMonthLoginHistory,JsonConfigUtils.getDateJsonConfig("yyyy-MM-dd"));
		json.put("datas", data);
		return json.toString();
	}

}
