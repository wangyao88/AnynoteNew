/*******************************************************************************
 * Anynote 共同JS定义
 *******************************************************************************/

var Menu = {};

/*******************************************************************************
 * 常量数据
 *******************************************************************************/
// 主页树数据
Menu.homeMenuData = [{
	id: 'homePage',
	text: '我的主页',
	leaf: true
}];

//任务树数据
Menu.todoMenuData = [{
	id: 'todoList',
	text: '所有事项',
	leaf: true
},{
	id: 'uncompleteTodoList',
	text: '未完成事项',
	leaf: true
},{
	id: 'completeTodoList',
	text: '已完成事项',
	leaf: true
}];

// 账目树数据
Menu.accountMenuData = [{
	id: 'accountList',
	text: '账目查询',
	leaf: true
},{
	id: 'accountCategory',
	text: '收支项目',
	leaf: true
}];

// 用户树数据
Menu.systemMenuData = [{
	id: 'backup',
	text: '备份数据库',
	leaf: true
},{
	id: 'userList',
	text: '用户查询',
	leaf: true
},{
	id: 'setting',
	text: '系统设置',
	leaf: true
}];

/*******************************************************************************
 * 方法
 *******************************************************************************/
/*******************************************************************************
 * 主页菜单
 *******************************************************************************/
Menu.getHomeAccordion = function(param){
	// 主页
	var homeAccordion = {
		contentEl: 'homeAccordionDiv',
        title: '主页',
        border: false,
        iconCls: 'house',
    	tools: [{
			id: 'refresh',
			handler: function(){
	    		var homeTree = Ext.getCmp('homeTreePanel');
				Anynote.reloadTreeNode(homeTree, null, function(){
					homeTree.expandAll();
				});
    		}
	    }]
    };
	
	// 主页树
	var homeTree = new Ext.tree.TreePanel({
		id: 'homeTreePanel',
		renderTo: 'homeAccordionDiv',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: false,
	    containerScroll: true,
	    border: false,
	    dataUrl:'',
	    rootVisible: false,
	    loader: new Ext.tree.TreeLoader({
	    	dataUrl:param.baseUrl + '/userMetaAction.do?method=getFavoriteMenuTree',
	    	listeners: {
				load: function(loader,node,response){
					var jsonResult = Ext.decode(response.responseText);
					if(jsonResult && jsonResult.exceptionMsg && jsonResult.exceptionMsg!=''){
						Anynote.exceptionWindow(jsonResult.message, jsonResult.exceptionMsg);
					}
				}
			}
	    }),
	    root: new Ext.tree.AsyncTreeNode({
	        text: 'root',
	        id: 'root'
    	}),
    	listeners: {click: function(node) {homeTreeClick(node);}}
	});
	homeTree.getRootNode().expand(true);
	
	// 树右键菜单
	var homeTreeContextMenu = new Ext.menu.Menu({
		items:[{
			text: '删除',
			iconCls: 'cross',
			handler: function(node, e){
				var selectNode = homeTree.getSelectionModel().getSelectedNode();
				if(selectNode.id=='homePage') return;
				var params = {};
				params.userMetaId = selectNode.id;
				// 发送请求
				Anynote.ajaxRequest({
					baseUrl: param.baseUrl,
					baseParams: params,
					action: '/userMetaAction.do?method=delete',
					callback: function(jsonResult){
						Anynote.reloadTreeNode(homeTree, null, function(node){
							node.expand(true);
						});
					},
					showWaiting: true,
					failureMsg: '删除失败.'
				});
			}
		}]
	});

	// 绑定树右键菜单
	homeTree.on('contextmenu',function(node,e){
		if(node.id=='homePage'){
			homeTreeContextMenu.items.get(0).disable();
		}else{
			homeTreeContextMenu.items.get(0).enable();
		}
		e.preventDefault();
		node.select();
		homeTreeContextMenu.showAt(e.getXY());
	});
	
	// 主页树的单击事件
	function homeTreeClick(node){
		if(!node.isLeaf()) return;
		var nodeId = node.attributes.id;
		var tabTitle = node.text;
		var tabUrl = param.baseUrl + node.attributes.attributes.url;
		var tabId = Anynote.getTabIdByUrl(tabUrl);
		if(tabId=='todoListTab'){
			tabTitle = '任务查询';
		}else if(tabId=='noteListTab'){
			tabTitle = '笔记查询';
		}else if(tabId=='pictureListTab'){
			tabTitle = '图片查询';
		}else if(tabId=='feedFavoriteTab'){
			tabTitle = '订阅收藏夹';
		}else if(tabId=='feedViewerTab'){
			tabTitle = '订阅查询';
		}else if(tabId=='documentListTab'){
			tabTitle = '文档查询';
		}else if(tabId=='accountListTab'){
			tabTitle = '账目查询';
		}else if(tabId=='accountCategoryListTab'){
			tabTitle = '收支项目';
		}else if(tabId=='userListTab'){
			tabTitle = '用户查询';
		}else if(tabId=='settingTab'){
			tabTitle = '系统设置';
		}
		// 	打开Tab页
		Anynote.openTab(tabTitle, tabUrl);
	}
	return homeAccordion;
}
/*******************************************************************************
 * 任务菜单
 *******************************************************************************/
