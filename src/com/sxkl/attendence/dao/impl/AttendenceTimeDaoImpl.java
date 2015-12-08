package com.sxkl.attendence.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sxkl.attendence.dao.AttendenceTimeDao;
import com.sxkl.attendence.model.AttendenceTime;
import com.sxkl.attendence.model.Holidy;

@Repository("attendenceTimeDaoImpl")
public class AttendenceTimeDaoImpl implements AttendenceTimeDao{

	  @Autowired
      private SessionFactory sessionFactory;

	public AttendenceTime getStanderAttendenceTimeInfo(String hql) {
//		this.getCurrentSession().l
		return null;
	}
	
	private Session getCurrentSession(){
		return this.sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Holidy> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<Holidy> find(String hql, Object[] params, int start, int limit) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.setFirstResult(start).setMaxResults(limit).list();
		
	}

	public AttendenceTime load(Class<AttendenceTime> class1, String atId) {
		AttendenceTime attendenceTime = (AttendenceTime) this.getCurrentSession().load(class1, atId);
		return attendenceTime;
	}

	public void saveHolidy(Holidy holidy) throws Exception{
		this.getCurrentSession().save(holidy);
		this.getCurrentSession().flush();
	}

	public void updateHolidy(Holidy hody) throws Exception{
		this.getCurrentSession().update(hody);
		this.getCurrentSession().flush();
	}

	public Holidy findHolidyById(String hId) {
		Holidy holidy = (Holidy) this.getCurrentSession().get(Holidy.class, hId);
		return holidy;
	}

	public int deleteHolidyInfoByIds(String hIds) throws Exception{
		String[] hIdsArr = hIds.split(",");
		int length  = hIdsArr.length;
		StringBuffer hql = new StringBuffer("delete from Holidy h where ");
		for(int i = 0; i < length; i++){
			if(i == 0){
				hql.append(" h.hId = ? ");
				continue;
			}
			hql.append(" or h.hId = ? ");
		}
		Query query = this.getCurrentSession().createQuery(hql.toString());
		for(int i = 0; i < length; i++){
			query.setParameter(i, hIdsArr[i]);
		}
		int rows = query.executeUpdate();
		return rows;
	}

	public AttendenceTime findAttendenceTime() throws Exception {
		String hql = "from AttendenceTime a";
		return (AttendenceTime) this.getCurrentSession().createQuery(hql).list().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Holidy> findAllHolidys() {
		String hql = "from Holidy";
		return this.getCurrentSession().createQuery(hql).list();
	}
	  
}
