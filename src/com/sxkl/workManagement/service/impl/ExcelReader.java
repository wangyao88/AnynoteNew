package com.sxkl.workManagement.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sxkl.common.utils.DateUtils;
import com.sxkl.common.utils.IDUtils;
import com.sxkl.workManagement.model.WorkItem;
import com.sxkl.workManagement.model.WorkMain;

/**
 * 读取Excel 97~2003 xls格式 /2007~ xlsx格式
 * 
 * @author ZhangLiKun
 * @mail likun_zhang@yeah.net
 * @date 2013-5-11
 */
public class ExcelReader {
	
	private static final Integer NAME_SHEET_ROW_NUM = 0;
	private static final Integer NAME_SHEET_CELL_NUM = 0;
	private static final Integer ACHIEVEMENT_SHEET_ROW_NUM = 3;
	private static final Integer ACHIEVEMENT_SHEET_CELL_NUM = 9;
	private static final Integer REMARK_SHEET_ROW_NUM = 3;
	private static final Integer REMARK_SHEET_CELL_NUM = 10;

	/**
	 * 创建工作簿对象
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @date 2013-5-11
	 */
	public static final Workbook createWbByFile(String filePath) throws IOException {
		if (StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException("参数错误!!!");
		}
		if (filePath.trim().toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(new FileInputStream(filePath));
		} else if (filePath.trim().toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(new FileInputStream(filePath));
		} else {
			throw new IllegalArgumentException("不支持除：xls/xlsx以外的文件格式!!!");
		}
	}
	
	public static final Workbook createWbByInputStream(ServletInputStream sis) throws IOException {
		return new HSSFWorkbook(sis);
	}

	public static final Sheet getSheet(Workbook wb, String sheetName) {
		return wb.getSheet(sheetName);
	}

	public static final Sheet getSheet(Workbook wb, int index) {
		return wb.getSheetAt(index);
	}

	public static final List<Object[]> listFromSheet(Sheet sheet) {

		int rowTotal = sheet.getPhysicalNumberOfRows();

		List<Object[]> list = new ArrayList<Object[]>();
		for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null)
				continue;
			// 不能用row.getPhysicalNumberOfCells()，可能会有空cell导致索引溢出
			// 使用row.getLastCellNum()至少可以保证索引不溢出，但会有很多Null值，如果使用集合的话，就不说了
			Object[] cells = new Object[row.getLastCellNum()];
			for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
				Cell cell = row.getCell(c);
				if (cell == null)
					continue;
				cells[c] = getValueFromCell(cell);
			}
			list.add(cells);
		}

		return list;
	}

	/**
	 * 获取单元格内文本信息
	 * 
	 * @param cell
	 * @return
	 * @date 2013-5-8
	 */
	public static final String getValueFromCell(Cell cell) {
		if (cell == null) {
			return null;
		}
		String value = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) { // 如果是日期类型
				value = new SimpleDateFormat("yyyy/MM/dd").format(cell.getDateCellValue());
			} else
				value = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			// 用数字方式获取公式结果，根据值判断是否为日期类型
			double numericValue = cell.getNumericCellValue();
			if (HSSFDateUtil.isValidExcelDate(numericValue)) { // 如果是日期类型
				value = new SimpleDateFormat("yyyy/MM/dd").format(cell.getDateCellValue());
			} else
				value = String.valueOf(numericValue);
			break;
		case Cell.CELL_TYPE_BLANK: // 空白
			value = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR: // Error，返回错误码
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			value = StringUtils.EMPTY;
			break;
		}
		// 使用[]记录坐标
//		return value + "[" + cell.getRowIndex() + "," + cell.getColumnIndex() + "]";
		return value;
	}
	
	public static String getWorkMainName(Sheet sheet) throws Exception{
		Row row = sheet.getRow(NAME_SHEET_ROW_NUM);
		Cell cell = row.getCell(NAME_SHEET_CELL_NUM);
		String name = getValueFromCell(cell);
		name = name.replaceAll(" ", "");
        name = name.substring(3, name.indexOf("工作"));
		return name;
	}
	
	public static String getWorkMainYear(Sheet sheet) throws Exception{
		Row row = sheet.getRow(NAME_SHEET_ROW_NUM);
		Cell cell = row.getCell(NAME_SHEET_CELL_NUM);
		String year = getValueFromCell(cell);
		year = year.replaceAll(" ", "");
		year = year.substring(year.indexOf("年度：")+3, year.indexOf("年度：")+8);
		return year;
	}
	
	public static String getWorkMainAchievement(Sheet sheet) throws Exception{
		Row row = sheet.getRow(ACHIEVEMENT_SHEET_ROW_NUM);
		Cell cell = row.getCell(ACHIEVEMENT_SHEET_CELL_NUM);
		String achievement = getValueFromCell(cell);
		return achievement;
	}
	
	public static String getWorkMainRemark(Sheet sheet) throws Exception{
		Row row = sheet.getRow(REMARK_SHEET_ROW_NUM);
		Cell cell = row.getCell(REMARK_SHEET_CELL_NUM);
		String remark = getValueFromCell(cell);
		return remark;
	}
	
	public static Set<WorkItem> getWormMainWorkItem(Sheet sheet,WorkMain workMain){
		int rowlastNum =  sheet.getLastRowNum();
		Set<WorkItem> workItems = new HashSet<WorkItem>();
		for(int i = 3; i <= rowlastNum; i++){
			Row row = sheet.getRow(i);
			WorkItem workItem = new WorkItem();
			workItem.setWiId(IDUtils.getUUID());
			Cell cell = row.getCell(0);
			workItem.setWiDate(DateUtils.formatStr2Date(getValueFromCell(cell), "yyyy/MM/dd"));
			cell = row.getCell(3);
			workItem.setWiTitile(getValueFromCell(cell));
			cell = row.getCell(4);
			workItem.setWiImportantLevel(getValueFromCell(cell));
			cell = row.getCell(5);
			workItem.setWiEmergentLevel(getValueFromCell(cell));
			cell = row.getCell(6);
			workItem.setWiIsTemporary(getValueFromCell(cell));
			cell = row.getCell(7);
			workItem.setWiCostTime(Double.valueOf(getValueFromCell(cell)).intValue());
			cell = row.getCell(8);
			workItem.setWiFinishRate(getValueFromCell(cell));
			workItem.setWorkMain(workMain);
			workItems.add(workItem);
		}
		return workItems;
	}
	
	public static WorkMain getWorkMain(Sheet sheet) throws Exception{
		WorkMain workMain = new WorkMain();
		workMain.setWmId(IDUtils.getUUID());
		workMain.setWmAchievement(getWorkMainAchievement(sheet));
		workMain.setWmName(getWorkMainName(sheet));
		workMain.setWmRemark(getWorkMainRemark(sheet));
		workMain.setWmYear(getWorkMainYear(sheet));
//		workMain.setWorkItems(workItems);
		return workMain;
	}
	
}