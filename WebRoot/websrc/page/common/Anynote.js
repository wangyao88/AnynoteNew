/*******************************************************************************
 * Anynote 通用JS定义
 ******************************************************************************/

var Anynote = {};

/*******************************************************************************
 * 常量数据
 ******************************************************************************/
//性别常量
Anynote.SEX_DATA = [['1', '男'], ['2', '女']];

//风格常量
Anynote.THEME_DATA = [['ext-all.css','经典蓝色'],['xtheme-gray.css','简约灰色'],['xtheme-olive.css','清新绿色'],['xtheme-purple.css', '浪漫紫色']];

//默认风格
Anynote.THEME_DEFAULT = 'ext-all.css';

//星期
Anynote.WEEK_DATA_CN = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];

//分页工具条没有数据时显示信息
Anynote.PAGINGTOOLBAR_EMPTYMSG = "没有数据";

//分页工具条显示的分页信息
Anynote.PAGINGTOOLBAR_DISPLAYMSG = "显示 {0} - {1} 条 共 {2} 条";

// 弹出窗口宽度
Anynote.OPEN_WIN_WIDTH = 960;

// 弹出窗口高度
Anynote.OPEN_WIN_HEIGHT = 600;

/*******************************************************************************
 * 属性
 ******************************************************************************/
// 页面头部面板
Anynote.headerPanel = null;

// 页面左侧菜单面板
Anynote.menuPanel = null;

//页面右侧任务面板
Anynote.todoPanel = null;

//页面主区域Tab面板
Anynote.tabPanel = null;


/*******************************************************************************
 * 方法
 ******************************************************************************/
/**
 * 打开Tab页
 * @param tabPanel	tab面板对象
 * @param tabTitle	tab页名称
 * @param url		tab页链接地址
 */
Anynote.openTab = function(tabTitle, url){
	// 参数验证
	if(!Anynote.tabPanel || tabTitle=="" || url==""){
		Ext.Msg.alert("错误", "参数错误.", function(){return false;});
		return;
	}
	var tabId = Anynote.getTabIdByUrl(url);
	url = encodeURI(encodeURI(url));
	// tab页不存在的场合
	if(!Anynote.tabPanel.get(tabId)){
		var newTab = {
				id: tabId,
                title: tabTitle,
                layout: 'fit',
                closable: true,
                autoLoad: {url:url,scripts:true,nocache:true}
            };
		Anynote.tabPanel.add(newTab).show();
	}else{
		// tab页已经存在的场合
		var tab = Anynote.tabPanel.get(tabId);
		tab.getUpdater().update(url);
		Anynote.tabPanel.activate(tab);
	}
}


/**
 * Ajax请求方法
 * @param		settings	配置对象
 * 包括以下参数：baseUrl		项目根目录地址 必须
 * 				baseParams	参数对象
 * 				action		请求的Action地址 必须
 * 				callback	回调方法
 * 				reload		重新数据载入方法
 * 				showMsg		处理成功时，是否显示提示信息 true:显示 false:不显示 默认:false
 * 				successMsg	请求成功时的提示消息
 * 				failureMsg  请求失败时的提示消息
 * 				showWaiting 加载过程中是否显示进度条 true:显示 false:不显示 默认:false
 */
Anynote.ajaxRequest = function (settings){
	// 参数对象
	var params = {};
	if(settings.baseParams){
		params = settings.baseParams;
	}
	// 发送请求
	var waiting = null;
	if(settings.showWaiting == true){
		waiting = Ext.Msg.wait('正在处理，请稍等...','','');
	}
	Ext.Ajax.request({
		url: settings.baseUrl + settings.action,
		params: params,
		success: function(response, options){
			if(waiting != null){
				waiting.hide();
			}
			var jsonResult = Ext.decode(response.responseText);
			if(jsonResult.success==true){
				if(settings.showMsg==true){// 显示提示信息
					// 请求成功时的提示文字
					var successMsg = '操作成功.';
					if(jsonResult.message && jsonResult.message != ''){
						successMsg = jsonResult.message;
					}else if(settings.successMsg && settings.successMsg != ''){
						successMsg = settings.successMsg;
					}
					Ext.Msg.alert('提示', successMsg, function(){
						if(settings.reload){// 加载方法
							settings.reload(jsonResult);
						}
						if(settings.callback){// 回调方法
							settings.callback(jsonResult);
						}
					});
				}else{
					if(settings.reload){// 加载方法
						settings.reload(jsonResult);
					}
					if(settings.callback){// 回调方法
						settings.callback(jsonResult);
					}
				}
			}else{
				var message = '发生异常.';
				if(jsonResult.message && jsonResult.message != ''){// 后台设定的业务消息
					message = jsonResult.message;
				}else if(settings.failureMsg && settings.failureMsg!=''){// 前台指定的错误信息
					message = settings.failureMsg;
				}
				if(jsonResult.exceptionMsg && jsonResult.exceptionMsg != ''){// 有异常信息
					Anynote.exceptionWindow(message, jsonResult.exceptionMsg);
				}else{
					Ext.Msg.alert('错误', message);
				}
			}
		},
		failure: function(response, options){
			Ext.Msg.alert('错误', '请求超时.');
		}
	});
}

