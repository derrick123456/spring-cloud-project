package com.test.demo.user.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>文件名称: PathUtil.java </p>
 * <p>类型描述: [uri工具 提供通配符匹配校验以及uri添加参数等] </p>
 */
@Slf4j
public class PathUtil {

	/**
	 * <p>功能描述: [匹配成功返回true 如/abc/abc patterns包含:/abc/**] </p>
	 * @Title pathMatcher
	 * @param path url路径
	 * @param patterns 通配符表达式集合,匹配规则
	 * @return boolean
	 */
	public static boolean pathMatcher(String path, List<String> patterns) {
		AntPathMatcher pathMatcher = new AntPathMatcher();
		pathMatcher.setCaseSensitive(false);
		boolean checkFlag = false;
		for (String pattern : patterns) {
			checkFlag = pathMatcher.match(pattern, path);
			if (checkFlag) {
				break;
			}
		}
		return checkFlag;
	}

	/**
	 * <p>功能描述: [给url添加多个参数] </p>
	 * @Title addParams
	 * @param uri 需要添加的uri
	 * @param params 参数的key value
	 * @return URI
	 */
	public static URI addParams(URI uri, Map<String, String> params) {
		try {
			List<NameValuePair> paramsAlready = new URIBuilder(uri.toString()).getQueryParams();
			Map<String ,String> paramsTemp = params == null ? new HashMap<String, String>() : params;
			List<String> alreadyKey = new ArrayList<String>();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			paramsAlready.forEach(nameValues -> {
				alreadyKey.add(nameValues.getName());
			});
			paramsTemp.forEach((k, v) -> {
				if (k != null && v != null && !alreadyKey.contains(k)) {
					NameValuePair param = new BasicNameValuePair(k, v);
					nvps.add(param);
				}
			});
			return new URIBuilder(uri.toString()).addParameters(nvps).build();
		} catch (URISyntaxException e) {
			log.error("", e);
			return uri;
		}
	}

	/**
	 * <p>功能描述: [给url添加多个参数] </p>
	 * @Title addParams
	 * @param uri 需要添加的uri
	 * @param params 参数的key value
	 * @return URI
	 */
	public static URI addParamsByMap(URI uri, Map<String, Object> params) {
		try {
			List<NameValuePair> paramsAlready = new URIBuilder(uri.toString()).getQueryParams();
			Map<String ,Object> paramsTemp = params == null ? new HashMap<String, Object>() : params;
			List<String> alreadyKey = new ArrayList<String>();
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			paramsAlready.forEach(nameValues -> {
				alreadyKey.add(nameValues.getName());
			});
			paramsTemp.forEach((k, v) -> {
				if (k != null && v != null && !alreadyKey.contains(k)) {
					NameValuePair param = new BasicNameValuePair(k, v.toString());
					nvps.add(param);
				}
			});
			return new URIBuilder(uri.toString()).addParameters(nvps).build();
		} catch (URISyntaxException e) {
			log.error("", e);
			return uri;
		}
	}

	public static String addParamsByMap(String uri, Map<String, Object> params) {
		try {
			return addParamsByMap(new URIBuilder(uri).build(), params).toString();
		} catch (URISyntaxException e) {
			log.error("", e);
			return uri;
		}
	}

	/**
	 * <p>功能描述: [给url添加多个参数] </p>
	 * @Title addParams
	 * @param uri 需要添加的uri
	 * @param params 参数的key value
	 * @return URI
	 */
	public static String addParams(String uri, Map<String, String> params) {
		return addParams(URI.create(uri), params).toString();
	}

	/**
	 * <p>功能描述: [去掉所有参数] </p>
	 * @Title clearParams
	 * @param uri .
	 * @return URI
	 */
	public static URI clearParams(URI uri) {
		try {
			return new URIBuilder(uri.toString()).clearParameters().build();
		} catch (URISyntaxException e) {
			log.error("", e);
			return uri;
		}
	}

	public static String clearParams(String uri) {
		try {
			return new URIBuilder(uri).clearParameters().build().toString();
		} catch (URISyntaxException e) {
			log.error("", e);
			return uri;
		}
	}

