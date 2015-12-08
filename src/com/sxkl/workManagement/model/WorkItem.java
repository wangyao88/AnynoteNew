package com.sxkl.workManagement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 工作项实体
 * @author wangyao
 * @date 2015-11-17
 */

@Entity
@Table(name="an_work_item")
@ApiModel(value="WorkItem",description="工作项实体")
public class WorkItem implements Serializable{
	
	private static final long serialVersionUID = -1552874653625476369L;
	
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid") 
	@Column(name="wi_id", nullable=false, length=32)
	@ApiModelProperty(required=true,value="wiId",notes="工作项主键",position=1,dataType="String")
	private String wiId;
	
	@Column(name="wi_date", nullable=false, columnDefinition="Date")
	@ApiModelProperty(required=true,value="wiDate",notes="工作项日期",position=2,dataType="Date")
	private Date wiDate;
	
	@Column(name="wi_title", nullable=false, length=100)
	@ApiModelProperty(required=true,value="wiTitile",notes="工作项标题",position=3,dataType="String")
	private String wiTitile;
	
	@Column(name="wi_important_level", nullable=false, length=4)
	@ApiModelProperty(required=true,value="wiImportantLevel",notes="工作项重要性",position=4,dataType="String")
	private String wiImportantLevel;
	
	@Column(name="wi_emergent_level", nullable=false, length=4)
	@ApiModelProperty(required=true,value="wiEmergentLevel",notes="工作项紧急性",position=5,dataType="String")
	private String wiEmergentLevel;
	
	@Column(name="wi_is_temporary", nullable=false, length=4)
	@ApiModelProperty(required=true,value="wiIsTemporary",notes="工作项是否临时任务",position=6,dataType="String")
	private String wiIsTemporary;
	
	@Column(name="wi_cost_time", nullable=false)
	@ApiModelProperty(required=true,value="wiCostTime",notes="工作项消费时间",position=7,dataType="Integer")
	private Integer wiCostTime;
	
	@Column(name="wi_finish_rate", nullable=false, length=10)
	@ApiModelProperty(required=true,value="wiFinishRate",notes="工作项完成率",position=8,dataType="String")
	private String wiFinishRate;
	
	@ManyToOne(cascade=CascadeType.ALL, optional=false)
	@JoinColumn(name="wm_id", referencedColumnName="wm_id")
	@ApiModelProperty(required=true,value="workMain",notes="工作日报",position=9,dataType="com.sxkl.workManagement.model.WorkMain")
	private WorkMain workMain;
	
	
	public String getWiId() {
		return wiId;
	}
	public void setWiId(String wiId) {
		this.wiId = wiId;
	}
	public Date getWiDate() {
		return wiDate;
	}
	public void setWiDate(Date wiDate) {
		this.wiDate = wiDate;
	}
	public String getWiTitile() {
		return wiTitile;
	}
	public void setWiTitile(String wiTitile) {
		this.wiTitile = wiTitile;
	}
	public String getWiImportantLevel() {
		return wiImportantLevel;
	}
	public void setWiImportantLevel(String wiImportantLevel) {
		this.wiImportantLevel = wiImportantLevel;
	}
	public String getWiEmergentLevel() {
		return wiEmergentLevel;
	}
	public void setWiEmergentLevel(String wiEmergentLevel) {
		this.wiEmergentLevel = wiEmergentLevel;
	}
	public String getWiIsTemporary() {
		return wiIsTemporary;
	}
	public void setWiIsTemporary(String wiIsTemporary) {
		this.wiIsTemporary = wiIsTemporary;
	}
	public Integer getWiCostTime() {
		return wiCostTime;
	}
	public void setWiCostTime(Integer wiCostTime) {
		this.wiCostTime = wiCostTime;
	}
	public String getWiFinishRate() {
		return wiFinishRate;
	}
	public void setWiFinishRate(String wiFinishRate) {
		this.wiFinishRate = wiFinishRate;
	}
	public WorkMain getWorkMain() {
		return workMain;
	}
	public void setWorkMain(WorkMain workMain) {
		this.workMain = workMain;
	}
}