/**
 * 显示异常信息的窗口
 * @param exceptionMsg	异常信息
 */
Anynote.exceptionWindow = function(message,exceptionMsg){
	if(message=='') message = '发生异常!';
	if(exceptionMsg=='') exceptionMsg = '发生异常!';
	var exceptionWindow = Ext.getCmp('exceptionWindow');
	if (!exceptionWindow) {
		exceptionWindow = new Ext.Window({
			id: 'exceptionWindow',
			title: '错误',
			width: 400,
			autoHeight: true,
			modal: true,
			layout:'fit',
			items: [
			    new Ext.form.Label({
			    	html:'<div style="padding:5px;">'+message+'</div>'
			    }),
				new Ext.Panel({
					layout:'fit',
					title: '详细信息',
					collapsible: true,
					collapsed: true,
					height: 200,
					items:new Ext.form.TextArea({
						name: 'errorMsg',
						value: exceptionMsg
					})
				})
			]
		}).show();
	}else{
		exceptionWindow.show();
	}
}

/**
 * 打开文件上传窗口
 * @param		settings		配置对象
 * 包括以下参数：baseUrl			项目根目录地址 必须
 * 				baseParams		参数对象 必须 至少有一个userId参数
 * 				action			请求的Action地址 必须
 * 				title			窗口标题
 * 				minTargetId		窗口最小化到的目标对象ID
 * 				fileTypes		文件类型
 * 				fileSize		文件大小限制
 * 				fileCount		文件数量限制
 * 				callback		回调方法
 */
