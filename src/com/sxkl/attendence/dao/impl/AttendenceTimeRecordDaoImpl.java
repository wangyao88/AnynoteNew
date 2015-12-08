package com.sxkl.attendence.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sxkl.attendence.dao.AttendenceTimeRecordDao;
import com.sxkl.attendence.model.AttendenceTimeRecord;
import com.sxkl.common.utils.PageNoUtil;

@Repository("attendenceTimeRecordDaoImpl")
public class AttendenceTimeRecordDaoImpl extends HibernateDaoSupport implements AttendenceTimeRecordDao{

	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory){  
        super.setSessionFactory(sessionFactory);  
    }
	
	@SuppressWarnings("unchecked")
	public List<AttendenceTimeRecord> getAttendenceTimeRecordsPage(final int start,final int limit, final String userId) {
		final String hql = "from AttendenceTimeRecord a where a.userId=? order by a.amOnTime desc";
		List<AttendenceTimeRecord> attendenceTimeRecords = (List<AttendenceTimeRecord>) getHibernateTemplate().execute(
				new HibernateCallback() {
			           public Object doInHibernate(Session session)throws HibernateException{
				            List list = PageNoUtil.getList(session,hql,start,limit,new Object[]{userId});
				            return list;
			           }
				});
		return attendenceTimeRecords;
	}

	@SuppressWarnings("unchecked")
	public int getAttendenceTimeRecordNum(String userId) throws Exception{
		int num = 0;
		String hql = "select count(a.atrId) from AttendenceTimeRecord a where a.userId=:userId";
		String paramName = "userId";
		List list = this.getHibernateTemplate().findByNamedParam(hql,paramName,userId);
		if(list != null && list.size() > 0){
			num = Integer.valueOf(list.get(0)+"");
		}
		return num;
	}

	public void addAttendenceTimeRecord(AttendenceTimeRecord attendenceTimeRecord) throws Exception{
		this.getHibernateTemplate().save(attendenceTimeRecord);
	}

	public AttendenceTimeRecord findAttendenceTimeRecordByBean(AttendenceTimeRecord queryBean) {
		List<AttendenceTimeRecord> list = this.getHibernateTemplate().findByExample(queryBean, 0, 1);
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	public void updateAttendenceTimeRecord(AttendenceTimeRecord attendenceTimeRecord) {
		this.getHibernateTemplate().update(attendenceTimeRecord);
	}

	@SuppressWarnings("unchecked")
	public List<AttendenceTimeRecord> findAttendenceTimeRecordsByBean(String hql, String userId, String date) {
		return (List<AttendenceTimeRecord>) this.getHibernateTemplate().find(hql,userId,date);
	}  
	
	

}
