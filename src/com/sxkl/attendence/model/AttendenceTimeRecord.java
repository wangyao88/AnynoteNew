package com.sxkl.attendence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="an_attendence_time_record")
@ApiModel(value="AttendenceTimeRecord",description="考勤记录模型")
public class AttendenceTimeRecord {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid") 
	@Column(name="atr_id", nullable=true, length=32)
	@ApiModelProperty(required=true,value="atrId",notes="考勤记录主键",position=1,dataType="String")
	private String atrId;
	
	@Column(name="atr_am_on_time", nullable=true,length=10)//columnDefinition="DATETIME"
	@ApiModelProperty(required=true,value="amOnTime",notes="上午上班打卡时间",position=2,dataType="String")
	private String amOnTime;
	
	@Column(name="atr_am_off_time", nullable=true,length=10)
	@ApiModelProperty(required=true,value="amOffTime",notes="上午下班打卡时间",position=3,dataType="String")
	private String amOffTime;
	
	@Column(name="atr_pm_on_time", nullable=true,length=10)
	@ApiModelProperty(required=true,value="pmOnTime",notes="下午上班打卡时间",position=4,dataType="String")
	private String pmOnTime;
	
	@Column(name="atr_pm_off_time", nullable=true,length=10)
	@ApiModelProperty(required=true,value="pmOffTime",notes="下午下班打卡时间",position=5,dataType="String")
	private String pmOffTime;
	
	@Column(name="atr_user_id", nullable=true, length=32)
	@ApiModelProperty(required=true,value="userId",notes="当前登录人主键",position=6,dataType="String")
	private String userId;
	
	@Column(name="atr_remark", nullable=true, length=50)
	@ApiModelProperty(required=true,value="atrRemark",notes="打卡备注",position=7,dataType="String")
	private String atrRemark;
	
	@Column(name="atr_date", nullable=false,length=10)
	@ApiModelProperty(required=true,value="atrDate",notes="日期",position=8,dataType="String")
	private String atrDate;
	
	@Column(name="atr_am_on_time_type", nullable=false,length=1)
	@ApiModelProperty(required=true,value="atrAmOnTimeType",notes="上午上班打卡状态",position=9,dataType="String")
	private String atrAmOnTimeType;
	
	@Column(name="atr_am_off_time_type", nullable=false,length=1)
	@ApiModelProperty(required=true,value="atrAmOffTimeType",notes="上午下班打卡状态",position=10,dataType="String")
	private String atrAmOffTimeType;
	
	@Column(name="atr_pm_on_time_type", nullable=false,length=1)
	@ApiModelProperty(required=true,value="atrPmOnTimeType",notes="下午上班打卡状态",position=11,dataType="String")
	private String atrPmOnTimeType;
	
	@Column(name="atr_pm_off_time_type", nullable=false,length=1)
	@ApiModelProperty(required=true,value="atrPmOffTimeType",notes="下午下班打卡状态",position=12,dataType="String")
	private String atrPmOffTimeType;
	
	
	public String getAtrId() {
		return atrId;
	}
	public void setAtrId(String atrId) {
		this.atrId = atrId;
	}
	public String getAmOnTime() {
		return amOnTime;
	}
	public void setAmOnTime(String amOnTime) {
		this.amOnTime = amOnTime;
	}
	public String getAmOffTime() {
		return amOffTime;
	}
	public void setAmOffTime(String amOffTime) {
		this.amOffTime = amOffTime;
	}
	public String getPmOnTime() {
		return pmOnTime;
	}
	public void setPmOnTime(String pmOnTime) {
		this.pmOnTime = pmOnTime;
	}
	public String getPmOffTime() {
		return pmOffTime;
	}
	public void setPmOffTime(String pmOffTime) {
		this.pmOffTime = pmOffTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAtrRemark() {
		return atrRemark;
	}
	public void setAtrRemark(String atrRemark) {
		this.atrRemark = atrRemark;
	}
	public String getAtrDate() {
		return atrDate;
	}
	public void setAtrDate(String atrDate) {
		this.atrDate = atrDate;
	}
	public String getAtrAmOnTimeType() {
		return atrAmOnTimeType;
	}
	public void setAtrAmOnTimeType(String atrAmOnTimeType) {
		this.atrAmOnTimeType = atrAmOnTimeType;
	}
	public String getAtrAmOffTimeType() {
		return atrAmOffTimeType;
	}
	public void setAtrAmOffTimeType(String atrAmOffTimeType) {
		this.atrAmOffTimeType = atrAmOffTimeType;
	}
	public String getAtrPmOnTimeType() {
		return atrPmOnTimeType;
	}
	public void setAtrPmOnTimeType(String atrPmOnTimeType) {
		this.atrPmOnTimeType = atrPmOnTimeType;
	}
	public String getAtrPmOffTimeType() {
		return atrPmOffTimeType;
	}
	public void setAtrPmOffTimeType(String atrPmOffTimeType) {
		this.atrPmOffTimeType = atrPmOffTimeType;
	}
	
}
