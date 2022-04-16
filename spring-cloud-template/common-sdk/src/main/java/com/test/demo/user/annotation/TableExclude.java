package com.test.demo.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: TableExclude.java </p> 
 * <p>描述: 指定不是数据库字段的属性 </p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableExclude {

	/**
	 * 是否包含该字段
	 * @return
	 */
	// public boolean isInclude() default true;
}