Anynote.uploadFile = function(settings){
	// 参数验证
	if(!(settings.baseUrl && settings.baseUrl!='')
		||!(settings.baseParams && settings.baseParams.userId && settings.baseParams.userId!='')
		||!(settings.action || settings.action!='')){
		Ext.Msg.alert('错误','参数错误.');
		return;
	}
	// 如果窗口存在，关闭
	if(Ext.getCmp('uploadFileWindow')){
		Ext.getCmp('uploadFileWindow').close();
		// 隐藏最小化按钮
		if($("#"+settings.minTargetId)){
			$("#"+settings.minTargetId).hide();
		}
	}
	
	// 参数设定
	var baseParams = baseParams = settings.baseParams;
	baseParams.actionType = "UPLOAD_ACTION";
	// 文件类型
	var fileTypes = "*.*";
	if(settings.fileTypes && settings.fileTypes!=""){
		fileTypes = settings.fileTypes;
	}
	// 文件大小限制
	var fileSize = "1MB";
	if(settings.fileSize && settings.fileSize!=""){
		fileSize = settings.fileSize;
	}
	// 文件数量限制
	var fileCount = 10;
	if(settings.fileCount && settings.fileCount!=""){
		fileCount = settings.fileCount;
	}
	// 标题
	var title = '文件上传';
	if(settings.title && settings.title!=""){
		title = settings.title;
	}

	// 文件上传数据源
    var uploadFileStore = new Ext.data.JsonStore({
        root: 'datas',
        totalProperty: 'results',
        fields: ['fileName']
    });
 	// 文件上传表格
    var uploadFileSm = new Ext.grid.CheckboxSelectionModel();
    var uploadFileGrid = new Ext.grid.GridPanel({
    	region: 'center',
		border: false,
		columnLines: true,
		stateful: true,
	    autoScroll: 'auto',
	    clicksToEdit: 1,
	    height: 395,
        store: uploadFileStore,
        cm: new Ext.grid.ColumnModel({
            defaults: {
                width: 200,
                sortable: true
            },
	        columns: [
				uploadFileSm,
				new Ext.grid.RowNumberer({header:'№'}),
				{id:'fileId',header: '文件ID', width: 50, sortable: true, dataIndex: 'fileId', css: 'vertical-align:middle;', hidden: true},
				{id:'status',header: '状态', width: 50, sortable: true, dataIndex: 'status', css: 'vertical-align:middle;'},
	            {id:'fileName',header: '文件名', width: 150, sortable: true, dataIndex: 'fileName', css: 'vertical-align:middle;'},
	            {id:'fileSize',header: '大小', width: 80, sortable: true, dataIndex: 'fileSize', align: 'right', css: 'vertical-align:middle;', renderer: formatFileSize},
	            {id:'progress',header: '进度', width: 120, sortable: true, dataIndex: 'progress', css: 'vertical-align:middle;', renderer: formatProgressBar},
	            {id:'fileTime',header: '剩余时间', width: 100, sortable: true, dataIndex: 'fileTime', css: 'vertical-align:middle;'},
	            {id:'fileSpeed',header: '速度', width: 100, sortable: true, dataIndex: 'fileSpeed', css: 'vertical-align:middle;'}
	        ]
        }),
        sm: uploadFileSm,
        columnLines: true
    });

	// 工具栏
    var uploadFileToolbar = new Ext.Toolbar({
    	items: [
    	    '<div id="uploadFile-add-button"></div>',
    	    new Ext.Button({
    	    	id: 'uploadFile-delete-button',
    	    	text: '删除',
    	    	iconCls: 'delete'
    	    }),
    	    '-',
	        new Ext.Button({
	        	id: 'uploadFile-upload-button',
	        	text: '开始上传',
	        	iconCls: 'upload'
	        }),
	        new Ext.Button({
	        	id: 'uploadFile-cancel-button',
	        	text: '取消队列',
	        	iconCls: 'stop2'
	        }),
	        '-',
			new Ext.Button({
			    id: 'uploadFile-hide-button',
				text: '隐藏',
				iconCls: 'hide'
			}),
			new Ext.Button({
			    id: 'uploadFile-close-button',
				text: '关闭',
				iconCls: 'cross'
			})
		]
	});

    // 状态栏
    var uploadFileStatusPanel = new Ext.Panel({
    	region: 'south',
    	layout:'fit',
    	height: 20,
    	border: false,
    	bodyStyle: 'background-color:transparent;padding:2px;',
    	html: '准备就绪'
    });
    
    // 判断是否需要最小化和模态显示
    var minimizable = true;
    var modal = false;
	if(!(settings.minTargetId && settings.minTargetId!='')){
		// 去掉隐藏按钮
		uploadFileToolbar.remove('uploadFile-hide-button');
		// 去掉最小化按钮
		minimizable = false;
		// 模态显示
		modal = true;
	}

	// 打开文件上传窗口
	var uploadFileWindow = new Ext.Window({
		id: 'uploadFileWindow',
		title: title,
		width: 680,
		height: 480,
		maximizable: false,
		minimizable: minimizable,
		resizable: false,
		layout:'border',
		plain: true,
		modal: modal,
		items: [uploadFileGrid,uploadFileStatusPanel],
		tbar: uploadFileToolbar,
		listeners: {
			close: function(){
				// 自定义的回调函数
				if(settings.callback){
					settings.callback();
				}
				// 隐藏最小化按钮
				if($("#"+settings.minTargetId)){
					$("#"+settings.minTargetId).hide();
				}
			},
			minimize: function(){
				if($("#"+settings.minTargetId)){
					// 显示最小化按钮
					$("#"+settings.minTargetId).show();
					// 更新状态
					updateStatus();
					// 隐藏窗口
					this.setAnimateTarget(settings.minTargetId);
					this.hide();
				}else{
					return;
				}
			}
		}
	}).show();
	
	// 格式化文件大小
	function formatFileSize(value, cellmeta,record){ 
		return Ext.util.Format.fileSize(value);
	}
	
	// 格式化进度条
	function formatProgressBar(value, cellmeta,record){ 
		var returnValue = '';
		if(record.data.status == '已完成'){
			if (Ext.isIE) {
				returnValue = '<div class="x-progress-wrap" style="height: 18px">'
						+ '<div class="x-progress-inner">'
						+ '<div style="width: 100%;" class="x-progress-bar x-progress-text">' + '100 %'
				'</div>' + '</div>' + '</div>';
			} else {
				returnValue = '<div class="x-progress-wrap" style="height: 18px">'
						+ '<div class="x-progress-inner">' + '<div id="progressBar_' + record.data.fileId
						+ '" style="width: 100%;" class="x-progress-bar">' + '</div>' + '<div id="progressText_'
						+ record.data.fileId
						+ '" style="width: 100%;" class="x-progress-text x-progress-text-back" />100 %</div>'
						'</div>' + '</div>';
			}
		}else{
			returnValue = '<div class="x-progress-wrap" style="height: 18px">' + '<div class="x-progress-inner">'
						+ '<div id="progressBar_' + record.data.fileId + '" style="width: 0%;" class="x-progress-bar">'
						+ '</div>' + '<div id="progressText_' + record.data.fileId
						+ '" style="width: 100%;" class="x-progress-text x-progress-text-back" />0 %</div>'
						'</div>' + '</div>';
		}
		return returnValue;
	}
	
	var swfu = new SWFUpload({
		flash_url : settings.baseUrl + "/websrc/js/swfupload/swfupload.swf",
		upload_url: settings.baseUrl + settings.action,	
		post_params: {albumId: 1},
		file_post_name : "fileData",
		file_size_limit : fileSize,
		file_types : fileTypes,
		file_types_description : "文件",
		file_upload_limit : fileCount,  //配置上传个数
		file_queue_limit : 0,
		use_query_string: true,
		debug: false,

		// Button settings
		button_image_url: settings.baseUrl + "/websrc/js/ext-3.3.0/resources/icons/add-upload.png",
		button_width: "45",
		button_height: "20",
		button_placeholder_id: "uploadFile-add-button",
		button_text: '添加',
		button_text_style: '',
		button_text_left_padding: 17,
		button_cursor: SWFUpload.CURSOR.HAND,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete	
	});
	
    
    // 正在上传的记录
    var currentUploadRecord = null;
    // 本次上传的文件数
    var currentUploadedCount = 0;
    // 总计上传的文件数
    var totalUploadedCount = 0;
	
	// 添加按钮
    $("#uploadFile-add-button").click(function(){
    	$("#SWFUpload_0").click();
	});
	
	// 删除按钮
    $("#uploadFile-delete-button").click(function(){
    	var records = uploadFileGrid.getSelectionModel().getSelections();
		if(records.length < 1){
			Ext.Msg.alert("提示", "请至少选择一条数据.");
		}else{
			for(var i=0;i<records.length;i++){
				var record = records[i];
				uploadFileStore.remove(record);
				swfu.cancelUpload(record.get("fileId"), false);
				if(record.get("status")=="已完成"){
					// 上传总数
					if(currentUploadedCount - 1>0){
						currentUploadedCount = currentUploadedCount - 1;
					}else{
						currentUploadedCount = 0;
					}
				}
			}
			// 更新状态栏
        	updateStatus();
		}
	});
	
	// 上传按钮
    $("#uploadFile-upload-button").click(function(){
    	if(uploadFileStore.getCount()<1){
	    	Ext.Msg.alert("提示", "请添加文件.");
		}else{
			swfu.setPostParams(baseParams);
			swfu.startUpload();
		}
	});

	// 取消按钮
    $("#uploadFile-cancel-button").click(function(){
    	if(currentUploadRecord!=null){
	    	swfu.cancelUpload(currentUploadRecord.get("fileId"));
		}else{
			Ext.Msg.alert("提示", "没有上传中的数据.");
		}
	});
    
    // 隐藏按钮
    $("#uploadFile-hide-button").click(function(){
    	uploadFileWindow.minimize();
	});
	
	// 关闭按钮
    $("#uploadFile-close-button").click(function(){
    	uploadFileWindow.close();
	});

    // 选择完文件后，将文件添加到Grid
    function fileQueued(file) {
    	var record = new Ext.data.Record({
    		fileId: file.id,
    		status: '等待中',
    		fileName: file.name,
    		fileSize: file.size,
    		process: '',
    		fileTime: '--',
    		fileSpeed: '--'
		});
    	uploadFileStore.insert(uploadFileStore.getCount(), record);
    }

    // 文件验证
    function fileQueueError(file, errorCode, message) {
    	if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
    		Ext.Msg.alert("错误", "最多只能同时上传  " + message + " 个文件.");
    		return;
    	}

    	switch (errorCode) {
    	case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
    		Ext.Msg.alert("错误", "文件尺寸过大.文件尺寸: " + file.size / 1024 +"KB.");
    		break;
    	case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
    		Ext.Msg.alert("错误", "无法上传零字节文件.");
    		break;
    	case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
    		Ext.Msg.alert("错误", "不支持的文件类型.文件名: " + file.name +".");
    		break;
    	default:
    		if (file !== null) {
    			Ext.Msg.alert("错误", "未处理的错误.");
    		}
    		break;
    	}
    }
    //文件选择完成后
    function fileDialogComplete(numFilesSelected, numFilesQueued) {
    	// 更新状态栏
    	updateStatus();
    }

    //单个文件开始上传
    function uploadStart(file) {
    	var fileId = file.id;
    	var records = uploadFileStore.getRange(0,uploadFileStore.getCount()-1);
    	for(var i=0;i<records.length;i++){
    		var record = records[i];
    		if(fileId==record.get("fileId")){
    			currentUploadRecord = record;
    			break;
    		}
    	}
    }

    //单个文件上传中
    function uploadProgress(file, bytesLoaded, bytesTotal) {
    	if(currentUploadRecord!=null){
    		// 更新状态
    		currentUploadRecord.set("status", "进行中");
    		// 剩余时间
    		currentUploadRecord.set("fileTime", SWFUpload.speed.formatTime(file.timeRemaining));
    		// 当前速度
    		currentUploadRecord.set("fileSpeed", SWFUpload.speed.formatBPS(file.currentSpeed));
    		// 更新进度条
    		var percent = Math.ceil((bytesLoaded / bytesTotal) * 99);
    		Ext.getDom('progressBar_' + file.id).style.width = percent + "%";
    		Ext.getDom('progressText_' + file.id).innerHTML = percent + " %";
    	}else{
    		Ext.Msg.alert("错误", "没有上传中的文件.");
    	}
    }

    //单个文件上传成功
    function uploadSuccess(file, serverData) {
    	var jsonResult = Ext.decode(serverData);
    	if(jsonResult && jsonResult.success){
    		if(currentUploadRecord!=null){
    			// 更新状态
    			currentUploadRecord.set("status", "已完成");
    			// 本次上传
    			currentUploadedCount = currentUploadedCount + 1;
    			// 总计上传
    			totalUploadedCount = totalUploadedCount + 1;
    			// 更新状态栏
    			updateStatus();
    		}else{
    			Ext.Msg.alert("错误", "没有上传中的文件.");
    		}
    	}else{
    		var message = '发生异常.';
			if(jsonResult.message && jsonResult.message != ''){// 后台设定的业务消息
				message = jsonResult.message;
			}else if(settings.failureMsg && settings.failureMsg!=''){// 前台指定的错误信息
				message = settings.failureMsg;
			}
			if(jsonResult.exceptionMsg && jsonResult.exceptionMsg != ''){// 有异常信息
				Anynote.exceptionWindow(message, jsonResult.exceptionMsg);
			}else{
				Ext.Msg.alert('错误', message);
			}
    	}
    }

    //文件上传错误验证
    function uploadError(file, errorCode, message) {
    	switch (errorCode) {
    	case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
    		currentUploadRecord.set("status", "HTTP错误");
    		break;
    	case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
    		currentUploadRecord.set("status", "上传失败");
    		break;
    	case SWFUpload.UPLOAD_ERROR.IO_ERROR:
    		currentUploadRecord.set("status", "IO 错误");
    		break;
    	case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
    		currentUploadRecord.set("status", "安全错误");
    		break;
    	case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
    		currentUploadRecord.set("status", "超出上传限制");
    		break;
    	case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
    		currentUploadRecord.set("status", "文件验证失败");
    		break;
    	case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
    		currentUploadRecord.set("status", "取消");
    		break;
    	case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
    		currentUploadRecord.set("status", "停止");
    		break;
    	default:
    		currentUploadRecord.set("status", "未处理的错误");
    		break;
    	}
    }

    //单个文件上传完成
    function uploadComplete(file) {
    }

    // 全部上传完成
    function queueComplete(numFilesUploaded) {
    	currentUploadRecord = null;
    	Ext.Msg.alert('提示', '上传完成.');
    }
    
    // 更新状态栏
    function updateStatus(){
    	// 等待上传
    	var unUploadCount = uploadFileStore.getCount() - currentUploadedCount;
    	uploadFileStatusPanel.body.update("等待上传: "+unUploadCount+" 个，本次上传: "+currentUploadedCount+" 个 ，总计上传: "+totalUploadedCount+" 个。");
    	if(Ext.getCmp("minUploadWinBtn")){
    		var btnText = "";
    		if(unUploadCount==0 && currentUploadedCount==0){
    			btnText = "准备就绪";
    		}else if(unUploadCount!=0 && currentUploadedCount==0){
    			btnText = "等待上传";
    		}else if(unUploadCount!=0 && currentUploadedCount!=0){
    			btnText = "上传中..."+currentUploadedCount+"/"+(currentUploadedCount+unUploadCount);
    		}else if(unUploadCount==0 && currentUploadedCount!=0){
    			btnText = "上传完成";
    		}
    		Ext.getCmp("minUploadWinBtn").setText(btnText);
    	}
    }
}

