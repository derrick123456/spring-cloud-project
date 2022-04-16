/**   
* 
* @Description: swagger动态新增API文档,注入参数是用
* @author: wgg
* @date: 2020年10月28日 下午6:10:17 
*/
package com.test.demo.user.enums;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**   
* Copyright: Copyright (c) 2020 
* 
* @ClassName: ParameterTypeEnum.java
* @Description: swagger动态新增API文档,注入参数是用
*
*/
public enum ParameterTypeEnum {

	QUERY, PATH, BODY;
	
	private static final Map<String, ParameterTypeEnum> mappings = new HashMap<>(3);

	static {
		for (ParameterTypeEnum httpMethod : values()) {
			mappings.put(httpMethod.name(), httpMethod);
		}
	}
	
	@Nullable
	public static ParameterTypeEnum resolve(@Nullable String method) {
		return (method != null ? mappings.get(method) : null);
	}

	public   boolean matches(String method) {
		return (this == resolve(method));
	}
}
