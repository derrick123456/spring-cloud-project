package com.test.demo.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: SearchValue.java </p> 
 * <p>类型描述: [模糊查询字段标识] </p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchValue {

}
