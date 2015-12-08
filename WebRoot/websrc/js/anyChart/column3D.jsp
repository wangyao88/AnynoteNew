<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'index.jsp' starting page</title>
	<script type="text/javascript" language="javascript" src="<%=basePath%>anyChart/js/AnyChart.js"></script>
</head> 
<body> 
    <script type="text/javascript" language="javascript"> 
    //<![CDATA[
    //<data_plot_settings enable_3d_mode="True">
    var JSONData = {
		    "charts": {
		        "chart": {
					"data_plot_settings" : {
					    "enable_3d_mode" : "true",
					    "bar_series":{
	                        "tooltip_settings":{
	                              "enabled": "true",
	                              "format":"{%SeriesName}{%Name}\n电网风险条数:{%YValue}{numDecimals:0,trailingZeros:false}"
	                        },
	                        "label_settings":{
	                              "enabled": "true",
	                              "position":{
                                      "anchor": "Center",
                                      "halign": "Center",
                                      "valign": "Center"
	                              },
	                              "format":"{%YValue}{numDecimals:0,trailingZeros:false}"
	                        }
					    }
					 },
		            "chart_settings": {
		                "title": {
		                    "text": "电网风险柱状图"
		                },
		                "axes":{
		                    "x_axis": {
		                	    "enabled":"true",
		                        "title":{
		                            "text": ""
		                         }
		                    },
		                    "y_axis": {
		                	    "enabled":"true",
		                        "title":{
		                            "text": ""
		                         },
		                         "labels":{
		                        	 "format":"{%Value}{numDecimals:0,trailingZeros:false}"
			                     }
		                    }
		   			    }
		            },
		            "data": {
		                "series": [
		                    {
			                    "name": "wqqw",
		                        "point": [
		                            {"name": "Jan", "y": 21,"color":"#F41111"},
		                            {"name": "Feb", "y": 23,"color":"#BF0ECC"},
		                            {"name": "Mar", "y": 10,"color":"#5D0ECC"},
		                            {"name": "Apr", "y": 34,"color":"#0E8FCC"},
		                            {"name": "May", "y": 45,"color":"#ACCC0E"}
		                        ]
		                    }
		                ]
		            }
		        }
		    }
    
		};

    var chart = new AnyChart('<%=basePath%>anyChart/swf/AnyChart.swf'); 
    chart.width = 700; 
    chart.height = 300; 
    chart.setJSData(JSONData);
    chart.write(); 
    //]]> 
    </script> 
</body> 
</html>
