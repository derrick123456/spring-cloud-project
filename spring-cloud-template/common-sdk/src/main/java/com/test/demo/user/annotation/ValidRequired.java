package com.test.demo.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: TableExclude.java </p> 
 * <p>描述: 用以标注controller VO内List属性是否必填项</p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRequired {

	public boolean required() default true;
}
