package com.sxkl.workManagement.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sxkl.common.utils.DateJsonValueProcessImpl;
import com.sxkl.common.utils.FileUtils;
import com.sxkl.workManagement.dao.WorkManageDao;
import com.sxkl.workManagement.model.WorkItem;
import com.sxkl.workManagement.model.WorkMain;
import com.sxkl.workManagement.service.WorkManageService;

@Service("workManageServiceImpl")
public class WorkManageServiceImpl implements WorkManageService{
	
	@Autowired
	@Qualifier("workManageDaoImpl")
	private WorkManageDao workManageDaoImpl;

	public String getWorkMainPage(int start, int limit, String userId) {
		JSONObject json = new JSONObject();
		try {
			List<WorkMain> workMains = workManageDaoImpl.getWorkMainPage(start,limit,userId);
			int workMainNum = workManageDaoImpl.getWorkMainPageNum(userId);
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[]{"workItems"});
			config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessImpl("yyyy/MM/dd"));
			JSONArray data = JSONArray.fromObject(workMains,config);
			json.put("datas", data);
			json.put("results", workMainNum);
			json.put("start", start);
			json.put("limit", limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public String addWorkMainByExcel(String userId, String title, String path) {
		try {
			Workbook wb = ExcelReader.createWbByFile(path) ;
	        Sheet sheet = ExcelReader.getSheet(wb, 0) ;
			WorkMain workMain = ExcelReader.getWorkMain(sheet);
			workMain.setUserId(userId);
			workMain.setWmTitle(title);
	        Set<WorkItem> workItems = ExcelReader.getWormMainWorkItem(sheet, workMain);
	        workMain.setWorkItems(workItems);
	        this.workManageDaoImpl.addWorkMainByExcel(workMain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("path", path);
		return json.toString();
	}

	public void deleteExcel(String path) {
		try {
			FileUtils.delete(path);
		} catch (Exception e) {
			System.out.println("删除文件失败!--" + e.getLocalizedMessage());
		}
	}

	public String getWorkMainById(String wmId) {
		WorkMain workMain = this.workManageDaoImpl.getWorkMainById(wmId);
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"workItems"});
		JSONObject json = JSONObject.fromObject(workMain, config);
		return json.toString();
	}

	public String getWorkItemsById(String wmId) {
		List<WorkItem> workItems = this.workManageDaoImpl.getWorkItemsById(wmId);
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"workMain"});
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessImpl("yyyy/MM/dd"));
		JSONArray data = JSONArray.fromObject(workItems,config);
		JSONObject json = new JSONObject();
		json.put("datas", data);
		json.put("results", workItems.size());
		return json.toString();
	}

}