Menu.getTodoAccordion = function(param){
	// 任务
	var todoAccordion = {
        contentEl: 'todoAccordionDiv',
        title: '任务管理',
        border: false,
        autoScroll: true,
        iconCls: 'add-todo',
    	tools: [{
			id: 'refresh',
			handler: function(){
    			Anynote.reloadTreeNode(Ext.getCmp('todoTreePanel'), null, null);
    		}
	    }]
    };
	// 任务树
	var todoTree = new Ext.tree.TreePanel({
		id: 'todoTreePanel',
		renderTo: 'todoAccordionDiv',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: false,
	    containerScroll: true,
	    border: false,
	    dataUrl:'',
	    rootVisible: false,
	    loader: new Ext.tree.TreeLoader({
	    	dataUrl:param.baseUrl + '/todoCategoryAction.do?method=getTodoCategoryTree',
	    	listeners: {
				load: function(loader,node,response){
					var jsonResult = Ext.decode(response.responseText);
					if(jsonResult && jsonResult.exceptionMsg && jsonResult.exceptionMsg!=''){
						Anynote.exceptionWindow(jsonResult.message, jsonResult.exceptionMsg);
					}
				}
			}
	    }),
	    root: new Ext.tree.AsyncTreeNode({
	        text: 'root',
	        id: 'root'
    	}),
    	listeners: {click: function(node) {
					todoTreeClick(node);
			}
		}
	});
	todoTree.getRootNode().expand();
	
	// 树右键菜单
	var todoTreeContextMenu = new Ext.menu.Menu({
		items:[{
			text: '新建分类',
			iconCls: 'tag_blue_add',
			handler: function(node, e){
				var selectNode = todoTree.getSelectionModel().getSelectedNode();
				editTodoCategory('', '');
			}
		},{
			text: '修改分类',
			iconCls: 'tag_blue_edit',
			handler: function(node, e){
				// 修改节点
				var selectNode = todoTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="allTodo" || selectNode.id=="0" || selectNode.id=="uncompleteTodo" || selectNode.id=="completeTodo") return;
				// 不能编辑根节点
				if(selectNode.id=='0') return;
				editTodoCategory(selectNode.id, selectNode.text);
			}
		},{
			text: '删除分类',
			iconCls: 'tag_blue_delete',
			handler: function(){
				// 选中的节点
				var selectNode = todoTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="allTodo" || selectNode.id=="0" || selectNode.id=="uncompleteTodo" || selectNode.id=="completeTodo") return;
				Ext.Msg.confirm("警告", "确定要删除分类及其下的所有任务吗？", function(btn){
					if(btn=="yes"){
						var params = {};
						params.categoryId = selectNode.id;
						// 发送请求
						Anynote.ajaxRequest({
							baseUrl: param.baseUrl,
							baseParams: params,
							action: '/todoCategoryAction.do?method=delete',
							callback: function(jsonResult){
								Anynote.reloadTreeNode(todoTree, null, null);
							},
							showWaiting: true,
							failureMsg: '删除失败.'
						});
					}
				});
			}
		},'-',{
			text: '加入快捷菜单',
			iconCls: 'star',
			handler: function(node, e){
				var selectNode = todoTree.getSelectionModel().getSelectedNode();
				if(!selectNode.isLeaf()) return;
				var url = '';
				if(selectNode.id=='allTodo'){
					url = '/websrc/page/todo/todoList.jsp';
				}else if(selectNode.id=='uncompleteTodo'){
					url = '/websrc/page/todo/todoList.jsp?delflag=1';
				}else if(selectNode.id=='completeTodo'){
					url = '/websrc/page/todo/todoList.jsp?delflag=2';
				}
				if(url!=''){
					Menu.addFavoriteMenu(param.baseUrl, selectNode.text, url);
				}
			}
		}]
	});

	// 绑定树右键菜单
	todoTree.on('contextmenu',function(node,e){
		if(!node.isLeaf() || node.id=='0'){
			todoTreeContextMenu.items.get(4).disable();
		}else{
			todoTreeContextMenu.items.get(4).enable();
		}
		e.preventDefault();
		node.select();
		todoTreeContextMenu.showAt(e.getXY());
	});
	/**
	* 修改分类
	*/
	function editTodoCategory(categoryId, categoryName){
		Ext.Msg.prompt('修改分类', '分类名称', function(btn, text){
			if('ok'==btn && text!=''){
				if(text.length<=20){
					var params = {};
					params.categoryId = categoryId;
					params.categoryName = text;
					// 发送请求
					Anynote.ajaxRequest({
						baseUrl: param.baseUrl,
						baseParams: params,
						action: '/todoCategoryAction.do?method=save',
						callback: function(jsonResult){
							Anynote.reloadTreeNode(todoTree, null, function(node){
								node.expand(true);
							});
						},
					showWaiting: true,
					failureMsg: '保存失败.'
					});
				}else{
					Ext.Msg.alert('提示', '列表名称不能大于20个汉字.');
				}
			}
		},null,null,categoryName);
	}
	
	// 任务树的单击事件
	function todoTreeClick(node){
		if(!node.isLeaf()) return;
		var nodeId = node.attributes.id;
		var tabTitle = "任务查询";
		var tabUrl = "";
		if(nodeId=='allTodo'){
			tabUrl = param.baseUrl + "/websrc/page/todo/todoList.jsp";
		}else if(nodeId=='uncompleteTodo'){
			tabUrl = param.baseUrl + "/websrc/page/todo/todoList.jsp?delflag=1";
		}else if(nodeId=='completeTodo'){
			tabUrl = param.baseUrl + "/websrc/page/todo/todoList.jsp?delflag=2";
		}else{
			tabUrl = param.baseUrl + "/websrc/page/todo/todoList.jsp?categoryId="+nodeId;
		}
		// 	打开Tab页
		Anynote.openTab(tabTitle, tabUrl);
	}
	return todoAccordion;
}

/*******************************************************************************
 * 笔记菜单
 *******************************************************************************/
