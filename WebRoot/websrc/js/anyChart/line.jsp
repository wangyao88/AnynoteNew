<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'index.jsp' starting page</title>
	<script type="text/javascript" src="<%=basePath%>anyChart/js/AnyChart.js"></script>
</head> 
<body> 
    <script type="text/javascript"> 
    var JSONData = {
		    "charts": {
		        "chart": {
    	            "plot_type":"CategorizedVertical",
					"data_plot_settings" : {
					   // "enable_3d_mode" : "true",
					    "default_series_type":"Line",
					    "line_series":{
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
	                              "format":"{%YValue}{numDecimals:0,trailingZeros:false}",
	                              "font":{
                                     "size":"14"
		                          }
	                        }
					    }
					 },
		            "chart_settings": {
						"legend":{ 
							"enabled":"true",
							"title":{ 
								"enabled":"false"
							},
						},
		                "title": {
		                    "text": "电网风险线形图"
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
			                    "name": "2014年",
			                    "color":"#BF0ECC",
		                        "point": [
		                            {"name": "Jan", "y": 21},
		                            {"name": "Feb", "y": 23},
		                            {"name": "Mar", "y": 10},
		                            {"name": "Apr", "y": 34},
		                            {"name": "May", "y": 45}
		                        ]
		                    },
		                    {
			                    "name": "2015年",
			                    "color":"#F41111",
		                        "point": [
		                            {"name": "Jan", "y": 41},
		                            {"name": "Feb", "y": 63},
		                            {"name": "Mar", "y": 40},
		                            {"name": "Apr", "y": 14},
		                            {"name": "May", "y": 0}
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
