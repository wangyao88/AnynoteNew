<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.sxkl.menu.model.*" %>
<%@ page import="com.sxkl.common.constant.*" %>
<%@ page import="java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
   List<ParentMenu> parentMenus = GlobalConstants.S_PARENT_MENUS;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>信息管理系统--菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>菜单</div>
    
    <dl class="leftmenu">
    
    <%for(ParentMenu pm : parentMenus){%>
          <dd>
		    <div class="title">
		       <span><img src="images/leftico01.png" /></span><%=pm.getMenu().getMenuName()%>
    	    </div>
    	    <ul class="menuson">
	    	    <%Set<ChildrenMenu> childrenMenus = pm.getChildrenMenu();%>
	    	    <%Iterator<ChildrenMenu> it = childrenMenus.iterator();%>
	    	    <%while(it.hasNext()){%>
					<%ChildrenMenu cm = it.next();%>
					<li><cite></cite><a href="<%=cm.getMenu().getMenuLink()%>" target="rightFrame"><%=cm.getMenu().getMenuName()%></a><i></i></li>
                <%}%>
            </ul>    
         </dd>
     <%}%>
        
    <dd>
    <div class="title">
    <span><img src="images/leftico01.png" /></span>管理信息
    </div>
    	<ul class="menuson">
        <li><cite></cite><a href="index.html" target="rightFrame">首页模版</a><i></i></li>
        </ul>    
    </dd>
        
    
    <dd>
    <div class="title">
    <span><img src="images/leftico02.png" /></span>其他设置
    </div>
    <ul class="menuson">
        <li><cite></cite><a href="#">编辑内容</a><i></i></li>
        <li><cite></cite><a href="#">发布信息</a><i></i></li>
        <li><cite></cite><a href="#">档案列表显示</a><i></i></li>
        </ul>     
    </dd> 
    
    
    <dd><div class="title"><span><img src="images/leftico03.png" /></span>编辑器</div>
    <ul class="menuson">
        <li><cite></cite><a href="#">自定义</a><i></i></li>
        <li><cite></cite><a href="#">常用资料</a><i></i></li>
        <li><cite></cite><a href="#">信息列表</a><i></i></li>
        <li><cite></cite><a href="#">其他</a><i></i></li>
    </ul>    
    </dd>  
    
    
    <dd><div class="title"><span><img src="images/leftico04.png" /></span>日期管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="#">自定义</a><i></i></li>
        <li><cite></cite><a href="#">常用资料</a><i></i></li>
        <li><cite></cite><a href="#">信息列表</a><i></i></li>
        <li><cite></cite><a href="#">其他</a><i></i></li>
    </ul>
    
    </dd>   
    
    </dl>
    
</body>
</html>