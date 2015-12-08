package com.sxkl.workManagement.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 工作日报主体
 * @author wangyao
 * @date 2015-11-17
 */

@Entity
@Table(name="an_work_main")
@ApiModel(value="WorkMain",description="工作日报主体")
public class WorkMain implements Serializable{

	private static final long serialVersionUID = -3542719731412173438L;

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid") 
	@Column(name="wm_id", nullable=false, length=32)
	@ApiModelProperty(required=true,value="wmId",notes="工作日报主键",position=1,dataType="String")
	private String wmId;
	
	@Column(name="wm_name", nullable=false, length=10)
	@ApiModelProperty(required=true,value="wmName",notes="工作日报人",position=2,dataType="String")
	private String wmName;
	
	@Column(name="wm_year", nullable=false, length=10)
	@ApiModelProperty(required=true,value="wmYear",notes="工作日报年度",position=3,dataType="String")
    private String wmYear;
	
	@Column(name="wm_achievement", nullable=false, length=100)
	@ApiModelProperty(required=true,value="wmAchievement",notes="工作日报交付物",position=4,dataType="String")
    private String wmAchievement;
	
	@Column(name="wm_remark", nullable=false, length=500)
	@ApiModelProperty(required=true,value="wmRemark",notes="工作日自省、知识共享、问题交流",position=5,dataType="String")
    private String wmRemark;
	
	@OneToMany(mappedBy="workMain",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@ApiModelProperty(required=true,value="workItems",notes="工作日报关联的工作项",position=6,dataType="String")
    private Set<WorkItem> workItems;
    
    @Column(name="user_id", nullable=false, length=32)
	@ApiModelProperty(required=true,value="userId",notes="工作日报人Id",position=7,dataType="String")
    private String userId;
    
    @Column(name="wm_title", nullable=false, length=30)
	@ApiModelProperty(required=true,value="wmTitle",notes="工作日报标题",position=8,dataType="String")
	private String wmTitle;
    
    @Transient
    private int wmItemNum;

	public String getWmId() {
		return wmId;
	}

	public void setWmId(String wmId) {
		this.wmId = wmId;
	}

	public String getWmName() {
		return wmName;
	}

	public void setWmName(String wmName) {
		this.wmName = wmName;
	}

	public String getWmYear() {
		return wmYear;
	}

	public void setWmYear(String wmYear) {
		this.wmYear = wmYear;
	}

	public String getWmAchievement() {
		return wmAchievement;
	}

	public void setWmAchievement(String wmAchievement) {
		this.wmAchievement = wmAchievement;
	}

	public String getWmRemark() {
		return wmRemark;
	}

	public void setWmRemark(String wmRemark) {
		this.wmRemark = wmRemark;
	}

	public Set<WorkItem> getWorkItems() {
		return workItems;
	}

	public void setWorkItems(Set<WorkItem> workItems) {
		this.workItems = workItems;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getWmItemNum() {
		if(this.workItems != null){
			return this.workItems.size();
		}
		return 0;
	}

	public void setWmItemNum(int wmItemNum) {
		this.wmItemNum = wmItemNum;
	}

	public String getWmTitle() {
		return wmTitle;
	}

	public void setWmTitle(String wmTitle) {
		this.wmTitle = wmTitle;
	}
	
}