/**
 * 初期化上传窗口最小后后的按钮
 */
Anynote.initMinUploadWinBtn = function(targetId){
	var minUploadWinBtn = new Ext.Button({
		id: 'minUploadWinBtn',
		renderTo: targetId,
    	text: '上传中...',
    	iconCls: 'upload',
    	handler: function(){
			if(Ext.getCmp('uploadFileWindow')){
				Ext.getCmp('uploadFileWindow').setAnimateTarget(targetId);
				Ext.getCmp('uploadFileWindow').show();
				$("#"+targetId).hide();
			}
    	}
    });
	var left = $(window).width() - 150;
	var top = $(window).height() - 60;
	$("#"+targetId).attr("style", "position:absolute;display:none;z-index:99;left:"+left+"px;top:"+top+"px;");
}

/**
 * 重写的HtmlEditor,支持插入图片
 */
Anynote.HtmlEditor = Ext.extend(Ext.form.HtmlEditor, {
	addWebImage: function(){
		// 编辑器
		var editor = this;
		// 插入图片用Form
		var addWebImageFormPanel = new Ext.FormPanel({
	        labelWidth: 60,
	        border: false,
	        bodyStyle: 'background-color:transparent;',
	        url: '',
	        items: [{
	            layout:'form',
	            border: false,
	            bodyStyle: 'padding:5px;background-color:transparent;',
	            items:[
		           new Ext.form.TextField ({// 图片地址
	            		inputType: 'textfield',
		                name: 'imageUrl',
		                fieldLabel: '图片地址',
		                anchor:'95%',
		                allowBlank:false
		           }),
		           new Ext.form.NumberField ({// 图片宽
		               name:'imageWidth',
		               fieldLabel:'图片宽度',
		               anchor:'95%',
		               allowBlank:true,
		               decimalPrecision: 0
		           }),
		           new Ext.form.NumberField ({// 图片高度
		               name:'imageHeight',
		               fieldLabel:'图片高度',
		               anchor:'95%',
		               allowBlank:true,
		               decimalPrecision: 0
		           })
	           ]
	        }]
	    });
		// 插入图片用Window
		var addWebImageWindow = new Ext.Window({
			title: '插入图片',
			width: 300,
			height: 160,
			modal: true,
			maximizable: false,
			resizable: false,
			layout:'fit',
			plain: true,
			tbar: new Ext.Toolbar({
		    	items: [
				    new Ext.Button({
					    id: 'addWebImage-save-button',
						text: '确定',
						iconCls: 'tick',
						handler: function(){
					    	var form = addWebImageFormPanel.getForm();
						    if(form.isValid()){
						    	// 插入图片的HTML
						    	var imgHtml
						    	// 图片地址
						    	var src = form.findField('imageUrl').getValue();
						    	// 宽度
						    	var width = form.findField('imageWidth').getValue();
						    	// 高度
						    	var height = form.findField('imageHeight').getValue();
						    	if(width!='' &&height!=''){
						    		imgHtml = '<a href="'+src+'" target="_blank"><img src="'+src+'" width="'+width+'" height="'+height+'"/></a>';
						    	}else{
						    		imgHtml = '<a href="'+src+'" target="_blank"><img src="'+src+'"/></a>';
						    	}
						    	editor.setValue(editor.getValue() + imgHtml);
						    	addWebImageWindow.close();
						    }
						}
					}),
					new Ext.Button({
					    id: 'addWebImage-cancel-button',
						text: '取消',
						iconCls: 'cross',
						handler: function(){
							addWebImageWindow.close();
						}
					})
				]
			}),
			items: addWebImageFormPanel
		});
		addWebImageWindow.show();
	},
	addAlbumImage: function(){
		// 从相册添加图片的处理页面地址
		var addAlbumImageUrl = this.addAlbumImageUrl;
		// 图片查看窗口
		var addAlbumImageWindow = new Ext.Window({
			id: 'addAlbumImageWindow',
			title: '选择图片',
			width: 800,
			height: 500,
			modal: true,
			maximizable: false,
			resizable: false,
			layout:'fit',
			plain: false,
			border:false,
			autoLoad:{url:addAlbumImageUrl,scripts:true,nocache:true}
		});
		addAlbumImageWindow.show();
	},
	createToolbar : function(editor) {
		Anynote.HtmlEditor.superclass.createToolbar.call(this, editor);
		this.tb.insertButton(17, {
			iconCls: 'picture_link',
			tooltip: '通过链接添加网络图片',
            handler: this.addWebImage,
            scope: this
		});
		this.tb.insertButton(18, {
			iconCls: 'photo_link',
			tooltip: '从相册添加图片',
            handler: this.addAlbumImage,
            scope: this
		});
    }
});
Ext.reg('AnynoteHtmleditor', Anynote.HtmlEditor);

