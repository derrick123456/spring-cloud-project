package com.test.demo.user.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author wgg
 * @date 2021/04/05
 */
@Slf4j
public class IpUtil {

	public static final String IPV4 = "ipv4";

	public static final String IPV6 = "ipv6";

	/**
	 * <p>项目名称: common-base </p> 
	 * <p>文件名称: DateUtil.java </p> 
	 * <p>类型描述: [日期时间处理类] </p>
	 * <p>创建时间: 2019年10月08日 </p>
	 * @author wgg
	 * @version V1.0
	 * @update  
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = null;
		// X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader("X-Forwarded-For");
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// Proxy-Client-IP：apache 服务代理
			ipAddresses = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// WL-Proxy-Client-IP：weblogic 服务代理
			ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// HTTP_CLIENT_IP：有些代理服务器
			ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// X-Real-IP：nginx服务代理
			ipAddresses = request.getHeader("X-Real-IP");
		}
		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ipAddresses != null && ipAddresses.length() != 0) {
			ip = ipAddresses.split(",")[0];
		}
		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.equals(ip, "0:0:0:0:0:0:0:1")) {
			if (request.getHeader("x-forwarded-for") == null) {
				return request.getRemoteAddr();
			}
			return request.getHeader("x-forwarded-for");
		}
		return ip;
	}

	public static List<String> getLocalIp4Addr() {
		return getLocalIpAddr(IPV4);
	}

	public static List<String> getLocalIp6Addr() {
		return getLocalIpAddr(IPV6);
	}

	private static List<String> getLocalIpAddr(String iptv_type) {
		List<String> resultList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface current = interfaces.nextElement();
				if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
					continue;
				}
				Enumeration<InetAddress> addresses = current.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();
					if (((addr instanceof Inet4Address) && IPV4.equals(iptv_type))
							|| ((addr instanceof Inet6Address) && IPV6.equals(iptv_type))) {
						resultList.add(addr.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			log.error("", e);
		}
		return resultList;
	}

}