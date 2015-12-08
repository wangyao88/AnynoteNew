package com.sxkl.menu.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sxkl.menu.dao.MenuDao;
import com.sxkl.menu.model.ChildrenMenu;
import com.sxkl.menu.model.ParentMenu;

@Repository("menuDaoImpl")
public class MenuDaoImpl extends HibernateDaoSupport implements MenuDao{
	
	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory){  
        super.setSessionFactory(sessionFactory);  
    }

	public void addChildrenMenu(ChildrenMenu childrenMenu) {
		this.getHibernateTemplate().save(childrenMenu);
	}

	public void addParentMenu(ParentMenu parentMenu) {
		this.getHibernateTemplate().save(parentMenu);
	}

	@SuppressWarnings("unchecked")
	public List<ParentMenu> getParentMenu() {
		String hql = "from ParentMenu p where p.menu.delflag = ?";
		List<ParentMenu> parentMenus = (List<ParentMenu>) this.getHibernateTemplate().find(hql,new Object[]{"0"});
		if(parentMenus == null){
			return new ArrayList<ParentMenu>();
		}
		return parentMenus;
	}

}