Menu.getNoteAccordion = function(param){
	var noteAccordion = {
    	contentEl: 'noteAccordionDiv',
        title: '笔记管理',
        border: false,
        autoScroll: true,
        iconCls: 'folder_note',
    	tools: [{
			id: 'refresh',
			handler: function(){
    			Anynote.reloadTreeNode(Ext.getCmp('noteTreePanel'), null, null);
    		}
	    }]
    };
    
	// 笔记树
	var noteTree = new Ext.tree.TreePanel({
		id: 'noteTreePanel',
		renderTo: 'noteAccordionDiv',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: false,
	    containerScroll: true,
	    border: false,
	    rootVisible: false,
	    loader: new Ext.tree.TreeLoader({
	    	dataUrl:param.baseUrl + '/noteCategoryAction.do?method=getNoteCategoryTree',
	    	listeners: {
				load: function(loader,node,response){
					var jsonResult = Ext.decode(response.responseText);
					if(jsonResult && jsonResult.exceptionMsg && jsonResult.exceptionMsg!=''){
						Anynote.exceptionWindow(jsonResult.message, jsonResult.exceptionMsg);
					}
				}
			}
	    }),
	    root: new Ext.tree.AsyncTreeNode({
	        text: 'root',
	        id: 'root'
    	}),
    	listeners: {
			click: function(node) {
				noteTreeClick(node);
			},
			render: function(g){
			    g.dropZone = new Ext.dd.DropZone(g.getEl(), {
			        getTargetFromEvent: function(e) {
			    		var el = e.getTarget('.x-tree-node-el.x-tree-node-leaf');
			    		if($(el).attr('ext:tree-node-id')!='allCategory' && $(el).attr('ext:tree-node-id')!='rubbish'){
			    			return el;
			    		}else{
			    			return null;
			    		}
			        },
			        onNodeOver : function(target, dd, e, data){ 
			        	if(data.fromView == 'noteListDataView'){
			        		return Ext.dd.DropZone.prototype.dropAllowed;
			        	}else{
			        		return Ext.dd.DropZone.prototype.dropNotAllowed;
			        	}
			        },
			        onNodeDrop : function(target, dd, e, data){
			        	if(data.fromView == 'noteListDataView'){
			        		var params = {};
							params.moveCategoryId = $(target).attr('ext:tree-node-id');
							params.noteIds = data.noteIds;
							// 发送请求
							Anynote.ajaxRequest({
								baseUrl: param.baseUrl,
								baseParams: params,
								action: '/noteAction.do?method=move',
								callback: function(jsonResult){
									Anynote.reloadTreeNode(noteTree, null, function(node){
										node.expand(true);
									});
									Ext.getCmp('noteListDataView').getStore().reload();
								},
								showWaiting: true,
								failureMsg: '移动失败.'
							});
				            return true;
			        	}else{
			        		return false;
			        	}
			        	
			        }
			    });
			}
		}
	});
	noteTree.getRootNode().expand();
	
	// 树右键菜单
	var noteTreeContextMenu = new Ext.menu.Menu({
		items:[{
			text: '新建分类',
			iconCls: 'tag_blue_add',
			handler: function(node, e){
				var selectNode = noteTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="addNote" || selectNode.id=="allCategory" || selectNode.id=="rubbish") return;
				editCategory('', '');
			}
		},{
			text: '修改分类',
			iconCls: 'tag_blue_edit',
			handler: function(node, e){
				// 修改节点
				var selectNode = noteTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="addNote" || selectNode.id=="allCategory" || selectNode.id=="0" || selectNode.id=="rubbish") return;
				// 不能编辑根节点
				if(selectNode.id=='0') return;
				editCategory(selectNode.id, selectNode.attributes.attributes.textNoCount);
			}
		},{
			text: '删除分类',
			iconCls: 'tag_blue_delete',
			handler: function(){
				// 选中的节点
				var selectNode = noteTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="addNote" || selectNode.id=="allCategory" || selectNode.id=="0" || selectNode.id=="rubbish") return;
				Ext.Msg.confirm("警告", "确定要删除分类及其下的所有笔记吗？", function(btn){
					if(btn=="yes"){
						var params = {};
						params.categoryId = selectNode.id;
						// 发送请求
						Anynote.ajaxRequest({
							baseUrl: param.baseUrl,
							baseParams: params,
							action: '/noteCategoryAction.do?method=delete',
							callback: function(jsonResult){
								Anynote.reloadTreeNode(noteTree, null, function(node){
									node.expand(true);
								});
								Ext.getCmp('noteListDataView').getStore().reload();
							},
							showWaiting: true,
							failureMsg: '删除失败.'
						});
					}
				});
			}
		},'-',{
			text: '加入快捷菜单',
			iconCls: 'star',
			handler: function(node, e){
				var selectNode = noteTree.getSelectionModel().getSelectedNode();
				if(!selectNode.isLeaf()) return;
				var text = selectNode.attributes.attributes.textNoCount;
				var url = '';
				if(selectNode.id=='allCategory'){
					url = '/websrc/page/note/noteList.jsp?categoryName='+text;
				}else if(selectNode.id=='rubbish'){
					text = selectNode.text;
					url = '';
				}else{
					url = '/websrc/page/note/noteList.jsp?categoryId='+selectNode.id+'&categoryName='+text;
				}
				if(url!=''){
					Menu.addFavoriteMenu(param.baseUrl, text, url);
				}
			}
		}]
	});

	// 绑定树右键菜单
	noteTree.on('contextmenu',function(node,e){
		if(!node.isLeaf() || node.id=='0' || node.id=='rubbish'){
			noteTreeContextMenu.items.get(4).disable();
		}else{
			noteTreeContextMenu.items.get(4).enable();
		}
		e.preventDefault();
		node.select();
		noteTreeContextMenu.showAt(e.getXY());
	});
	
	/**
	* 修改分类
	*/
	function editCategory(categoryId, categoryName){
		Ext.Msg.prompt('修改分类', '分类名称', function(btn, text){
			if('ok'==btn && text!=''){
				if(text.length<=20){
					var params = {};
					params.categoryId = categoryId;
					params.categoryName = text;
					// 发送请求
					Anynote.ajaxRequest({
						baseUrl: param.baseUrl,
						baseParams: params,
						action: '/noteCategoryAction.do?method=save',
						callback: function(jsonResult){
							Anynote.reloadTreeNode(noteTree, null, function(node){
								node.expand(true);
							});
							
						},
					showWaiting: true,
					failureMsg: '保存失败.'
					});
				}else{
					Ext.Msg.alert('提示', '分类名称不能大于20个汉字.');
				}
			}
		},null,null,categoryName);
	}
	
	// 笔记树的单击事件
	function noteTreeClick(node){
		if(!node.isLeaf()) return;
		var nodeId = node.attributes.id;
		var tabTitle = "";
		var tabUrl = "";
		if(nodeId=='rubbish'){
			tabTitle = "回收站";
			tabUrl = param.baseUrl + "/websrc/page/note/rubbishNote.jsp";
		}else if (nodeId=="allCategory"){
			nodeId="";
			tabTitle = "笔记查询";
			tabUrl = param.baseUrl + "/websrc/page/note/noteList.jsp?categoryId="+nodeId+"&categoryName="+node.attributes.attributes.textNoCount;
		}else if (nodeId=="addNote"){
			addNoteWindow = new Ext.Window({
				title: '添加笔记',
				width: 800,
				height: 500,
				plain: true,
				modal: true,
				maximizable: false,
				resizable: false,
				layout:'fit',
				autoLoad:{url:param.baseUrl + '/websrc/page/note/addNote.jsp',scripts:true,nocache:true}
			}).show();
		}else{
			tabTitle = "笔记查询";
			tabUrl = param.baseUrl + "/websrc/page/note/noteList.jsp?categoryId="+nodeId+"&categoryName="+node.attributes.attributes.textNoCount;
		}
		
		if (nodeId=="addNote"){
			return;
		}else{
			// 	打开Tab页
			Anynote.openTab(tabTitle, tabUrl);
		}
	}
	return noteAccordion;
}

/*******************************************************************************
 * 订阅菜单
 *******************************************************************************/