/**
 * 重新加载树结点
 * @param tree	树对象
 * @param node	结点对象
 * @param callback	回调方法
 */
Anynote.reloadTreeNode = function(tree, node, callback){
	if(tree){
		if(!node){// 结点为空是，默认为跟结点
			node = tree.getRootNode();
		}
		tree.getLoader().load(node, function(node){
			if(callback){
				callback(node);
			}
		});
	}else{
		Ext.Msg.alert('错误', '参数错误');
	}
}

/**
 * 改变活动Tab的宽度高度以适应窗口大小变化
 */
Anynote.resizeActiveTab = function(){
	var tabId = Anynote.tabPanel.getActiveTab().getId();
	if(tabId=="noteListTab"){
		// 笔记Grid
		this.resizeGridTo("noteListMainPanel", 0, 28);
		if(Ext.getCmp('noteListMainPanel')){
			Ext.getCmp('noteListPanel').setWidth(Ext.getCmp('noteListMainPanel').getWidth() * 0.4);
			Ext.getCmp('noteListOptPanel').setWidth(Ext.getCmp('noteListMainPanel').getWidth() * 0.6);
			if(Ext.getCmp('editNoteFormPanel')){
				Ext.getCmp('editNoteFormPanel').findById("noteContent").setHeight(Ext.getCmp('noteListMainPanel').getHeight() - 150);
			}
		}
	}else if(tabId=="categoryListTab"){
		// 笔记分类Grid
		this.resizeGridTo("categoryListGrid", 0, 56);
	}else if(tabId=="rubbishNoteTab"){
		// 笔记回收站Grid
		this.resizeGridTo("rubNoteGrid", 0, 56);
	}else if(tabId=="albumListTab"){
		// 相册Grid
		this.resizeGridTo("albumListGrid", 0, 56);
	}else if(tabId=="pictureListTab"){
		// 图片Panel
		this.resizeGridTo("pictureListPanel", 0, 56);
	}else if(tabId=="accountListTab"){
		// 账目Grid
		this.resizeGridTo("accountListGrid", 0, 169);
	}else if(tabId=="accountCategoryListTab"){
		// 账目分类Grid
		this.resizeGridTo("accountCategoryListGrid", 0, 56);
	}else if(tabId=="todoListTab"){
		// 任务Grid
		this.resizeGridTo("todoListGrid", 0, 56);
	}else if(tabId=="userListTab"){
		// 用户Grid
		this.resizeGridTo("userListGrid", 0, 56);
	}else if(tabId=="feedViewerTab"){
		// 订阅
		this.resizeGridTo("feedViewerPanel", 0, 28);
	}else if(tabId=="feedFavoriteTab"){
		// 订阅收藏夹
		this.resizeGridTo("feedFavoritePanel", 0, 28);
	}else if(tabId=="documentListTab"){
		// 文档grid
		this.resizeGridTo("documentListGrid", 0, 79);
	}
	
}

