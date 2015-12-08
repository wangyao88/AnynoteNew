package com.sxkl.loginHistory.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sxkl.common.utils.DateUtils;
import com.sxkl.loginHistory.dao.LoginHistoryDao;
import com.sxkl.loginHistory.model.LoginHistory;

@Repository("loginHistoryDaoImpl")
public class LoginHistoryDaoImpl implements LoginHistoryDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void addLoginHistory(LoginHistory loginHistory) {
		
//		this.getHibernateTemplate().save(loginHistory);
		this.getSessionFactory().getCurrentSession().save(loginHistory);
		this.getSessionFactory().getCurrentSession().flush();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public String getUserNameByUserId(String userId) {
		String sql = "select USER_NAME from an_user where USER_ID=?";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, userId);
		List<String> userNames = query.list();
		return userNames.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<LoginHistory> getLoginHistoryWithoutLoginOutTime() {
		String hql = "from LoginHistory lh where lh.loginOut is null";
		Query query = this.getCurrentSession().createQuery(hql);
		List<LoginHistory> loginHistories = query.list();
		return loginHistories;
	}

	@SuppressWarnings("unchecked")
	public List<LoginHistory> getLoginHistory() {
		String hql = "from LoginHistory";
		Query query = this.getCurrentSession().createQuery(hql);
		List<LoginHistory> loginHistories = query.list();
		return loginHistories;
	}

	public void setLoginOutTime(LoginHistory loginHistory) {
		this.getCurrentSession().saveOrUpdate(loginHistory);
		this.getCurrentSession().flush();
	}
	
	private Session getCurrentSession(){
		return this.getSessionFactory().getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getLoginHistoryInfos(String userId) {
//		select * from 
//			(select lh_login_user_name as userName,count(1) as num from an_login_history lh where lh.lh_login_user_id='admin' group by lh_login_user_name) A,
//			(select top 1 lh_login_ip as ip from an_login_history lh  where lh.lh_login_user_id='admin' group by lh.lh_login_ip order by count(lh_login_user_id) desc)B,
//			(select sum(lh.lh_login_sum_time) as sum_time from an_login_history lh where lh.lh_login_user_id='admin') C
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ")
		   .append("(select lh_login_user_name as userName,count(1) as num from an_login_history lh where lh.lh_login_user_id=? group by lh_login_user_name) A,")
		   .append("(select top 1 lh_login_ip as ip from an_login_history lh  where lh.lh_login_user_id=? group by lh.lh_login_ip order by count(lh_login_user_id) desc)B,")
		   .append("(select sum(lh.lh_login_sum_time) as sumTime from an_login_history lh where lh.lh_login_user_id=?) C");
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter(0, userId);
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, String>> infos = query.list();
		return infos;
	}

	@SuppressWarnings("unchecked")
	public List<LoginHistory> getCurrentMonthLoginHistory(String userId) {
//		String hql = "select new LoginHistory(loginTime,loginSumTime) from LoginHistory lh where lh.loginUserId = ? order by lh.loginTime asc";
//		Query query = this.getCurrentSession().createQuery(hql);
//		query.setParameter(0, userId);
//		List<LoginHistory> list = query.list();
		
		//String sql = "select day(lh.lh_login_time) as loginTime,sum(lh.lh_login_sum_time) as loginSumTime from an_login_history lh where lh.lh_login_user_id='admin' and month(lh.lh_login_time)='1' and year(lh.lh_login_time)='2015' group by day(lh.lh_login_time)";
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		StringBuffer sql = new StringBuffer();
		sql.append("select day(lh.lh_login_time) as loginTime, sum(lh.lh_login_sum_time) as loginSumTime")
		   .append("  from an_login_history lh")
		   .append(" where lh.lh_login_user_id = ?")
		   .append("   and month(lh.lh_login_time) = ?")
		   .append("   and year(lh.lh_login_time) = ?")
		   .append(" group by day(lh.lh_login_time)")
		   .append("order by  day(lh.lh_login_time) asc");
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter(0, userId);
		query.setParameter(1, month);
		query.setParameter(2, year);
		List<Object[]> temp = query.list();
		List<LoginHistory> list = new ArrayList<LoginHistory>();
		long rate = 1000*60;
		for(Object[] obj : temp){
			LoginHistory loginHistory = new LoginHistory();
			String loginTime = year + "-" + month + "-" + obj[0].toString();
			loginHistory.setLoginTime(DateUtils.formatStr2Date(loginTime,"yyyy-MM-dd"));
			loginHistory.setLoginSumTime(Long.valueOf(obj[1].toString())/rate);
			list.add(loginHistory);
			loginHistory = null;
		}
		return list;
	}
	
	
//	@Resource
//	public void setSessionFactory0(SessionFactory sessionFactory){  
//	   super.setSessionFactory(sessionFactory);  
//	}

}
