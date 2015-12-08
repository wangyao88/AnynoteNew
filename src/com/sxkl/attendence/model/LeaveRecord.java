package com.sxkl.attendence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="an_leave_record")
@ApiModel(value="LeaveRecord",description="请假记录")
public class LeaveRecord implements Serializable{
	
	private static final long serialVersionUID = 2797313099623908062L;

	@Id
	@GenericGenerator(name="paymentableGenerator", strategy="uuid") 
	@Column(name="lr_id", nullable=true, length=32)
	@ApiModelProperty(required=true,value="lrId",notes="请假记录主键",position=1,dataType="String")
	private String lrId;
	
	@Column(name="lr_start_time", nullable=true,columnDefinition="Date")
	@ApiModelProperty(required=true,value="lrStartTime",notes="请假记录开始时间",position=2,dataType="Date")
	private Date lrStartTime;
	
	@Column(name="lr_end_time", nullable=true,columnDefinition="Date")
	@ApiModelProperty(required=true,value="lrEndTime",notes="请假记录结束时间",position=3,dataType="Date")
	private Date lrEndTime;
	
	@Column(name="user_id", nullable=true,length=32)
	@ApiModelProperty(required=true,value="userId",notes="当前登录人ID",position=4,dataType="String")
	private String userId;
	
	@Column(name="lr_remark", nullable=true,length=200)
	@ApiModelProperty(required=true,value="lrRemark",notes="请假记录是由",position=5,dataType="String")
	private String lrRemark;
	
	public String getLrId() {
		return lrId;
	}
	public void setLrId(String lrId) {
		this.lrId = lrId;
	}
	public Date getLrStartTime() {
		return lrStartTime;
	}
	public void setLrStartTime(Date lrStartTime) {
		this.lrStartTime = lrStartTime;
	}
	public Date getLrEndTime() {
		return lrEndTime;
	}
	public void setLrEndTime(Date lrEndTime) {
		this.lrEndTime = lrEndTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLrRemark() {
		return lrRemark;
	}
	public void setLrRemark(String lrRemark) {
		this.lrRemark = lrRemark;
	}

}
