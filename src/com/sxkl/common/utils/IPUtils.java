package com.sxkl.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtils {

	public static String getCurrentIp() {
		StringBuffer ip = new StringBuffer();
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
			ip.append(address.getHostAddress());
		} catch (UnknownHostException e) {
			ip.append("null");
		}   
        return ip.toString();   
	}

}
