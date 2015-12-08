package com.sxkl.authorized.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
 * 角色实体类
 * @author wangyao
 * @date 2015-11-27
 */
@Entity
@Table(name="an_role")
@ApiModel(value="Role",description="角色实体类")
public class Role implements Serializable{
	
	private static final long serialVersionUID = -6223894082807042009L;

	@Id
	@GenericGenerator(name="paymentableGenerator",strategy="uuid")
	@Column(name="r_role_id",nullable=false,length=32)
	@ApiModelProperty(required=true,value="roleId",notes="角色主键",position=1,dataType="String")
	private String roleId;
	
	@Column(name="r_role_name",nullable=false,length=15)
	@ApiModelProperty(required=true,value="roleName",notes="角色名称",position=2,dataType="String")
	private String roleName;
	
	@Column(name="r_create_time",nullable=false,columnDefinition="Date")
	@ApiModelProperty(required=true,value="createTime",notes="角色创建时间",position=3,dataType="Date")
	private Date createTime;
	
	@Transient
	private String createTimeStr;
	
	@JsonIgnore
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity=User.class )
	@JoinColumn(name="ro_user_id", referencedColumnName="u_user_id")
	private User createUser;
	
	@JsonIgnore
	@ManyToMany(mappedBy="roles",cascade=CascadeType.ALL)
	private Set<User> ownUsers;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name="an_roles_resources",
            joinColumns=@JoinColumn(name="r_role_id"),
            inverseJoinColumns=@JoinColumn(name="r_resource_id")
    )
	private Set<Resource> resources;
	
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public Set<User> getOwnUsers() {
		return ownUsers;
	}
	public void setOwnUsers(Set<User> ownUsers) {
		this.ownUsers = ownUsers;
	}
	public Set<Resource> getResources() {
		return resources;
	}
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	public String getCreateTimeStr() {
		return DateUtils.formatDate2Str(this.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	

}
