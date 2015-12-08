package com.sxkl.workManagement.test;

import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.sxkl.common.utils.IDUtils;
import com.sxkl.workManagement.model.WorkItem;
import com.sxkl.workManagement.model.WorkMain;

public class WorkManageTest {
	
	private String userId = "admin";
	private String wmId = IDUtils.getUUID();
	
	@Test
	public void excelImportTet() throws Exception{
//		 Workbook wb = ExcelReader.createWb("F:\\WokeCache\\借贷宝\\日报\\20151116-20151122-第六周\\20151116-王曜-工作日志.xls") ;
//         // 获取Workbook中Sheet个数
//         int sheetTotal = wb.getNumberOfSheets() ;
//         // 获取Sheet
//         Sheet sheet = ExcelReader.getSheet(wb, 0) ;
         // 遍历Sheet
//         List<Object[]> list = ExcelReader.listFromSheet(sheet) ;
//         for(Object[] obj : list){
//        	 for(Object o : obj){
//        		 System.out.println(o.toString());
//        	 }
//         }
//         WorkMain workMain = ExcelReader.getWorkMain(sheet);
//         Set<WorkItem> workItems = ExcelReader.getWormMainWorkItem(sheet, workMain);
//         workMain.setWorkItems(workItems);
         
         String str = "180";
         int num = Integer.valueOf(str);
         System.out.println(num);
	}

}