/**
 * 改变GridPanel的宽度高度以适应窗口大小变化
 */
Anynote.resizeGridTo = function(gridId, offWidth, offHeight){
	var w = Anynote.tabPanel.getWidth();
	var h = Anynote.tabPanel.getHeight() - offHeight;
	var gridPanel = Ext.getCmp(gridId);
	if(gridPanel){
		gridPanel.setWidth(w);
		gridPanel.setHeight(h);
	}
}

/**
 * 主题切换
 */
Anynote.changeTheme = function(themeCss){
	//实际改变风格样式的处理  
    Ext.util.CSS.swapStyleSheet('theme', '/Anynote/websrc/js/ext-3.3.0/resources/css/'+themeCss);
}

/**
 * 主题切换ComboBox控件
 */
Anynote.themeComboBox = Ext.extend(Ext.form.ComboBox,{
    editable : false,  
    width: 100,
    valueField : 'css',  
    displayField : 'theme',
    typeAhead : true,  
    mode : 'local',  
    value : '经典蓝色',  
    readonly : true,  
    triggerAction : 'all',  
    selectOnFocus : true,  
    initComponent : function(){  
        var themes = [  
                ['经典蓝色', 'ext-all.css'],  
                ['简约灰色', 'xtheme-gray.css'],
                ['清新绿色', 'xtheme-olive.css'],
                ['时尚紫色', 'xtheme-purple.css']
        ];  
        this.store = new Ext.data.SimpleStore({  
            fields : ['theme', 'css'],  
            data : themes  
        });  
    },  
    initEvents : function(){  
        this.on('collapse', function(){  
            //实际改变风格样式的处理  
            Ext.util.CSS.swapStyleSheet('theme', '/Anynote/websrc/js/ext-3.3.0/resources/css/'+ this.getValue());
            $.cookie('ext_theme', this.getValue());
        });  
    },
    listeners: {
        render: function(combo) {
    		var cookieTheme = $.cookie('ext_theme');
            if (null != cookieTheme){
	        	Ext.util.CSS.swapStyleSheet('theme', '/Anynote/websrc/js/ext-3.3.0/resources/css/'+ cookieTheme);
	        	this.setValue(cookieTheme);
            }
        }
    }
}); 

