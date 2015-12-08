package com.sxkl.common.listner;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.sxkl.common.constant.GlobalConstants;
import com.sxkl.menu.model.ParentMenu;
import com.sxkl.menu.service.MenuService;

@Service
public class StartAddDataListener implements ApplicationListener<ContextRefreshedEvent> {

	private Logger logger = LoggerFactory.getLogger(StartAddDataListener.class);
	
	@Autowired
	@Qualifier("menuServiceImpl")
	private MenuService menuServiceImpl;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		if (event.getApplicationContext().getParent() == null){// root application context 没有parent，他就是老大.
//			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
//			System.out.println("\n\n\n\n\n______________\n\n\n加载了\n\n_________\n\n");
//		}
//
//		// 或者下面这种方式
//		if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
//			System.out.println("\n\n\n_________\n\n加载一次的 \n\n ________\n\n\n\n");
//		}
//		menuServiceImpl.initMune();
//		List<ParentMenu> parentMenus = this.menuServiceImpl.getParentMenu();
//		if(parentMenus != null && !parentMenus.isEmpty()){
//			GlobalConstants.S_PARENT_MENUS = parentMenus;
//			logger.info("系统父菜单共有" + parentMenus.size() + "个");
//			logger.info("size of GlobalConstants.S_PARENT_MENUS is " + GlobalConstants.S_PARENT_MENUS.size());
//		}
		logger.info("spring 容器初始化完成");
		

	}

}