Menu.getFeedAccordion = function(param){
	var feedAccordion = {
    	contentEl: 'feedAccordionDiv',
        title: '订阅管理',
        border: false,
        autoScroll: true,
        iconCls: 'folder_feed',
    	tools: [{
			id: 'refresh',
			handler: function(){
    			Anynote.reloadTreeNode(Ext.getCmp("feedTreePanel"), null, null);
    		}
	    }]
    };

	// 订阅树Loader
	var feedTreeLoader = new Ext.tree.TreeLoader({
	    dataUrl:param.baseUrl + '/feedAction.do?method=getFeedTree',
	    listeners: {
			beforeload: function(loader,node){
				this.baseParams.parentId = node.id;
			},
			load: function(loader,node,response){
				var jsonResult = Ext.decode(response.responseText);
				if(jsonResult && jsonResult.exceptionMsg && jsonResult.exceptionMsg!=''){
					Anynote.exceptionWindow(jsonResult.message, jsonResult.exceptionMsg);
				}
			}
		}
	});
	// 订阅树
	var feedTree = new Ext.tree.TreePanel({
		id: 'feedTreePanel',
		renderTo: 'feedAccordionDiv',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    border: false,
	    rootVisible: false,
	    loader: feedTreeLoader,
	    root: new Ext.tree.AsyncTreeNode({
			nodeType: 'async',
	        text: 'root',
	        draggable: false,
	        id: '-1'
    	}),
    	listeners: {
    		click: function(node) {feedTreeClick(node);},
    		dblclick: function(node) {feedTreeClick(node);}
		}
	});
	feedTree.getRootNode().expand();
	
	// 树右键菜单
	var feedTreeContextMenu = new Ext.menu.Menu({
		items:[{
			text: '更新订阅',
			iconCls: 'refresh',
			handler: function(node, e){
				var selectNode = feedTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="feedFavorite") return;
				var params = {};
				params.feedId = selectNode.id;
				// 发送请求
				Anynote.ajaxRequest({
					baseUrl: param.baseUrl,
					baseParams: params,
					action: '/feedAction.do?method=refreshFeed',
					callback: function(jsonResult){
					},
					showWaiting: true,
					failureMsg: '更新失败.'
				});
			}
		},'-',{
			text: '新建目录',
			iconCls: 'folder_add',
			handler: function(node, e){
				var selectNode = feedTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="feedFavorite") return;
				if(selectNode.isLeaf()){
					Ext.Msg.alert('提示', '请选择目录节点.');
					return;
				}else{
					// 创建目录
					editFeed('', '', '', '0', selectNode.id, '0');
				}
			}
		},{
			text: '新建订阅',
			iconCls: 'feed_add',
			handler: function(node, e){
				var selectNode = feedTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="feedFavorite") return;
				if(selectNode.isLeaf()){
					Ext.Msg.alert('提示', '请选择目录节点.');
					return;
				}else{
					// 创建订阅
					editFeed('', '', '', '50', selectNode.id, '1');
				}
			}
		},{
			text: '修改订阅',
			iconCls: 'feed_edit',
			handler: function(node, e){
				// 修改节点
				var selectNode = feedTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="feedFavorite" || selectNode.id=="0") return;
				// 不能编辑根节点
				if(selectNode.id=='0') return;
				var isleaf = '';
				if(selectNode.isLeaf()){
					 isleaf = '1';
				}else{
					isleaf = '0';
				}
				editFeed(selectNode.id, selectNode.text, selectNode.attributes.attributes.feedUrl, selectNode.attributes.attributes.feedCount, selectNode.parentNode.id, isleaf);
			}
		},{
			text: '删除订阅',
			iconCls: 'feed_delete',
			handler: function(){
				// 选中的节点
				var selectNode = feedTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="feedFavorite") return;
				Ext.Msg.confirm("警告", "确定要删除吗？", function(btn){
					if(btn=="yes"){
						var params = {};
						params.feedId = selectNode.id;
						// 发送请求
						Anynote.ajaxRequest({
							baseUrl: param.baseUrl,
							baseParams: params,
							action: '/feedAction.do?method=delete',
							callback: function(jsonResult){
								feedTree.getLoader().load(selectNode.parentNode, function(node){
									node.expand();
								});
								if(Anynote.tabPanel.getActiveTab().getId()=='feedViewerTab'){
									Anynote.tabPanel.remove(Anynote.tabPanel.getActiveTab());
								}
							},
							showWaiting: true,
							failureMsg: '删除失败.'
						});
					}
				});
			}
		},'-',{
			text: '导入订阅',
			iconCls: 'folder_import',
			handler: function(){
				var selectNode = feedTree.getSelectionModel().getSelectedNode();
				var importFeedWindow = new Ext.Window({
					title: '导入订阅列表(OPML)',
					width: 400,
					height: 120,
					modal: true,
					maximizable: false,
					resizable: false,
					layout:'fit',
					plain: true,
					items: [
						new Ext.FormPanel({
							id: 'importFeedFormPanel',
						    labelWidth: 80,
							fileUpload: true,
						    border: false,
						    buttonAlign: 'center',
						    bodyStyle: 'padding:10px;background-color:transparent;',
						    url: '',
						    items:[{
					            xtype: 'fileuploadfield',
					            fieldLabel: '订阅列表文件',
					            name: 'feedFile',
					            buttonText: '选择...',
					            anchor: '98%',
					            allowBlank: false
						    }],
							buttons: [{
						        text:'导入',
						        handler: function(){
									var form = Ext.getCmp('importFeedFormPanel').getForm();
									var selectNode = feedTree.getSelectionModel().getSelectedNode();
									if(form.isValid()){
										var waiting = Ext.Msg.wait('正在处理，请稍等...','','');
										form.submit({
						                    url: param.baseUrl + '/feedAction.do?method=importOpml&feedId='+selectNode.id,
						                    success: function(form, action){
												waiting.hide();
												importFeedWindow.close();
												feedTree.getLoader().load(selectNode, function(node){
													node.expand();
												});
						                    },
						                    failure: function(form, action){
						                    	waiting.hide();
						                    	Ext.Msg.alert('错误', '导入异常，请检查文件是否正确.');
						                    }
						                });
					                }
						    	}
						    },{
						        text: '取消',
						        handler: function(){
						    		importFeedWindow.close();
						        }
						    }]
						})
					]
				}).show();
			}
		},{
			text: '导出订阅',
			iconCls: 'folder_export',
			handler: function(){
				var selectNode = feedTree.getSelectionModel().getSelectedNode();
				//下载链接
				document.getElementById("downloadForm").action = param.baseUrl+"/feedAction.do?method=exportOpml&feedId="+selectNode.id;
				$("#downloadForm").submit();
			}
		},'-',{
			text: '加入快捷菜单',
			iconCls: 'star',
			handler: function(node, e){
				var selectNode = feedTree.getSelectionModel().getSelectedNode();
				if(!selectNode.isLeaf()) return;
				var text = selectNode.text;
				var url = '';
				if(selectNode.id=='feedFavorite'){
					url = '/websrc/page/feedViewer/feedFavorite.jsp';
				}else{
					var feedId = selectNode.id;
					var feedUrl = selectNode.attributes.attributes.feedUrl;
					var feedCount = selectNode.attributes.attributes.feedCount;
					url = "/websrc/page/feedViewer/feedViewer.jsp?feedId="+feedId+"&feedUrl="+feedUrl+"&feedCount="+feedCount+"&feedName="+text;
				}
				if(url!=''){
					Menu.addFavoriteMenu(param.baseUrl, text, url);
				}
			}
		}]
	});

	// 绑定树右键菜单
	feedTree.on('contextmenu',function(node,e){
		if(!node.isLeaf()){
			feedTreeContextMenu.items.get(7).enable();
			feedTreeContextMenu.items.get(8).enable();
			feedTreeContextMenu.items.get(10).disable();
		}else{
			feedTreeContextMenu.items.get(7).disable();
			feedTreeContextMenu.items.get(8).disable();
			feedTreeContextMenu.items.get(10).enable();
		}
		e.preventDefault();
		node.select();
		feedTreeContextMenu.showAt(e.getXY());
	});

	// 拖动之前事件
	feedTree.on('beforenodedrop',function(e){
		// 如果拖动的是根节点，取消操作
		var nodeId = e.dropNode.id;
		if(nodeId=="0" || nodeId=="feedFavorite"){
			return false;
		}
		// 如果拖动方式不是‘append’，取消操作
		if(e.point!='append'){
			return false;
		}
	});
	// 拖动事件
	feedTree.on('nodedrop',function(e){
		if(e.point!='append'){
			return false;
		}
		var feedId = e.dropNode.id;
		var parentId = e.target.id;
		// 发送保存请求
		Anynote.ajaxRequest({
			baseUrl: param.baseUrl,
			baseParams: {feedId:feedId, parentId:parentId},
			action: '/feedAction.do?method=save',
			callback: function(jsonResult){
			},
			showWaiting: true,
			failureMsg: '保存失败.'
		});
	});

	// 编辑订阅
	function editFeed(feedId, feedName, feedUrl, feedCount, parentId, isleaf){
		editFeedWindow = new Ext.Window({
			id: 'editFeedWindow',
			title: '编辑订阅',
			width: 300,
			height: 150,
			modal: true,
			maximizable: false,
			resizable: false,
			layout:'fit',
			plain: true,
			autoLoad:{
				url:param.baseUrl + '/websrc/page/feedViewer/editFeed.jsp',
				params: {feedId:feedId, feedName:feedName, feedUrl:feedUrl, feedCount:feedCount, parentId:parentId, isleaf:isleaf},
				scripts:true,
				nocache:true
			}
		});
		editFeedWindow.show();
	}

	// 订阅树的单击事件
	function feedTreeClick(node){
		if(!node.isLeaf()) return;
		var nodeId = node.attributes.id;
		var tabTitle = "";
		var tabUrl = "";
		// 	打开Tab页
		if(nodeId=="feedFavorite"){
			tabTitle = "订阅收藏夹";
			tabUrl = param.baseUrl + "/websrc/page/feedViewer/feedFavorite.jsp";
		}else{
			var feedId = nodeId;
			var feedUrl = node.attributes.attributes.feedUrl;
			var feedCount = node.attributes.attributes.feedCount;
			tabTitle = "订阅查询";
			tabUrl = param.baseUrl + "/websrc/page/feedViewer/feedViewer.jsp?feedId="+feedId+"&feedUrl="+feedUrl+"&feedCount="+feedCount+"&feedName="+node.text;
		}
		// 打开Tab页
		Anynote.openTab(tabTitle, tabUrl);
	}
	return feedAccordion;
}

/*******************************************************************************
 * 相册菜单
 *******************************************************************************/