/**
 * 可拖动层
 */
var a;
document.onmouseup=function(){if(!a)return;document.all?a.releaseCapture():window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);a="";};
document.onmousemove=function (d){if(!a)return;if(!d)d=event;a.style.left=(d.clientX-b)+"px";a.style.top=(d.clientY-c)+"px";};
Anynote.dragDiv = function(o,e){a=o;document.all?a.setCapture():window.captureEvents(Event.MOUSEMOVE);b=e.clientX-parseInt(a.style.left);c=e.clientY-parseInt(a.style.top);}

/**
 * 判断是否是图片
 */
Anynote.isPic = function(type){
	var picTypes = ['jpg','jpeg','png','gif','bmp'];
	for(i=0;i<picTypes.length;i++){
		if(type.toLowerCase() == picTypes[i]){
			return true;
		}
	}
	return false;
}

/**
 * 把records转换成JSON对象
 */
Anynote.convertRecordsToJson = function(records) {
	if (records.length ==0) {
    	return '';
    }
    var record;
    
    var jsonData = "[";
    
    for(var i = 0; i < records.length; i++) {
    	record = records[i];
        jsonData += Ext.util.JSON.encode(record.data) + ","; 
    }                                          
    jsonData = jsonData.substring(0,jsonData.length-1) + "]";
          
    return jsonData;
}

/**
 * 弹出窗口，并居中显示
 */
Anynote.openWin = function(url){
	// 目标尺寸
	var targetWidth = Anynote.OPEN_WIN_WIDTH;
	var targetHeight = Anynote.OPEN_WIN_HEIGHT;
	// 屏幕尺寸
	var availWidth = screen.availWidth;
	var availHeight = screen.availHeight;
	// 外观
	var features = "top="+(availHeight-targetHeight)/2+",left="+(availWidth-targetWidth)/2+",height="+targetHeight+",width="+targetWidth+",status=yes,toolbar=no,menubar=no,location=no,resizable=yes";
	// 弹出窗口
	window.open(url,null,features);
}

/**
 * 根据固定尺寸或者比率返回原始图片的缩略图
 * @param image	原始图片对象
 * @param ratio 百分比
 * @param toWidth 目标宽度
 * @param toHeight 目标高度
 */
Anynote.getThumbImage = function(image, ratio, toWidth, toHeight){
	// 优先根据比率设定
	if(ratio && ratio!=''){
		if(ratio < 1 || ratio > 100){
			Ext.Msg.alert('提示', '图片百分比必须大于1且小于100.');
			return;
		}else{
			image.width = image.width * ratio / 100;
			image.height = image.height * ratio / 100;
			return image;
		}
	}else{
		if(toWidth && toHeight && toWidth != '' && toHeight !=''){
			// 设置图片尺寸
			var imgWidth = image.width;
			var imgHeight = image.height;
			var ratio = 1;
			// 宽度设定
			if(imgWidth>toWidth){
				ratio = toWidth / imgWidth;
				imgWidth = toWidth;
				imgHeight = imgHeight * ratio;
			}
			// 高度设定
			if(imgHeight>toHeight){
				ratio = toHeight / imgHeight;
				imgWidth = imgWidth * ratio;
				imgHeight = toHeight;
			}
			image.width = imgWidth;
			image.height = imgHeight;
			return image;
		}else{
			Ext.Msg.alert('提示', '图片宽度和高度不能为空.');
			return;
		}
	}
}

