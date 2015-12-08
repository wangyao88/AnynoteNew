package com.sxkl.easypoi.test;

import java.util.Date;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;

public class MsgClient implements java.io.Serializable {
	
	private static final long serialVersionUID = -898883034598738949L;
	/** id */
	private java.lang.String id;
	// 电话号码(主键)
	@Excel(name = "电话号码", width = 20, orderNum = "2")
	private String clientPhone = null;
	// 客户姓名
	@Excel(name = "姓名", orderNum = "1")
	private String clientName = null;
	// 所属分组
	@ExcelEntity
	private MsgClientGroup group = null;
	// 备注
	@Excel(name = "备注")
	private String remark = null;
	// 生日
	@Excel(name = "出生日期", format = "yyyy-MM-dd", width = 20)
	private Date birthday = null;
	// 创建人
	private String createBy = null;
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public MsgClientGroup getGroup() {
		return group;
	}
	public void setGroup(MsgClientGroup group) {
		this.group = group;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
}