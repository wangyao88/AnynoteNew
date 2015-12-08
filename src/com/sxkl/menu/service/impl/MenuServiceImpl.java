package com.sxkl.menu.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sxkl.common.utils.DateUtils;
import com.sxkl.common.utils.IDUtils;
import com.sxkl.menu.dao.MenuDao;
import com.sxkl.menu.model.ChildrenMenu;
import com.sxkl.menu.model.Menu;
import com.sxkl.menu.model.ParentMenu;
import com.sxkl.menu.service.MenuService;

@Service("menuServiceImpl")
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	@Qualifier("menuDaoImpl")
	private MenuDao MenuDaoImpl;

	public void addChildrenMenu(ChildrenMenu childrenMenu) {
		this.MenuDaoImpl.addChildrenMenu(childrenMenu);
	}

	public void addParentMenu(ParentMenu parentMenu) {
		// TODO Auto-generated method stub
		
	}

	public List<ParentMenu> getParentMenu() {
		List<ParentMenu> parentMenus = this.MenuDaoImpl.getParentMenu();
		return parentMenus;
	}
	
	public void initMune(){
		ParentMenu parentMenu = new ParentMenu();
		parentMenu.setParentMenuId(IDUtils.getUUID());
		Menu pmenu = new Menu();
		pmenu.setCreateTime(new Date());
		pmenu.setDelflag("0");
		pmenu.setIsLeaf("0");
		pmenu.setMenuName("系统管理");
		pmenu.setMenuLink("#");
		parentMenu.setMenu(pmenu);
		
		Set<ChildrenMenu> childrenMenus = new HashSet<ChildrenMenu>();
		ChildrenMenu childrenMenu1 = new ChildrenMenu();
		childrenMenu1.setChildrenMenuId(IDUtils.getUUID());
		childrenMenu1.setParentMenu(parentMenu);
		Menu cmenu1 = new Menu();
		cmenu1.setCreateTime(new Date());
		cmenu1.setDelflag("0");
		cmenu1.setIsLeaf("1");
		cmenu1.setMenuLink("#");
		cmenu1.setMenuName("菜单管理");
		childrenMenu1.setMenu(cmenu1);
		childrenMenus.add(childrenMenu1);
		
		ChildrenMenu childrenMenu2 = new ChildrenMenu();
		childrenMenu2.setChildrenMenuId(IDUtils.getUUID());
		childrenMenu2.setParentMenu(parentMenu);
		Menu cmenu2 = new Menu();
		cmenu2.setCreateTime(new Date());
		cmenu2.setDelflag("0");
		cmenu2.setIsLeaf("1");
		cmenu2.setMenuLink("#");
		cmenu2.setMenuName("用户管理");
		childrenMenu2.setMenu(cmenu2);
		childrenMenus.add(childrenMenu2);
		
		ChildrenMenu childrenMenu3 = new ChildrenMenu();
		childrenMenu3.setChildrenMenuId(IDUtils.getUUID());
		childrenMenu3.setParentMenu(parentMenu);
		Menu cmenu3 = new Menu();
		cmenu3.setCreateTime(new Date());
		cmenu3.setDelflag("0");
		cmenu3.setIsLeaf("1");
		cmenu3.setMenuLink("#");
		cmenu3.setMenuName("角色管理");
		childrenMenu3.setMenu(cmenu3);
		childrenMenus.add(childrenMenu3);
		
		ChildrenMenu childrenMenu4 = new ChildrenMenu();
		childrenMenu4.setChildrenMenuId(IDUtils.getUUID());
		childrenMenu4.setParentMenu(parentMenu);
		Menu cmenu4 = new Menu();
		cmenu4.setCreateTime(new Date());
		cmenu4.setDelflag("0");
		cmenu4.setIsLeaf("1");
		cmenu4.setMenuLink("#");
		cmenu4.setMenuName("资源管理");
		childrenMenu4.setMenu(cmenu4);
		childrenMenus.add(childrenMenu4);
		
		ChildrenMenu childrenMenu5 = new ChildrenMenu();
		childrenMenu5.setChildrenMenuId(IDUtils.getUUID());
		childrenMenu5.setParentMenu(parentMenu);
		Menu cmenu5 = new Menu();
		cmenu5.setCreateTime(new Date());
		cmenu5.setDelflag("0");
		cmenu5.setIsLeaf("1");
		cmenu5.setMenuLink("#");
		cmenu5.setMenuName("首页模块定制");
		childrenMenu5.setMenu(cmenu5);
		childrenMenus.add(childrenMenu5);
		
		ChildrenMenu childrenMenu6 = new ChildrenMenu();
		childrenMenu6.setChildrenMenuId(IDUtils.getUUID());
		childrenMenu6.setParentMenu(parentMenu);
		Menu cmenu6 = new Menu();
		cmenu6.setCreateTime(new Date());
		cmenu6.setDelflag("0");
		cmenu6.setIsLeaf("1");
		cmenu6.setMenuLink("#");
		cmenu6.setMenuName("缓存管理");
		childrenMenu6.setMenu(cmenu6);
		childrenMenus.add(childrenMenu6);
		
		ChildrenMenu childrenMenu7 = new ChildrenMenu();
		childrenMenu7.setChildrenMenuId(IDUtils.getUUID());
		childrenMenu7.setParentMenu(parentMenu);
		Menu cmenu7 = new Menu();
		cmenu7.setCreateTime(new Date());
		cmenu7.setDelflag("0");
		cmenu7.setIsLeaf("1");
		cmenu7.setMenuLink("#");
		cmenu7.setMenuName("数据库管理");
		childrenMenu7.setMenu(cmenu7);
		childrenMenus.add(childrenMenu7);
		
		ChildrenMenu childrenMenu8 = new ChildrenMenu();
		childrenMenu8.setChildrenMenuId(IDUtils.getUUID());
		childrenMenu8.setParentMenu(parentMenu);
		Menu cmenu8 = new Menu();
		cmenu8.setCreateTime(new Date());
		cmenu8.setDelflag("0");
		cmenu8.setIsLeaf("1");
		cmenu8.setMenuLink("#");
		cmenu8.setMenuName("系统配置");
		childrenMenu8.setMenu(cmenu8);
		childrenMenus.add(childrenMenu8);
		
		parentMenu.setChildrenMenu(childrenMenus);
		
		this.MenuDaoImpl.addParentMenu(parentMenu);
	}

}
