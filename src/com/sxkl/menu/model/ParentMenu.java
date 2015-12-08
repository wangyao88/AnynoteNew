package com.sxkl.menu.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 父菜单实体类
 * @author wangyao
 * @date 2015-11-27
 */

@Entity
@Table(name="an_parent_menu")
@ApiModel(value="ParentMenu",description="父菜单实体类")
public class ParentMenu implements Serializable{
	
	private static final long serialVersionUID = 9043235810641306495L;

	@Id
	@GenericGenerator(name="paymentableGenerator",strategy="uuid")
	@Column(name="pm_parent_menu_id",nullable=false,length=32)
	@ApiModelProperty(required=true,value="parentMenuId",notes="菜单主键",position=1,dataType="String")
	private String parentMenuId;
	
	@Embedded
	private Menu menu;
	
	@JsonIgnore
	@OneToMany(mappedBy="parentMenu",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ChildrenMenu> childrenMenu;

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Set<ChildrenMenu> getChildrenMenu() {
		return childrenMenu;
	}

	public void setChildrenMenu(Set<ChildrenMenu> childrenMenu) {
		this.childrenMenu = childrenMenu;
	}

}
