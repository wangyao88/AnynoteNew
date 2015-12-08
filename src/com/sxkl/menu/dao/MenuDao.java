package com.sxkl.menu.dao;

import java.util.List;

import com.sxkl.menu.model.ChildrenMenu;
import com.sxkl.menu.model.ParentMenu;

/**
 * 菜单持久层接口
 * @author wangyao
 * @date 2015-12-02
 */
public interface MenuDao {
	
    public void addParentMenu(ParentMenu parentMenu);
	
	public void addChildrenMenu(ChildrenMenu childrenMenu);
	
	public List<ParentMenu> getParentMenu();

}
