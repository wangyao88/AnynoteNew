package com.sxkl.common.test;

import org.junit.Assert;
import org.junit.Test;

import com.sxkl.common.utils.IDUtils;

public class IDUtilsTest {
	
	@Test
	public void idTest(){
		String id = IDUtils.getUUID();
		System.out.println(id.length());
		Assert.assertTrue(id.length() == 32);
	}

}
