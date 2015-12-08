package com.sxkl.menu.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sxkl.authorized.model.User;
import com.sxkl.common.utils.DateUtils;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 菜单实体类
 * @author wangyao
 * @date 2015-11-27
 */
@Embeddable
@ApiModel(value="Menu",description="菜单实体类")
public class Menu implements Serializable{
	
	private static final long serialVersionUID = 1269007160394193676L;

	@Column(name="m_menu_name",nullable=false,length=100)
	@ApiModelProperty(required=true,value="menuId",notes="菜单名称",position=2,dataType="String")
	private String menuName;
	
	@Column(name="m_create_time",nullable=false,columnDefinition="Date")
	@ApiModelProperty(required=true,value="createTime",notes="菜单创建时间",position=3,dataType="Date")
	private Date createTime;
	
	@Transient
	private String createTimeStr;
	
	@JsonIgnore
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity=User.class )
	@JoinColumn(name="m_user_id", referencedColumnName="u_user_id")
	private User createUser;
	
	@Column(name="m_menu_link",nullable=true,length=200)
	@ApiModelProperty(required=true,value="menuLink",notes="菜单链接",position=4,dataType="String")
    private String menuLink;
	
	@Column(name="m_del_flag",nullable=false,length=2)
	@ApiModelProperty(required=true,value="delflag",notes="菜单删除标识",position=5,dataType="String")
    private String delflag = "0";
	
	@Column(name="m_is_leaf",nullable=false,length=5)
	@ApiModelProperty(required=true,value="isLeaf",notes="是否叶子节点",position=7,dataType="String")
	private String isLeaf = "1";
	
	@Column(name="m_icon",nullable=true,length=100)
	@ApiModelProperty(required=true,value="icon",notes="菜单图标",position=7,dataType="String")
	private String icon;
    
    
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		createTimeStr = DateUtils.formatDate2Str(this.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public String getMenuLink() {
		return menuLink;
	}
	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
