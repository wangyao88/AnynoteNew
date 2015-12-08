package com.sxkl.menu.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="an_children_menu")
@ApiModel(value="ChildrenMenu",description="子菜单实体类")
public class ChildrenMenu implements Serializable{
	
	private static final long serialVersionUID = 1649603702160294503L;

	@Id
	@GenericGenerator(name="paymentableGenerator",strategy="uuid")
	@Column(name="cm_children_menu_id",nullable=false,length=32)
	@ApiModelProperty(required=true,value="childrenMenuId",notes="菜单主键",position=1,dataType="String")
	private String childrenMenuId;
	
	@Embedded
	private Menu menu;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name="pm_parent_menu_id", referencedColumnName="pm_parent_menu_id")
	private ParentMenu parentMenu;

	public String getChildrenMenuId() {
		return childrenMenuId;
	}

	public void setChildrenMenuId(String childrenMenuId) {
		this.childrenMenuId = childrenMenuId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public ParentMenu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(ParentMenu parentMenu) {
		this.parentMenu = parentMenu;
	}

}
