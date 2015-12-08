package com.log.sxkl.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.log.sxkl.model.Log;
import com.log.sxkldao.LogDao;

@Repository("logDaoImpl")
public class LogDaoImpl extends HibernateDaoSupport implements LogDao{

	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory){  
        super.setSessionFactory(sessionFactory);  
    }
	
	public void addLog(Log log) throws Exception {
		this.getHibernateTemplate().save(log);
	}

}
