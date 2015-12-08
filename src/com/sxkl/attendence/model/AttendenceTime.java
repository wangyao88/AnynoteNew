package com.sxkl.attendence.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="an_attendence_time")
@ApiModel(value="AttendenceTime",description="考勤信息")
public class AttendenceTime {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="at_id",nullable=true,length=32)
	@ApiModelProperty(required=true,value="atId",notes="考勤信息主键",position=1,dataType="String")
	private String atId;
	
	@Column(name="at_am_on_time",nullable=true,length=20)
	@ApiModelProperty(required=true,value="amOnTime",notes="上午上班打卡时间",position=2,dataType="String")
	private String amOnTime;
	
	@Column(name="at_am_off_time",nullable=true,length=20)
	@ApiModelProperty(required=true,value="amOffTime",notes="上午下班打卡时间",position=3,dataType="String")
	private String amOffTime;
	
	@Column(name="at_pm_on_time",nullable=true,length=20)
	@ApiModelProperty(required=true,value="pmOnTime",notes="下午上班打卡时间",position=4,dataType="String")
	private String pmOnTime;
	
	@Column(name="at_pm_off_time",nullable=true,length=20)
	@ApiModelProperty(required=true,value="pmOffTime",notes="下午下班打卡时间",position=5,dataType="String")
	private String pmOffTime;
	
	@JsonIgnore
	@OneToMany(mappedBy = "attendenceTime", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ApiModelProperty(required=true,value="holidys",notes="关联的节假日",position=6,dataType="Set")
	private Set<Holidy> holidys = new HashSet<Holidy>();
	
	
	public String getAtId() {
		return atId;
	}
	public void setAtId(String atId) {
		this.atId = atId;
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
	public Set<Holidy> getHolidys() {
		return holidys;
	}
	public void setHolidys(Set<Holidy> holidys) {
		this.holidys = holidys;
	}
	
}