Menu.getPictureAccordion = function(param){
	var pictureAccordion = {
    	contentEl: 'pictureAccordionDiv',
        title: '相册管理',
        border: false,
        autoScroll: true,
        iconCls: 'folder_picture',
    	tools: [{
			id: 'refresh',
			handler: function(){
    			Anynote.reloadTreeNode(Ext.getCmp("albumTreePanel"), null, null);
    		}
	    }]
    };

	// 相册树Loader
	var albumTreeLoader = new Ext.tree.TreeLoader({
	    dataUrl:param.baseUrl + '/albumAction.do?method=getAlbumTree',
	    listeners: {
			beforeload: function(loader,node,response){
				this.baseParams.parentId = node.id
			},
			load: function(loader,node,response){
				var jsonResult = Ext.decode(response.responseText);
				if(jsonResult && jsonResult.exceptionMsg && jsonResult.exceptionMsg!=''){
					Anynote.exceptionWindow(jsonResult.message, jsonResult.exceptionMsg);
				}
			}
		}
	});
	// 相册树Panel
	var albumTree = new Ext.tree.TreePanel({
		id: 'albumTreePanel',
		renderTo: 'pictureAccordionDiv',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    border: false,
	    loader: albumTreeLoader,
	    rootVisible: false,
	    root: {
	        nodeType: 'async',
	        text: 'root',
	        draggable: false,
	        id: '-1'
    	},
    	listeners: {
	    		click: function(node) {albumTreeClick(node);},
	    		dblclick: function(node) {albumTreeClick(node);}
		}
	});
    albumTree.getRootNode().expand();

 	// 树编辑器
	var albumTreeEditor = new Ext.tree.TreeEditor(albumTree,{
		completeOnEnter: true,
		ignoreNoChange: true,
		cancelOnEsc: true
	});
	
	// 树右键菜单
	var albumTreeContextMenu = new Ext.menu.Menu({
		items:[{
			text: '上传图片',
			iconCls: 'upload',
			handler: function(node, e){
				var selectNode = albumTree.getSelectionModel().getSelectedNode();
				if(!selectNode.isLeaf() || selectNode.id=="allPicture"){
					Ext.Msg.alert('提示', '请选择相册节点.');
					return;
				}else{
					// 打开图片上传窗口
					Anynote.uploadFile({
						baseUrl: param.baseUrl,
						baseParams: {albumId:selectNode.id,userId:param.userId},
						action: '/pictureAction.do?method=upload',
						title: '图片上传 - '+selectNode.text,
						fileTypes: param.fileTypes,
						fileSize: param.fileSize,
						fileCount: param.fileCount,
						minTargetId: 'minUploadWinDiv'
					});
				}
			}
		},{
			text: '新建目录',
			iconCls: 'folder_add',
			handler: function(node, e){
				var selectNode = albumTree.getSelectionModel().getSelectedNode();
				if(selectNode.isLeaf()){
					Ext.Msg.alert('提示', '请选择目录节点.');
					return;
				}else{
					// 创建新节点
					var newNode = new Ext.tree.TreeNode({
						text:'',
						leaf: false,
						attributes: {type:'new'}
					});
					albumTreeEditor.editNode = newNode;
					// 展开结点并添加子节点为编辑状态
					selectNode.expand(false,true,function(){
						selectNode.appendChild(newNode);
						setTimeout(function(){
							albumTreeEditor.startEdit(newNode.ui.textNode);
						},10);
					});
				}
			}
		},{
			text: '新建相册',
			iconCls: 'picture_add',
			handler: function(node, e){
				var selectNode = albumTree.getSelectionModel().getSelectedNode();
				if(selectNode.isLeaf()){
					Ext.Msg.alert('提示', '请选择目录节点.');
					return;
				}else{
					// 创建新节点
					var newNode = new Ext.tree.TreeNode({
						text:'',
						leaf: true,
						type: 'create',
						attributes: {type:'new'}
					});
					albumTreeEditor.editNode = newNode;
					// 展开节点并添加子节点为编辑状态
					selectNode.expand(false,true,function(){
						selectNode.appendChild(newNode);
						setTimeout(function(){
							albumTreeEditor.startEdit(newNode.ui.textNode);
						},10);
					});
				}
			}
		},{
			text: '修改相册',
			iconCls: 'picture_edit',
			handler: function(node, e){
				var selectNode = albumTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="allPicture" || selectNode.id=="0") return;
				albumTreeEditor.editNode = selectNode;
				albumTreeEditor.startEdit(selectNode.ui.textNode);
			}
		},{
			text: '删除相册',
			iconCls: 'picture_delete',
			handler: function(){
				// 选中的节点
				var selectNode = albumTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="allPicture") return;
				Ext.Msg.confirm("警告", "确定要删除相册及其下的所有图片吗？", function(btn){
					if(btn=="yes"){
						var params = {};
						params.albumId = selectNode.id;
						// 发送保存请求
						Anynote.ajaxRequest({
							baseUrl: param.baseUrl,
							baseParams: params,
							action: '/albumAction.do?method=delete',
							callback: function(jsonResult){
								albumTree.getLoader().load(selectNode.parentNode, function(node){
									node.expand();
								});
							},
							showWaiting: true,
							failureMsg: '删除失败.'
						});
					}
				});
			}
		},'-',{
			text: '加入快捷菜单',
			iconCls: 'star',
			handler: function(node, e){
				var selectNode = albumTree.getSelectionModel().getSelectedNode();
				if(!selectNode.isLeaf()) return;
				var text = selectNode.text;
				var url = '';
				if(selectNode.id=="allPicture"){
					url = '/websrc/page/picture/pictureList.jsp?albumName='+text;
				}else{
					url = '/websrc/page/picture/pictureList.jsp?albumId='+selectNode.id+'&albumName='+text;
				}
				if(url!=''){
					Menu.addFavoriteMenu(param.baseUrl, text, url);
				}
			}
		}]
	});

	// 绑定树右键菜单
	albumTree.on('contextmenu',function(node,e){
		if(!node.isLeaf()){
			albumTreeContextMenu.items.get(6).disable();
		}else{
			albumTreeContextMenu.items.get(6).enable();
		}
		e.preventDefault();
		node.select();
		albumTreeContextMenu.showAt(e.getXY());
	});

	// 树编辑器-开始编辑前
	albumTreeEditor.on('beforestartedit', function(treeEditor,newText,oldText){
		var nodeId = treeEditor.editNode.id;
		if(nodeId=="allPicture" || nodeId=="0"){
			return false;
		}
	});

	// 树编辑器-完成前事件
	albumTreeEditor.on('beforecomplete', function(treeEditor,newText,oldText){
		if(newText==''){
			Ext.Msg.alert('提示','名称不能为空.', function(){
				treeEditor.cancelEdit();
				if(treeEditor.editNode.attributes.attributes && treeEditor.editNode.attributes.attributes.type){
					treeEditor.editNode.remove();
				}
			});
			return false;
		}else if(newText.length > 20){
			Ext.Msg.alert('提示', '名称最大长度不能超过20个字符.');
			return false;
		}else{
			return true;
		}
	});

	// 树编辑器-完成后事件
	albumTreeEditor.on('complete', function(treeEditor,newText,oldText){
		if(newText==oldText) return;
		// 相册ID
		if(treeEditor.editNode.attributes.attributes && treeEditor.editNode.attributes.attributes.type){
			var albumId = "";
		}else{
			var albumId = treeEditor.editNode.id;
		}
		// 相册名称
		var albumName = newText;
		// 父节点ID
		var parentId = treeEditor.editNode.parentNode.id;
		// 是否叶子
		if(treeEditor.editNode.isLeaf()){
			var isleaf = '1';
		}else{
			var isleaf = '0';
		}
		// 发送请求
		Anynote.ajaxRequest({
			baseUrl: param.baseUrl,
			baseParams: {albumId:albumId, albumName:albumName, parentId:parentId, isleaf:isleaf},
			action: '/albumAction.do?method=save',
			callback: function(jsonResult){
				albumTree.getLoader().load(treeEditor.editNode.parentNode, function(node){
					node.expand();
				});
			},
			showWaiting: true,
			failureMsg: '保存失败.'
		});
	});

	// 拖动之前事件
	albumTree.on('beforenodedrop',function(e){
		// 如果拖动的是根节点，取消操作
		var nodeId = e.dropNode.id;
		if(nodeId=="allPicture" || nodeId=="0"){
			return false;
		}
		// 如果拖动方式不是‘append’，取消操作
		if(e.point!='append'){
			return false;
		}
	});
	// 拖动事件
	albumTree.on('nodedrop',function(e){
		if(e.point!='append'){
			return false;
		}
		var albumId = e.dropNode.id;
		var parentId = e.target.id;
		// 发送请求
		Anynote.ajaxRequest({
			baseUrl: param.baseUrl,
			baseParams: {albumId:albumId, parentId:parentId},
			action: '/albumAction.do?method=save',
			callback: function(jsonResult){
			},
			showWaiting: true,
			failureMsg: '保存失败.'
		});
	});

 	// 相册分类树的单击事件
	function albumTreeClick(node){
		if(!node.isLeaf()) return;
		var nodeId = node.attributes.id;
		var tabTitle = "";
		var tabUrl = "";
		
		if(nodeId=="allPicture"){
			nodeId="";
		}
		tabTitle = "图片查询";
		tabUrl = param.baseUrl + "/websrc/page/picture/pictureList.jsp?albumId="+nodeId+"&albumName="+node.text;
		// 打开Tab页
		Anynote.openTab(tabTitle, tabUrl);
	}
	return pictureAccordion;
}

