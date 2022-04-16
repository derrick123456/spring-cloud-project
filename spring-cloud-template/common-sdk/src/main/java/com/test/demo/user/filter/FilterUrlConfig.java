package com.test.demo.user.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wgg
 * @date 2021/04/05
 */
@Component
@Data
@ConfigurationProperties(prefix = "filter-auth-url.config")
public class FilterUrlConfig {
	/**
	 * 不拦截的(不需要header添加authorization的通配符路径,见yaml配置文件)
	 */
	private List<String> unauthorizationUrl;
	
	private String tokenSplit;
	
	private List<String> unResponeUrl;

}
