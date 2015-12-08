package com.sxkl.common.test;

import java.util.List;

import org.junit.Test;

import com.sxkl.common.utils.DateUtils;

public class ClendarTest {
	
	@Test
	public void weekTest(){
//		Calendar calendar = Calendar.getInstance();
//		int num = calendar.get(Calendar.DAY_OF_WEEK);
//		System.out.println(num);
		
//		Calendar calendar = Calendar.getInstance();
//		  calendar.add(Calendar.DAY_OF_YEAR, -30);
//		  int year = calendar.get(Calendar.YEAR);
//		  int month = calendar.get(Calendar.MARCH) + 1;
//		  int day = calendar.get(Calendar.DAY_OF_MONTH);
//		  StringBuffer result = new StringBuffer();
//		  result.append(year)
//		        .append("-")
//		        .append(month)
//		        .append("-")
//		        .append(day);
//		  System.out.println(result.toString());
		List<String> list = DateUtils.getDayRange();
		for(String str : list){
			System.out.println(str);
		}
	}

}