/*******************************************************************************
 * 账目菜单
 *******************************************************************************/
Menu.getAccountAccordion = function(param){
	var accountAccordion = {
    	contentEl: 'accountAccordionDiv',
        title: '账目管理',
        border: false,
        autoScroll: true,
        iconCls: 'folder_table',
    	tools: [{
			id: 'refresh',
			handler: function(){
    			Anynote.reloadTreeNode(Ext.getCmp("accountTreePanel"), null, null);
    		}
	    }]
    };

	// 账目树
	var accountTree = new Ext.tree.TreePanel({
		id: 'accountTreePanel',
		renderTo: 'accountAccordionDiv',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: false,
	    containerScroll: true,
	    border: false,
	    dataUrl:'',
	    rootVisible: false,
	    loader: new Ext.tree.TreeLoader({
	    	dataUrl:param.baseUrl + '/accountBookAction.do?method=getAccountBookTree',
	    	listeners: {
				load: function(loader,node,response){
					var jsonResult = Ext.decode(response.responseText);
					if(jsonResult && jsonResult.exceptionMsg && jsonResult.exceptionMsg!=''){
						Anynote.exceptionWindow(jsonResult.message, jsonResult.exceptionMsg);
					}
				}
			}
	    }),
	    root: new Ext.tree.AsyncTreeNode({
	        text: 'root',
	        id: 'root'
    	}),
    	listeners: {click: function(node) {accountTreeClick(node);}}
	});
	accountTree.getRootNode().expand();
	
	// 树右键菜单
	var accountTreeContextMenu = new Ext.menu.Menu({
		items:[{
			text: '新建账本',
			iconCls: 'report_add',
			handler: function(node, e){
				var selectNode = accountTree.getSelectionModel().getSelectedNode();
				editAccountBook('', '');
			}
		},{
			text: '修改账本',
			iconCls: 'report_edit',
			handler: function(node, e){
				// 修改节点
				var selectNode = accountTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="allAccount" || selectNode.id=="0" || selectNode.id=="accountCategory") return;
				// 不能编辑根节点
				if(selectNode.id=='0') return;
				editAccountBook(selectNode.id, selectNode.text);
			}
		},{
			text: '删除账本',
			iconCls: 'report_delete',
			handler: function(){
				// 选中的节点
				var selectNode = accountTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="allAccount" || selectNode.id=="0" || selectNode.id=="accountCategory") return;
				Ext.Msg.confirm("警告", "确定要删除账本及其下的所有账目信息吗？", function(btn){
					if(btn=="yes"){
						var params = {};
						params.accountBookId = selectNode.id;
						// 发送请求
						Anynote.ajaxRequest({
							baseUrl: param.baseUrl,
							baseParams: params,
							action: '/accountBookAction.do?method=delete',
							callback: function(jsonResult){
								Anynote.reloadTreeNode(accountTree, null, function(node){
									node.expand(true);
								});
							},
							showWaiting: true,
							failureMsg: '删除失败.'
						});
					}
				});
			}
		},'-',{
			text: '加入快捷菜单',
			iconCls: 'star',
			handler: function(node, e){
				var selectNode = accountTree.getSelectionModel().getSelectedNode();
				if(!selectNode.isLeaf()) return;
				var text = selectNode.text;
				var url = '';
				if(selectNode.id=='accountCategory'){
					url = '/websrc/page/account/accountCategoryList.jsp';
				}else if(selectNode.id=='allAccount'){
					url = '/websrc/page/account/accountList.jsp?accountBookName='+text;
				}else{
					url = '/websrc/page/account/accountList.jsp?accountBookId='+selectNode.id+'&accountBookName='+text;
				}
				if(url!=''){
					Menu.addFavoriteMenu(param.baseUrl, text, url);
				}
			}
		}]
	});

	// 绑定树右键菜单
	accountTree.on('contextmenu',function(node,e){
		if(!node.isLeaf()){
			accountTreeContextMenu.items.get(4).disable();
		}else{
			accountTreeContextMenu.items.get(4).enable();
		}
		e.preventDefault();
		node.select();
		accountTreeContextMenu.showAt(e.getXY());
	});
	
	/**
	* 修改账本
	*/
	function editAccountBook(accountBookId, accountBookName){
		Ext.Msg.prompt('修改账本', '账本名称', function(btn, text){
			if('ok'==btn && text!=''){
				if(text.length<=20){
					var params = {};
					params.accountBookId = accountBookId;
					params.accountBookName = text;
					// 发送请求
					Anynote.ajaxRequest({
						baseUrl: param.baseUrl,
						baseParams: params,
						action: '/accountBookAction.do?method=save',
						callback: function(jsonResult){
							Anynote.reloadTreeNode(accountTree, null, function(node){
								node.expand(true);
							});
						},
					showWaiting: true,
					failureMsg: '保存失败.'
					});
				}else{
					Ext.Msg.alert('提示', '账本名称最大长度不能超过20个字符.');
				}
			}
		},null,null,accountBookName);
	}
	
	//打开日期选择Win
	function openReportWin(tabTitle,tabUrl,type){
	    var format = "";
	    if(type == 'year'){
	    	format = "Y";
	    }
	    if(type == 'month'){
	    	format = "Y-m";
	    }
	    if(type == 'day'){
	    	format = "Y-m-d";
	    }
		var reportFormPanel = new Ext.FormPanel({
	          id: 'reportFormPanel',
	          labelWidth: 40,
	          border: false,
	          buttonAlign: 'center',
	          style: 'border-bottom:0px;',
		      bodyStyle: 'padding:10px;background-color:transparent;',
	          items: [
			              new Ext.form.DateField ({//日期
		            		inputType: 'textfiled',
			                id: 'dateOfReport',
			                name: 'dateOfReport',
			                fieldLabel: '日期',
			                anchor:'98%',
			                allowBlank:false,
			                format:format
			           })
	          ],
	          buttons: [{
				id:'login-button',
              text:'生成图表',
              handler: function(){
					var dateOfReport = $("#dateOfReport").val();
					if(dateOfReport==""){
						Ext.Msg.alert('提示', '请输入日期.');
					}else{
						// 发送请求
						Anynote.ajaxRequest({
							baseUrl: param.baseUrl,
							baseParams: {dateOfReport:dateOfReport,type:type},
							action: '/accountAction.do?method=selectReportData',
							callback: function(jsonResult){
								reportWin.close();
								Anynote.openTab(tabTitle, tabUrl);
							}
						});
					}
          	}
          },{
              text: '取消',
              handler: function(){
          		dateTodoFormPanel.getForm().reset();
              }
          }]
	    });
	    
	    var reportWin = new Ext.Window({
	        title: tabTitle,
			width: 200,
			height: 100,
			modal: true,
			maximizable: false,
			resizable: true,
			layout:'fit',
			bodyStyle: 'background-color:#CFC0FF;',
			items:[reportFormPanel]
	    });
	    reportWin.show();
	}

	// 账目分类树的单击事件
	function accountTreeClick(node){
		if(!node.isLeaf()) return;
		var nodeId = node.attributes.id;
		var tabTitle = "账目查询";
		var tabUrl = "";
		if(nodeId == 'yearReport'){
			tabTitle = "年收支图表";
			tabUrl = param.baseUrl + "/websrc/page/account/report/yearReportFrame.jsp";
			openReportWin(tabTitle,tabUrl,'year');
			return;
		}else if(nodeId=='monthReport'){
			tabTitle = "月收支图表";
			tabUrl = param.baseUrl + "/websrc/page/account/report/monthReportFrame.jsp";
			openReportWin(tabTitle,tabUrl,'month');
			return;
		}else if(nodeId=='dayReport'){
			tabTitle = "日支出图表";
			tabUrl = param.baseUrl + "/websrc/page/account/report/dayReportFrame.jsp";
			openReportWin(tabTitle,tabUrl,'day');
			return;
		}else if(nodeId=='accountCategory'){
			tabTitle = "收支项目";
			tabUrl = param.baseUrl + "/websrc/page/account/accountCategoryList.jsp";
		}else if(nodeId=='allAccount'){
			tabUrl = param.baseUrl + "/websrc/page/account/accountList.jsp?accountBookName="+node.text;
		}else{
			tabUrl = param.baseUrl + "/websrc/page/account/accountList.jsp?accountBookId="+nodeId+"&accountBookName="+node.text;
		}
		// 	打开Tab页
		Anynote.openTab(tabTitle, tabUrl);
	}
	return accountAccordion;
}