	/**
	 * 移除某一个查询类参数
	 * @param url 如http://128.0.0.1:90/abc?abc=edd
	 * @param parameterName 如 abc
	 * @return 如http://128.0.0.1:90/abc
	 */
	public static String removeParameter(String url, String parameterName) {
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			List<NameValuePair> queryParameters = uriBuilder.getQueryParams().stream()
					.filter(p -> !p.getName().equals(parameterName)).collect(Collectors.toList());
			if (queryParameters.isEmpty()) {
				uriBuilder.removeQuery();
			} else {
				uriBuilder.setParameters(queryParameters);
			}
			return uriBuilder.build().toString();
		} catch (URISyntaxException e) {
			log.error("", e);
		}
		return url;
	}

	/**
	 * <p>功能描述: [给url添加多个参数] </p>
	 * @Title addParam
	 * @param uri 需要添加的uri
	 * @param name 参数名称
	 * @param value 参数值
	 * @return URI
	 */
	public static URI addParam(URI uri, String name, String value) {
		try {
			if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
				return uri;
			}
			List<NameValuePair> paramsAlready = new URIBuilder(uri.toString()).getQueryParams();
			List<String> alreadyKey = new ArrayList<String>();
			paramsAlready.forEach(nameValues -> alreadyKey.add(nameValues.getName()));
			if (!alreadyKey.contains(name)) {
				return new URIBuilder(uri.toString()).addParameter(name, value).build();
			} else {
				return uri;
			}
		} catch (URISyntaxException e) {
			log.error("", e);
			return uri;
		}
	}

	/**
	 * <p>功能描述: [给url添加多个参数] </p>
	 * @Title addParam
	 * @param uri 需要添加的uri
	 * @param name 参数名称
	 * @param value 参数值
	 * @return URI
	 */
	public static URI addParam(String uri, String name, String value) {
		try {
			if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
				new URIBuilder(uri).build();
			}
			return addParam(new URIBuilder(uri).build(), name, value);
		} catch (URISyntaxException e) {
			log.error("", e);
			return null;
		}
	}

	public static List<String> getLocalHostLANAddress() throws UnknownHostException {
		List<String> resutLis = new ArrayList<String>();
		try {
			//InetAddress candidateAddress = null;
			// 遍历所有的网络接口
			for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
				NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
				// 在所有的接口下再遍历IP
				for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
						resutLis.add(inetAddr.getHostAddress());
					}
				}
			}
//            InetAddress address = null;
//            try {
//                address = Inet4Address.getLocalHost();
//            } catch (UnknownHostException e) {
//            	log.error("",e);
//                //e.printStackTrace();
//            }
			// if (candidateAddress != null) {
			// return candidateAddress;
			// }
			// // 如果没有发现 non-loopback地址.只能用最次选的方案
			// InetAddress jdkSuppliedAddress = Inet4Address.getLocalHost();
			// if (jdkSuppliedAddress == null) {
			// throw new UnknownHostException("The JDK InetAddress.getLocalHost() method
			// unexpectedly returned null.");
			// }
			// return jdkSuppliedAddress;
		} catch (Exception e) {
			UnknownHostException unknownHostException = new UnknownHostException(
					"Failed to determine LAN address: " + e);
			unknownHostException.initCause(e);
			throw unknownHostException;
		}
		return resutLis;
	}

	public static String decode(String url) {
		String result = "";
		try {
			result = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
			result = "";
		}
		return result;
	}

	public static String encode(String url) {
		String result = "";
		try {
			result = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
			result = "";
		}
		return result;
	}

	/**
	 * 组装URI,用于组装PathVariable注解作用参数,如http://127.0.0.1/{v1}/{abc}
	 * @param url 如http://127.0.0.1/{v1}/{abc}
	 * @param variables 动态参数将按传入顺序替换PathVariable
	 * @return URI
	 */
	public static URI addURIVariables(String url, Object... variables) {
		URI uri = new DefaultUriBuilderFactory().expand(url, variables);
		return uri;
	}

	/**
	 * 组装URI,用于组装PathVariable注解作用参数,如http://127.0.0.1/{v1}/{abc}
	 * @param url 如http://127.0.0.1/{v1}/{abc}
	 * @param variables 动态参数将按传入顺序替换PathVariable
	 * @return String
	 */
	public static String addUrlVariables(String url, Object... variables) {
		URI uri = addURIVariables(url, variables);
		return uri == null ? null : uri.toString();
	}
	

	/**
	 * 组装URI,用于组装PathVariable注解作用参数,如http://127.0.0.1/{v1}/{abc}
	 * @param url 如http://127.0.0.1/{v1}/{abc}
	 * @param variables 动态参数Key为v1或abc,value为参数值
	 * @return String
	 */
	public static URI addURIVariables(String url, Map<String, Object> variables) {
		URI uri = new DefaultUriBuilderFactory().expand(url, variables);
		return uri;
	}

	/**
	 * 组装URI,用于组装PathVariable注解作用参数,如http://127.0.0.1/{v1}/{abc}
	 * @param url 如http://127.0.0.1/{v1}/{abc}
	 * @param variables 动态参数Key为v1或abc,value为参数值
	 * @return String
	 */
	public static String addUrlVariables(String url, Map<String, Object> variables) {
		URI uri = addURIVariables(url, variables);
		return uri == null ? null : uri.toString();
	}
}
