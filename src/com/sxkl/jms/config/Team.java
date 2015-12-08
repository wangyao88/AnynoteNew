/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.sxkl.jms.config;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 开发团队.
 * 
 * @author calvin
 */
public class Team extends IdEntity {

	private String name;
	private User master;
	private List<User> userList = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getMaster() {
		return master;
	}

	public void setMaster(User master) {
		this.master = master;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