/*******************************************************************************
 * 文档菜单
 *******************************************************************************/
Menu.getDocumentAccordion = function(param){
	var documentAccordion = {
    	contentEl: 'documentAccordionDiv',
        title: '文档管理',
        border: false,
        autoScroll: true,
        iconCls: 'folder_page',
    	tools: [{
			id: 'refresh',
			handler: function(){
    			Anynote.reloadTreeNode(Ext.getCmp("documentTreePanel"), null, null);
    		}
	    }]
    };

	// 文档树Loader
	var documentTreeLoader = new Ext.tree.TreeLoader({
	    dataUrl:param.baseUrl + '/documentAction.do?method=getDocumentTree',
	    listeners: {
			beforeload: function(loader,node){
				this.baseParams.parentId = node.id;
			},
			load: function(loader,node,response){
				var jsonResult = Ext.decode(response.responseText);
				if(jsonResult && jsonResult.exceptionMsg && jsonResult.exceptionMsg!=''){
					Anynote.exceptionWindow(jsonResult.message, jsonResult.exceptionMsg);
				}
			}
		}
	});
	// 文档树
	var documentTree = new Ext.tree.TreePanel({
		id: 'documentTreePanel',
		renderTo: 'documentAccordionDiv',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    border: false,
	    rootVisible: false,
	    loader: documentTreeLoader,
	    root: new Ext.tree.AsyncTreeNode({
			nodeType: 'async',
	        text: 'root',
	        draggable: false,
	        id: '-1'
    	}),
    	listeners: {
    		click: function(node) {documentTreeClick(node);},
    		dblclick: function(node) {documentTreeClick(node);}
		}
	});
	documentTree.getRootNode().expand();
	
	// 树编辑器
	var documentTreeEditor = new Ext.tree.TreeEditor(documentTree,{
		completeOnEnter: true,
		ignoreNoChange: true,
		cancelOnEsc: true
	});
	
	// 树右键菜单
	var documentTreeContextMenu = new Ext.menu.Menu({
		items:[{
			text: '上传文档',
			iconCls: 'upload',
			handler: function(node, e){
				var selectNode = documentTree.getSelectionModel().getSelectedNode();
				// 打开文档上传窗口
				Anynote.uploadFile({
					baseUrl: param.baseUrl,
					baseParams: {documentId:selectNode.id,userId:param.userId},
					action: '/documentAction.do?method=upload',
					title: '文档上传 - '+selectNode.text,
					fileTypes: param.fileTypes,
					fileSize: param.fileSize,
					fileCount: param.fileCount,
					minTargetId: 'minUploadWinDiv'
				});
			}
		},{
			text: '创建目录',
			iconCls: 'folder_add',
			handler: function(node, e){
				var selectNode = documentTree.getSelectionModel().getSelectedNode();
				if(selectNode.isLeaf()){
					Ext.Msg.alert('提示', '请选择目录节点.');
					return;
				}else{
					// 创建新节点
					var newNode = new Ext.tree.TreeNode({
						text:'',
						leaf: false,
						attributes: {type:'new'}
					});
					documentTreeEditor.editNode = newNode;
					// 展开结点并添加子节点为编辑状态
					selectNode.expand(false,true,function(){
						selectNode.appendChild(newNode);
						setTimeout(function(){
							documentTreeEditor.startEdit(newNode.ui.textNode);
						},10);
					});
				}
			}
		},{
			text: '修改目录',
			iconCls: 'folder_edit',
			handler: function(node, e){
				var selectNode = documentTree.getSelectionModel().getSelectedNode();
				// 如果是根目录返回
				if(selectNode.id=="0") return;
				documentTreeEditor.editNode = selectNode;
				documentTreeEditor.startEdit(selectNode.ui.textNode);
			}
		},{
			text: '删除目录',
			iconCls: 'folder_delete',
			handler: function(){
				// 选中的节点
				var selectNode = documentTree.getSelectionModel().getSelectedNode();
				Ext.Msg.confirm("警告", "确定要删除目录及其下的所有文档吗？", function(btn){
					if(btn=="yes"){
						var params = {};
						params.documentIds = selectNode.id;
						// 发送请求
						Anynote.ajaxRequest({
							baseUrl: param.baseUrl,
							baseParams: params,
							action: '/documentAction.do?method=delete',
							callback: function(jsonResult){
								documentTree.getLoader().load(selectNode.parentNode, function(node){
									node.expand();
								});
							},
							showWaiting: true,
							failureMsg: '删除失败.'
						});
					}
				});
			}
		},'-',{
			text: '加入快捷菜单',
			iconCls: 'star',
			handler: function(node, e){
				var selectNode = documentTree.getSelectionModel().getSelectedNode();
				var url = '/websrc/page/document/documentList.jsp?documentId='+selectNode.id;
				Menu.addFavoriteMenu(param.baseUrl, selectNode.text, url);
			}
		}]
	});

	// 绑定树右键菜单
	documentTree.on('contextmenu',function(node,e){
		if(node.id=='0'){
			documentTreeContextMenu.items.get(5).enable();
		}else{
			documentTreeContextMenu.items.get(5).disable();
		}
		e.preventDefault();
		node.select();
		documentTreeContextMenu.showAt(e.getXY());
	});
	
	// 树编辑器-开始编辑前
	documentTreeEditor.on('beforestartedit', function(treeEditor,newText,oldText){
		var nodeId = treeEditor.editNode.id;
		if(nodeId=="0"){
			return false;
		}
	});

	// 树编辑器-完成前事件
	documentTreeEditor.on('beforecomplete', function(treeEditor,newText,oldText){
		if(newText==''){
			Ext.Msg.alert('提示','名称不能为空.', function(){
				treeEditor.cancelEdit();
				if(treeEditor.editNode.attributes.attributes && treeEditor.editNode.attributes.attributes.type){
					treeEditor.editNode.remove();
				}
			});
			return false;
		}else if(newText.length > 256){
			Ext.Msg.alert('提示','名称最大长度不能超过256个字符.');
			return false;
		}else{
			return true;
		}
	});

	// 树编辑器-完成后事件
	documentTreeEditor.on('complete', function(treeEditor,newText,oldText){
		if(newText==oldText) return;
		// 相册ID
		if(treeEditor.editNode.attributes.attributes && treeEditor.editNode.attributes.attributes.type){
			var documentId = "";
		}else{
			var documentId = treeEditor.editNode.id;
		}
		// 相册名称
		var documentName = newText;
		// 父节点ID
		var parentId = treeEditor.editNode.parentNode.id;
		// 是否叶子
		if(treeEditor.editNode.isLeaf()){
			var isleaf = '1';
		}else{
			var isleaf = '0';
		}
		// 发送请求
		Anynote.ajaxRequest({
			baseUrl: param.baseUrl,
			baseParams: {documentId:documentId, documentName:documentName, parentId:parentId, isleaf:'0'},
			action: '/documentAction.do?method=save',
			callback: function(jsonResult){
				documentTree.getLoader().load(treeEditor.editNode.parentNode, function(node){
					node.expand();
				});
			},
			showWaiting: true,
			failureMsg: '保存失败.'
		});
	});

	// 拖动之前事件
	documentTree.on('beforenodedrop',function(e){
		// 如果拖动的是根节点，取消操作
		var nodeId = e.dropNode.id;
		if(nodeId=="0"){
			return false;
		}
		// 如果拖动方式不是‘append’，取消操作
		if(e.point!='append'){
			return false;
		}
	});
	// 拖动事件
	documentTree.on('nodedrop',function(e){
		if(e.point!='append'){
			return false;
		}
		var documentId = e.dropNode.id;
		var parentId = e.target.id;
		// 发送保存请求
		Anynote.ajaxRequest({
			baseUrl: param.baseUrl,
			baseParams: {documentId:documentId, parentId:parentId},
			action: '/documentAction.do?method=save',
			callback: function(jsonResult){
			},
			showWaiting: true,
			failureMsg: '保存失败.'
		});
	});

	// 订阅树的单击事件
	function documentTreeClick(node){
		var nodeId = node.attributes.id;
		var tabTitle = "";
		var tabUrl = "";
		// 	打开Tab页
		tabTitle = "文档查询";
		tabUrl = param.baseUrl + "/websrc/page/document/documentList.jsp?documentId="+nodeId;
		// 打开Tab页
		Anynote.openTab(tabTitle, tabUrl);
	}
	return documentAccordion;
}


