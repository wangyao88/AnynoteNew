package com.log.sxkl.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 操作日志实体类
 * @author wangyao
 * @date 2015-11-19
 */
@Entity
@Table(name="an_log")
@ApiModel(value="Log",description="操作日志实体类")
public class Log implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid") 
	@Column(name="l_id", nullable=false, length=32)
	@ApiModelProperty(required=true,value="lId",notes="操作日志主键",position=1,dataType="String")
	private String lId;
	
	@Column(name="l_user_name", nullable=false, length=10)
	@ApiModelProperty(required=true,value="lUserName",notes="操作人",position=2,dataType="String")
	private String lUserName;
	
	@Column(name="l_date",nullable=false,columnDefinition="Date")
	@ApiModelProperty(required=true,value="lDate",notes="操作日期",position=3,dataType="Date")
	private Date lDate;
	
	@Column(name="l_content", nullable=false, length=500)
	@ApiModelProperty(required=true,value="lContent",notes="操作内容",position=4,dataType="String")
	private String lContent;
	
	@Column(name="l_operate", nullable=false, length=15)
	@ApiModelProperty(required=true,value="lOperate",notes="操作类型",position=5,dataType="String")
	private String lOperate;
	
	@Column(name="l_cost_time", nullable=false)
	@ApiModelProperty(required=true,value="lCostTime",notes="响应时间",position=6,dataType="Integer")
	private int lCostTime;
	
	@Column(name="l_result", nullable=false, length=10)
	@ApiModelProperty(required=true,value="lResult",notes="操作结果",position=7,dataType="String")
	private String lResult;
	
	@Column(name="l_class_name", nullable=false, length=200)
	@ApiModelProperty(required=true,value="lClassName",notes="操作类名",position=8,dataType="String")
	private String lClassName;
	
	@Column(name="l_operate_name", nullable=false, length=100)
	@ApiModelProperty(required=true,value="lOperateName",notes="操作方法名",position=9,dataType="String")
	private String lOperateName;
	
	public String getlId() {
		return lId;
	}
	public void setlId(String lId) {
		this.lId = lId;
	}
	public String getlUserName() {
		return lUserName;
	}
	public void setlUserName(String lUserName) {
		this.lUserName = lUserName;
	}
	public Date getlDate() {
		return lDate;
	}
	public void setlDate(Date lDate) {
		this.lDate = lDate;
	}
	public String getlContent() {
		return lContent;
	}
	public void setlContent(String lContent) {
		this.lContent = lContent;
	}
	public String getlOperate() {
		return lOperate;
	}
	public void setlOperate(String lOperate) {
		this.lOperate = lOperate;
	}
	public int getlCostTime() {
		return lCostTime;
	}
	public void setlCostTime(int lCostTime) {
		this.lCostTime = lCostTime;
	}
	public String getlResult() {
		return lResult;
	}
	public void setlResult(String lResult) {
		this.lResult = lResult;
	}
	public String getlClassName() {
		return lClassName;
	}
	public void setlClassName(String lClassName) {
		this.lClassName = lClassName;
	}
	public String getlOperateName() {
		return lOperateName;
	}
	public void setlOperateName(String lOperateName) {
		this.lOperateName = lOperateName;
	}
}
