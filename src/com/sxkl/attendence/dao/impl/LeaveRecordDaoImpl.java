package com.sxkl.attendence.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sxkl.attendence.dao.LeaveRecordDao;
import com.sxkl.attendence.model.LeaveRecord;

@Repository("leaveRecordDaoImpl")
public class LeaveRecordDaoImpl extends HibernateDaoSupport implements LeaveRecordDao{

	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory){  
        super.setSessionFactory(sessionFactory);  
    }

	public void addLeaveRecord(LeaveRecord leaveRecord) {
		this.getHibernateTemplate().save(leaveRecord);
	}

	@SuppressWarnings("unchecked")
	public List<LeaveRecord> findAllLeaveRecord(String userId) {
		String hql = "from LeaveRecord l where l.userId = ?";
		return (List<LeaveRecord>) this.getHibernateTemplate().findByNamedParam(hql, "userId", userId);
	}
}
