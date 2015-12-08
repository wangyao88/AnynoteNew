package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="an_attendence_time")
public class AttendenceTime {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="at_id",nullable=true,length=32)
	private String id;
	
	@Column(name="at_am_on_time",nullable=true,columnDefinition="DATETIME")
	private Date amOnTime;
	
	@Column(name="at_am_off_time",nullable=true,columnDefinition="DATETIME")
	private Date amOffTime;
	
	@Column(name="at_pm_on_time",nullable=true,columnDefinition="DATETIME")
	private Date pmOnTime;
	
	@Column(name="at_pm_off_time",nullable=true,columnDefinition="DATETIME")
	private Date pmOffTime;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY,targetEntity=Holidy.class)
	@JoinColumn(name="h_id",nullable=false,updatable=false)//指定一个外键，也可以不指定。
	private Holidy holidy;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getAmOnTime() {
		return amOnTime;
	}
	public void setAmOnTime(Date amOnTime) {
		this.amOnTime = amOnTime;
	}
	public Date getAmOffTime() {
		return amOffTime;
	}
	public void setAmOffTime(Date amOffTime) {
		this.amOffTime = amOffTime;
	}
	public Date getPmOnTime() {
		return pmOnTime;
	}
	public void setPmOnTime(Date pmOnTime) {
		this.pmOnTime = pmOnTime;
	}
	public Date getPmOffTime() {
		return pmOffTime;
	}
	public void setPmOffTime(Date pmOffTime) {
		this.pmOffTime = pmOffTime;
	}
	public Holidy getHolidy() {
		return holidy;
	}
	public void setHolidy(Holidy holidy) {
		this.holidy = holidy;
	}
	
}
