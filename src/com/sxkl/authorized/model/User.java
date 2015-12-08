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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sxkl.common.utils.DateUtils;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 用户实体咧
 * @author wangyao
 * @date 2015-11-27
 */
@Entity
@Table(name="an_users")
@ApiModel(value="User",description="用户实体类")
public class User implements Serializable{
	
	private static final long serialVersionUID = 5203661041152791184L;

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid") 
	@Column(name="u_user_id", nullable=false, length=32)
	@ApiModelProperty(required=true,value="userId",notes="用户主键",position=1,dataType="String")
	private String userId;
	
	@Column(name="u_user_name", nullable=false, length=15)
	@ApiModelProperty(required=true,value="userName",notes="用户姓名",position=2,dataType="String")
    private String userName;
	
	@Column(name="u_password", nullable=false, length=15)
	@ApiModelProperty(required=true,value="password",notes="用户密码",position=3,dataType="String")
    private String password;
	
	@Column(name="u_sex", nullable=false, length=2)
	@ApiModelProperty(required=true,value="sex",notes="用户性别",position=4,dataType="String")
    private String sex;
	
	@Column(name="u_birthday", nullable=true, columnDefinition="Date")
	@ApiModelProperty(required=true,value="birthday",notes="用户生日",position=5,dataType="Date")
    private Date birthday;
	
	@Transient
    private String birthdayStr;
	
	@Column(name="u_email", nullable=true, length=20)
	@ApiModelProperty(required=true,value="email",notes="用户邮箱",position=6,dataType="String")
    private String email;
	
	@Column(name="u_phone", nullable=true, length=11)
	@ApiModelProperty(required=true,value="phone",notes="用户电话",position=7,dataType="String")
    private String phone;
	
	@Column(name="u_status", nullable=false, length=2)
	@ApiModelProperty(required=true,value="status",notes="用户状态",position=8,dataType="String")
    private String status;
	
	@Column(name="u_del_flag", nullable=false, length=2)
	@ApiModelProperty(required=true,value="delflag",notes="用户删除标识",position=9,dataType="String")
    private String delflag;
	
	@Column(name="u_create_time", nullable=false, columnDefinition="Date")
	@ApiModelProperty(required=true,value="createTime",notes="用户创建时间",position=10,dataType="String")
    private Date createTime;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name="an_users_roles",
            joinColumns=@JoinColumn(name="u_user_id"),
            inverseJoinColumns=@JoinColumn(name="r_role_id")
    )
    private Set<Role> roles;
    
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getBirthdayStr() {
		return DateUtils.formatDate2Str(this.getBirthday(), "yyyy-MM-dd HH:mm:ss");
	}
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
}
