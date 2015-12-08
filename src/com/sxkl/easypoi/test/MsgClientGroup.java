package com.sxkl.easypoi.test;

import java.io.Serializable;

public class MsgClientGroup implements Serializable {
	/** * */
	private static final long serialVersionUID = 6946265640897464878L;
	// 组名 @Excel(name = "分组")
	private String groupName = null;
	/** * 创建人 */
	private String createBy;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
}