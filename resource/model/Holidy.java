package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="an_holidy")
public class Holidy {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="h_id",nullable=true,length=32)
	private String id;
	
	@Column(name="h_name",nullable=true,length=32)
	private String name;
	
	@Column(name="h_start",nullable=true,columnDefinition="DATETIME")
	private Date start;
	
	@Column(name="h_end",nullable=true,length=32)
	private Date end;
	
	@OneToOne(mappedBy="holidy",cascade=CascadeType.ALL,fetch=FetchType.LAZY,targetEntity=AttendenceTime.class)
	private AttendenceTime attendenceTime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public AttendenceTime getAttendenceTime() {
		return attendenceTime;
	}
	public void setAttendenceTime(AttendenceTime attendenceTime) {
		this.attendenceTime = attendenceTime;
	}
}
