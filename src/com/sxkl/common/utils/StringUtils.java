package com.sxkl.common.utils;

public class StringUtils extends org.apache.commons.lang.StringUtils
{
  public static boolean isNotEmpty(String str)
  {
    if ((str != null) && (str.length() > 0) && (!"null".equals(str))) {
      return true;
    }
    return false;
  }

  public static String getEncoding(String str)
  {
    if (isEmpty(str)) return str;
    String[] encodeArr = { "ISO-8859-1", "GB2312", "GBK", "UTF-8", "UTF-16" };
    try {
      for (String encode : encodeArr)
        if (str.equals(new String(str.getBytes(encode), encode)))
          return encode;
    }
    catch (Exception e)
    {
      return null;
    }
    return null;
  }
}