/**
 * 预加载图片
 * @param url	图片地址
 * @param callback 加载完成后的操作
 */
Anynote.imgLoad = function(url, callback) {
    var img = new Image();
    img.src = url;
    if (img.complete) {
        callback(img.width, img.height);
    } else {
        img.onload = function () {
            callback(img.width, img.height);
            img.onload = null;
        }
    }
}

/**
 * 获得当天日期和星期
 */
Anynote.getToday = function(){
	var today=new Date();
	var year = today.getFullYear();
	var month = today.getMonth()+1;
	month = month<10?'0'+month:month;
	var date = today.getDate();
	date = date<10?'0'+date:date;
	var day = Anynote.WEEK_DATA_CN[today.getDay()];
	return year+"年"+month+"月"+date+"日 "+day;
}

/**
 * 分时问候
 */
Anynote.sayHello = function(){
	var hour = new Date().getHours();
	var hello = '';
	if(hour < 6){
		hello='凌晨好';
	}else if(hour < 9){
		hello='早上好';
	}else if(hour < 12){
		hello='上午好';
	}else if(hour < 14){
		hello='中午好';
	}else if(hour < 17){
		hello='下午好';
	}else if(hour < 19){
		hello='傍晚好';
	}else if(hour < 22){
		hello='晚上好';
	}else{
		hello='夜里好';
	}
	return hello;
}

/**
 * 根据页面url获得Tab页ID
 * 例如：/websrc/page/note/noteList.jsp 页面打开的Tab页的ID为:noteListTab
 */
Anynote.getTabIdByUrl = function(url){
	if(null==url || ''==url){
		Ext.Msg.alert('错误', '参数错误，获得TabId失败.');
		return;
	}
	return url.substring(url.lastIndexOf('/', url.indexOf('.'))+1, url.indexOf('.'))+'Tab';
}

Anynote.rightBottomTipWin = function(msg,widthP,heightP){
	var width = 226;
	var height = 160;
	if(widthP){
		width = widthP;
	}
	if(heightP){
		height = heightP;
	}
    var rightBottomTipWin = new Ext.Window({
          width:width, 
          height:height, 
          layout:'fit', 
          modal : false, 
          plain: true, 
          shadow:false, //去除阴影 
          draggable:false, //默认不可拖拽 
          resizable:false, 
          closable: true, 
          closeAction:'hide', //默认关闭为隐藏 
          autoHide:5, //5秒后自动隐藏，false则不自动隐藏
          title : '消息提醒',
          //html : ''+msg+'',
          html : msg,
          constructor: function(conf){ 
          Ext.Window.superclass.constructor.call(this, conf); 
              this.initPosition(true); 
          }, 
          initEvents: function() { 
          Ext.Window.superclass.initEvents.call(this); 
              //自动隐藏 
              if(false !== this.autoHide){ 
                  var task = new Ext.util.DelayedTask(this.hide, this), second = (parseInt(this.autoHide) || 3) * 1000; 
                  this.on('beforeshow', function(self) { 
                      task.delay(second); 
                  }); 
              } 
              this.on('beforeshow', this.showTips); 
              this.on('beforehide', this.hideTips);
              //window大小改变时，重新设置坐标 
              Ext.EventManager.onWindowResize(this.initPosition, this);
              //window移动滚动条时，重新设置坐标 
              Ext.EventManager.on(window, 'scroll', this.initPosition, this);
          }, 
          //参数flag为true时强制更新位置 
          initPosition: function(flag) {
           //不可见时，不调整坐标
              if(true !== flag && this.hidden){ 
                  return false; 
              } 
              var doc = document, bd = (doc.body || doc.documentElement); 
              //Ext取可视范围宽高(与上面方法取的值相同), 加上滚动坐标 
              var left = bd.scrollLeft + Ext.lib.Dom.getViewWidth()-4-this.width; 
              var top = bd.scrollTop + Ext.lib.Dom.getViewHeight()-4-this.height; 
              this.setPosition(left, top); 
          }, 
          showTips: function() { 
              var self = this; 
              if(!self.hidden){return false;} 
              //初始化坐标
              self.initPosition(true);  
              self.el.slideIn('b', { 
                  callback: function() {  
                 //显示完成后,手动触发show事件,并将hidden属性设置false,否则将不能触发hide事件  
                      self.fireEvent('show', self); 
                      self.hidden = false; 
                  } 
              });
              //不执行默认的show
              return false;  
          }, 
          hideTips: function() { 
              var self = this; 
              if(self.hidden){return false;} 
              self.el.slideOut('b', { 
                  callback: function() { 
                      //渐隐动作执行完成时,手动触发hide事件,并将hidden属性设置true 
                      self.fireEvent('hide', self); 
                      self.hidden = true; 
                  } 
              });
              //不执行默认的hide
              return false; 
          } 
    })
    return rightBottomTipWin;

}