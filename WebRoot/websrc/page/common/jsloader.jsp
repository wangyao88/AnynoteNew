<%@ page language="java" pageEncoding="UTF-8" %>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%
	String contextPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=contextPath %>/websrc/js/jsloader.js"></script>
<script type="text/javascript">
	// css
	JSLoader.loadStyleSheet("<%=contextPath %>/websrc/css/default.css");
	JSLoader.loadStyleSheet("<%=contextPath %>/websrc/js/ext-3.3.0/resources/css/ext-all.css");
	JSLoader.loadStyleSheet("<%=contextPath %>/websrc/js/ext-3.3.0/resources/css/icon.css");
	JSLoader.loadStyleSheet("<%=contextPath %>/websrc/js/ext-3.3.0/ux/css/fileuploadfield.css");
	JSLoader.loadStyleSheet("<%=contextPath %>/websrc/css/noteList.css");
	JSLoader.loadStyleSheet("<%=contextPath %>/websrc/css/pictureList.css");
	JSLoader.loadStyleSheet("<%=contextPath %>/websrc/css/feedViewer.css");
	// extjs
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/ext-3.3.0/adapter/ext/ext-base.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/ext-3.3.0/ext-all.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/ext-3.3.0/locale/ext-lang-zh_CN.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/ext-3.3.0/ux/SearchField.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/ext-3.3.0/ux/DataView-more.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/ext-3.3.0/ux/TabCloseMenu.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/ext-3.3.0/ux/FileUploadField.js");
	// jquery
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/jquery/jquery-1.4.2.min.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/jquery/jquery.cookie.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/jquery/jquery.jfeed.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/gen_validatorv31.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/kindeditor/kindeditor-min.js");
	// 文件上传
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/swfupload/swfupload.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/swfupload/swfupload.queue.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/swfupload/swfupload.speed.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/js/swfupload/fileprogress.js");
	// 共同JS
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/page/common/Anynote.js");
	JSLoader.loadJavaScript("<%=contextPath %>/websrc/page/common/Menu.js");
</script>
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = "<%=contextPath %>/websrc/js/ext-3.3.0/resources/images/default/s.gif";
</script>