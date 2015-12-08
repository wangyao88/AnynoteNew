package com.sxkl.workManagement.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sxkl.common.utils.PageNoUtil;
import com.sxkl.workManagement.dao.WorkManageDao;
import com.sxkl.workManagement.model.WorkItem;
import com.sxkl.workManagement.model.WorkMain;

@Repository("workManageDaoImpl")
public class WorkManageDaoImpl extends HibernateDaoSupport  implements WorkManageDao{

	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory){  
        super.setSessionFactory(sessionFactory);  
    }

	@SuppressWarnings("unchecked")
	public List<WorkMain> getWorkMainPage(final int start, final int limit, final String userId) {
		final String hql = "from WorkMain a where a.userId=? order by a.wmTitle desc";
		List<WorkMain> workMains = (List<WorkMain>) getHibernateTemplate().execute(
				new HibernateCallback() {
			           public Object doInHibernate(Session session)throws HibernateException{
				            List list = PageNoUtil.getList(session,hql,start,limit,new Object[]{userId});
				            return list;
			           }
				});
		return workMains;
	}

	@SuppressWarnings("unchecked")
	public int getWorkMainPageNum(final String userId) throws Exception{
		Integer count = 0;
		final String hql = "select count(a.wmId) from WorkMain a where a.userId = ?";
		Object num = this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				query.setParameter(0, userId);
				List list = query.list();
				if(list == null || list.size() == 0){
					return 0;
				}
				return list.get(0);
			}
		});
		count = Integer.valueOf(num.toString());
		return count;
	}

	public void addWorkMainByExcel(WorkMain workMain) throws Exception{
		this.getHibernateTemplate().save(workMain);
	}

	public WorkMain getWorkMainById(String wmId) {
		return this.getHibernateTemplate().get(WorkMain.class, wmId);
	}

	@SuppressWarnings("unchecked")
	public List<WorkItem> getWorkItemsById(String wmId) {
		String hql = "from WorkItem w where w.workMain.wmId = ?";
		return (List<WorkItem>) this.getHibernateTemplate().find(hql,wmId);
	}
}
