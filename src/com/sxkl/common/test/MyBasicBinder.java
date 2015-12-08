package com.sxkl.common.test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class MyBasicBinder extends BasicBinder{
	
	private Logger logger = LoggerFactory.getLogger(MyBasicBinder.class);

	public MyBasicBinder(JavaTypeDescriptor javaDescriptor,SqlTypeDescriptor sqlDescriptor) {
		super(javaDescriptor, sqlDescriptor);
	}

	@Override
	protected void doBind(PreparedStatement preparedStatement, Object arg1, int arg2,WrapperOptions wrapperOptions) throws SQLException {
		logger.info("----------------------logger-----------");
	}

}
