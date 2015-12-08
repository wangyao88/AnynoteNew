package com.sxkl.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessImpl implements JsonValueProcessor {

	private String formate;

	public DateJsonValueProcessImpl() {
		this.formate = "yyyy-MM-dd hh:mm:ss";
	}

	public DateJsonValueProcessImpl(String formate) {
		this.formate = formate;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return null;
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		if (value instanceof Date) {
			String str = new SimpleDateFormat(this.formate)
					.format((Date) value);
			return str;
		}
		return null;
	}

	public String getFormate() {
		return formate;
	}

	public void setFormate(String formate) {
		this.formate = formate;
	}
}