package com.test.demo.user.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: ApiVersion.java </p> 
 * <p>描述: 自动义版本注解 </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

	/**
	 * <p>功能描述:标识版本号 </p>
	 * @Title value
	 * @return int .
	 */
	int value();

}
