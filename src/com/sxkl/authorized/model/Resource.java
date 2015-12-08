package com.sxkl.authorized.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sxkl.common.utils.DateUtils;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 资源实体类
 * @author wangyao
 * @date 2015-11-27
 */

@Entity
@Table(name="an_resource")
@ApiModel(value="Resource",description="资源实体类")
public class Resource implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="paymentableGenerator",strategy="uuid")
	@Column(name="r_resource_id",nullable=false,length=32)
	@ApiModelProperty(required=true,value="resourceId",notes="资源主键",position=1,dataType="String")
	private String resourceId;
	
	@Column(name="r_resource_name",nullable=false,length=10)
	@ApiModelProperty(required=true,value="resourceName",notes="资源名称",position=2,dataType="String")
	private String resourceName;
	
	@Column(name="r_create_time",nullable=false,columnDefinition="Date")
	@ApiModelProperty(required=true,value="createTime",notes="资源创建时间",position=3,dataType="Date")
	private Date createTime;
	
	@Transient
	private String createTimeStr;
	
	@Column(name="r_link",nullable=false,length=100)
	@ApiModelProperty(required=true,value="link",notes="资源链接",position=4,dataType="String")
	private String link;
	
	@Column(name="r_del_flag",nullable=false,length=32)
	@ApiModelProperty(required=true,value="delflag",notes="资源删除标识",position=5,dataType="String")
	private String delflag;
	
	@JsonIgnore
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity=User.class )
	@JoinColumn(name="re_user_id", referencedColumnName="u_user_id")
	private User createUser;
	
	@JsonIgnore
	@ManyToMany(mappedBy="resources",cascade=CascadeType.ALL)
	private Set<Role> roles;
	
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getCreateTimeStr() {
		return DateUtils.formatDate2Str(this.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
}
