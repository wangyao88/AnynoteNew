package com.sxkl.easypoi.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EasyPoiTest {
	
	private List<MsgClient> list = new ArrayList<MsgClient>();
	private Workbook workbook = null;
	private Date start = null;
	
	@Before
	public void initData(){
		for (int i = 0; i < 25000; i++) {
			MsgClient client = new MsgClient();
			client.setBirthday(new Date());
			client.setClientName("小明" + i);
			client.setClientPhone("18797" + i);
			client.setCreateBy("jueyue");
			client.setId("1" + i);
			client.setRemark("测试" + i);
			MsgClientGroup group = new MsgClientGroup();
			group.setGroupName("测试" + i);
			client.setGroup(group);
			list.add(client);
		}
		start = new Date();
	}

	@Test
	public void simpleTest() throws Exception {
		ExportParams params = new ExportParams("2412312", "测试");
		//设置序号
	    params.setAddIndex(true);
		workbook = ExcelExportUtil.exportExcel(params, MsgClient.class, list);
	}
	
	@Test
	public void templateTet(){
		TemplateExportParams params = new TemplateExportParams();
	    params.setHeadingRows(2);
	    params.setHeadingStartRow(2);
	    Map<String,Object> map = new HashMap<String, Object>();
	    map.put("year", "2013");
	    map.put("sunCourses", list.size());
	    Map<String,Object> obj = new HashMap<String, Object>();
	    map.put("obj", obj);
	    obj.put("name", list.size());
	    params.setTemplateUrl("d:/tt.xls");
	    workbook = ExcelExportUtil.exportExcel(params, MsgClient.class, list,map);
	}
	
	@After
	public void writeData(){
		FileOutputStream fos = null;
		try {
			System.out.println(new Date().getTime() - start.getTime());
			File savefile = new File("d:/");
			if (!savefile.exists()) {
				savefile.mkdirs();
			}
			fos = new FileOutputStream("d:/data.xls");
			workbook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
