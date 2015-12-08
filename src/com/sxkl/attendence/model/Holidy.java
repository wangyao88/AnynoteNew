package com.sxkl.attendence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="an_holidy")
@ApiModel(value="Holidy",description="节假日模型")
public class Holidy implements Serializable{
	
	private static final long serialVersionUID = 5013900521271043617L;

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name="h_id",nullable=true,length=32)
	@ApiModelProperty(required=true,value="hId",notes="节假日主键",position=1,dataType="String")
	private String hId;
	
	@Column(name="h_name",nullable=true,length=32)
	@ApiModelProperty(required=true,value="hName",notes="节假日名称",position=2,dataType="String")
	private String hName;
	
	@Column(name="h_start",nullable=true,columnDefinition="DATETIME")
	@ApiModelProperty(required=true,value="hStart",notes="节假日开始时间",position=3,dataType="Date")
	private Date hStart;
	
	@Column(name="h_end",nullable=true,columnDefinition="DATETIME")
	@ApiModelProperty(required=true,value="hEnd",notes="节假日结束时间",position=4,dataType="Date")
	private Date hEnd;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name="at_id", referencedColumnName="at_id")//外键为sut_id，与student中的id关联
	@ApiModelProperty(required=true,value="attendenceTime",notes="考勤信息",position=5,dataType="AttendenceTime")
	private AttendenceTime attendenceTime;
	
	
	public Holidy() {
	}
	public Holidy(String hId, String hName, Date hStart, Date hEnd,
			AttendenceTime attendenceTime) {
		this.hId = hId;
		this.hName = hName;
		this.hStart = hStart;
		this.hEnd = hEnd;
		this.attendenceTime = attendenceTime;
	}
	public Holidy(String hId) {
		this.hId = hId;
	}
	public String gethId() {
		return hId;
	}
	public void sethId(String hId) {
		this.hId = hId;
	}
	public String gethName() {
		return hName;
	}
	public void sethName(String hName) {
		this.hName = hName;
	}
	public Date gethStart() {
		return hStart;
	}
	public void sethStart(Date hStart) {
		this.hStart = hStart;
	}
	public Date gethEnd() {
		return hEnd;
	}
	public void sethEnd(Date hEnd) {
		this.hEnd = hEnd;
	}
	public AttendenceTime getAttendenceTime() {
		return attendenceTime;
	}
	public void setAttendenceTime(AttendenceTime attendenceTime) {
		this.attendenceTime = attendenceTime;
	}
}
