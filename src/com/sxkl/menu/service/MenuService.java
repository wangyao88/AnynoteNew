package com.sxkl.menu.service;

import java.util.List;

import com.sxkl.menu.model.ChildrenMenu;
import com.sxkl.menu.model.ParentMenu;

/**
 * 菜单服务接口
 * @author wangyao
 * @date 2015-12-02
 */
public interface MenuService {
	
	public void addParentMenu(ParentMenu parentMenu);
	
	public void addChildrenMenu(ChildrenMenu childrenMenu);
	
	public List<ParentMenu> getParentMenu();
	
	public void initMune();

}