/*******************************************************************************
 * 用户菜单
 *******************************************************************************/
Menu.getSystemAccordion = function(param){
	var systemAccordion = {
       	contentEl: 'systemAccordionDiv',
		title: '系统管理',
		border: false,
		autoScroll: true,
		iconCls: 'folder_user',
    	tools: [{
			id: 'refresh',
			handler: function(){}
	    }]
    };
	
	// 如果是管理员，过滤掉系统设置菜单
	if(param.role=='2'){
		Menu.systemMenuData.pop();
	}

	// 用户树
	var systemTree = new Ext.tree.TreePanel({
		id: 'systemTreePanel',
		renderTo: 'systemAccordionDiv',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: false,
	    containerScroll: true,
	    border: false,
	    dataUrl:'',
	    rootVisible: false,
	    root: new Ext.tree.AsyncTreeNode({
	    	text: '账目树',
	    	children: Menu.systemMenuData
    	}),
    	listeners: {click: function(node) {systemTreeClick(node);}}
	});
	systemTree.getRootNode().expand();
	
	// 树右键菜单
	var systemTreeContextMenu = new Ext.menu.Menu({
		items:[{
			text: '加入快捷菜单',
			iconCls: 'star',
			handler: function(node, e){
				var selectNode = systemTree.getSelectionModel().getSelectedNode();
				var text = selectNode.text;
				var url = '';
				if(selectNode.id=='userList'){
					text = '用户查询';
					url = '/websrc/page/user/userList.jsp';
				}else if(selectNode.id=='setting'){
					text = '系统设置';
					url = '/websrc/page/system/setting.jsp';
				}
				Menu.addFavoriteMenu(param.baseUrl, text, url);
			}
		}]
	});

	// 绑定树右键菜单
	systemTree.on('contextmenu',function(node,e){
		e.preventDefault();
		node.select();
		systemTreeContextMenu.showAt(e.getXY());
	});
	
	function backup(){
		Ext.Msg.confirm("提示", "确定要备份数据库吗？", function(btn){
			if(btn=="yes"){
				// 发送请求
				Anynote.ajaxRequest({
					baseUrl: param.baseUrl,
					action: '/settingAction.do?method=backup',
					callback: function(jsonResult){
						
					},
					showWaiting: true,
					showMsg: true,
					successMsg: '备份数据库成功!',
					failureMsg: '备份数据库失败.'
				});
			}
		});
	}

	// 用户树的单击事件
	function systemTreeClick(node){
		if(!node.isLeaf()) return;
		var nodeId = node.attributes.id;
		var tabTitle = "";
		var tabUrl = "";
		if(nodeId=='userList'){
			tabTitle = "用户查询";
			tabUrl = param.baseUrl + "/websrc/page/user/userList.jsp";
		}else if(nodeId=='setting'){
			tabTitle = "系统设置";
			tabUrl = param.baseUrl + "/websrc/page/system/setting.jsp";
		}else if(nodeId=='backup'){
			backup();
			return;
		}
		// 	打开Tab页
		Anynote.openTab(tabTitle, tabUrl);
	}
	return systemAccordion;
}


/**
 * 将菜单加入到快捷菜单
 * @param baseUrl	baseUrl
 * @param text		菜单名称
 * @param url 		菜单URL
 */
Menu.addFavoriteMenu = function(baseUrl, text, url){
	if(baseUrl=='' || text=='' || url==''){
		Ext.Msg.alert('错误', '参数错误.');
		return;
	}else{
		var params = {};
		params.metaKey = 'favoriteMenu';
		params.metaValue = text+'::'+url;
		// 发送请求
		Anynote.ajaxRequest({
			baseUrl: baseUrl,
			baseParams: params,
			action: '/userMetaAction.do?method=addFavoriteMenu',
			callback: function(jsonResult){
				var homeTree = Ext.getCmp('homeTreePanel');
				Anynote.reloadTreeNode(homeTree, null, function(){
					homeTree.expandAll();
				});
			},
			showWaiting: true,
			failureMsg: '添加失败.'
		});
	}
}