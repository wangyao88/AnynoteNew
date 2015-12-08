package com.sxkl.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletHelp
{
  public static void outRequestForJson(HttpServletRequest request, HttpServletResponse response, String res)
    throws IOException
  {
    response.setContentType("application/x-json; charset=UTF-8");
    response.getWriter().print(res);
  }

  public static String getRealPath(HttpServletRequest request, String virtualPath)
  {
    return request.getSession().getServletContext()
      .getRealPath(virtualPath);
  }

  public static Map getParameterMap(HttpServletRequest request)
  {
    Map properties = request.getParameterMap();

    Map returnMap = new HashMap();
    Iterator entries = properties.entrySet().iterator();

    String name = "";
    String value = "";
    while (entries.hasNext()) {
      Map.Entry entry = (Map.Entry)entries.next();
      name = (String)entry.getKey();
      Object valueObj = entry.getValue();
      if (valueObj == null) {
        value = "";
      } else if ((valueObj instanceof String[])) {
        String[] values = (String[])valueObj;
        for (int i = 0; i < values.length; i++) {
          value = values[i] + ",";
        }
        value = value.substring(0, value.length() - 1);
      } else {
        value = valueObj.toString();
      }
      returnMap.put(name, value);
    }
    return returnMap;
  }

  public static String getArrayFromMap(Map map, String needEmpty)
  {
    if (map == null) {
      return null;
    }
    StringBuffer sb = null;
    if (StringUtils.isNotEmpty(needEmpty))
      sb = new StringBuffer("[['', '" + needEmpty + "'],");
    else {
      sb = new StringBuffer("[");
    }
    Set keySet = map.keySet();
    Iterator ite = keySet.iterator();
    String key = "";
    while (ite.hasNext()) {
      key = (String)ite.next();
      sb.append("['" + key + "', '" + map.get(key) + "'],");
    }
    String rs = sb.toString();
    if (rs.endsWith(",")) {
      rs = rs.substring(0, rs.length() - 1);
    }
    return rs += "]";
  }




}