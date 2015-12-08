package com.sxkl.loginHistory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * 登陆历史记录实体类
 * @author wangyao
 * @date 2015-10-21
 */
@Entity
@Table(name="an_login_history")
@ApiModel(value="LoginHistory",description="登陆历史记录")
public class LoginHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid") 
	@Column(name="lh_id",nullable=false,length=32)
	@ApiModelProperty(required=true,value="id",notes="登陆历史记录主键",position=1,dataType="String")
	private String id;
	
	@Column(name="lh_login_user_name",nullable=true,length=32)
	@ApiModelProperty(required=true,value="loginUserName",notes="登陆用户名",position=2,dataType="String")
	private String loginUserName;
	
	@Column(name="lh_login_user_id",nullable=true,length=32)
	@ApiModelProperty(required=true,value="loginUserId",notes="登陆用户主键",position=3,dataType="String")
	private String loginUserId;
	
	@Column(name="lh_login_time",nullable=true,columnDefinition="DATETIME")
	@ApiModelProperty(required=true,value="loginTime",notes="登陆时间",position=4,dataType="Date")
	private Date loginTime;
	
	@Column(name="lh_login_out",nullable=true,columnDefinition="DATETIME")
	@ApiModelProperty(required=true,value="loginOut",notes="登出时间",position=5,dataType="Date")
	private Date loginOut;
	
	@Column(name="lh_login_sum_time",nullable=true)
	@ApiModelProperty(required=true,value="loginSumTime",notes="本次总登陆时长",position=6,dataType="Long")
	private long loginSumTime;
	
	@Column(name="lh_login_ip",nullable=true,length=32)
	@ApiModelProperty(required=true,value="loginIP",notes="登陆IP",position=7,dataType="String")
	private String loginIP;
	
	public LoginHistory() {
	}
	
	public LoginHistory(String id) {
		this.id = id;
	}
	
	
	public LoginHistory(Date loginTime, long loginSumTime) {
		this.loginTime = loginTime;
		this.loginSumTime = loginSumTime;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginUserName() {
		return loginUserName;
	}
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getLoginOut() {
		return loginOut;
	}
	public void setLoginOut(Date loginOut) {
		this.loginOut = loginOut;
	}
	public long getLoginSumTime() {
		return loginSumTime;
	}
	public void setLoginSumTime(long loginSumTime) {
		this.loginSumTime = loginSumTime;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

}
