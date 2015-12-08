package com.sxkl.common.test;

import java.util.Date;

import org.junit.Test;

import com.sxkl.common.utils.DateUtils;

public class StringSubTest {
	
	@Test
	public void strTest(){
		Date date = new Date();
		String now = DateUtils.formatDate2Str(date, "HH:mm:ss");
		System.out.println(now);
	}

}
