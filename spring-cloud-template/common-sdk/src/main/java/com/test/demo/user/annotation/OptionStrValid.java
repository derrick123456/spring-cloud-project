/**   
* 
* @Description: 校验字符参数在指定选项内,如性别参数只能填写男或者女
* @author: wgg
* @date: 2020年10月29日 上午10:48:37 
*/
package com.test.demo.user.annotation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**   
* Copyright: Copyright (c) 2020 
* 
* @ClassName: EnumValidator.java
* @Description: 校验字符参数在指定选项内,如性别参数只能填写男或者女
*
*/
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { OptionStrValid.EnumValidator.class })
public @interface OptionStrValid {

	/**
	 * 
	* @Function: EnumValid.java
	* @Title: message
	* @Description: 异常消息
	* @return
	* String
	 */
	String message() default "参数请填写指定的字符串选项";

	// 作用参考@Validated和@Valid的区别
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

	/**
	 * 
	* @Function: OptionIntValid.java
	* @Title: options
	* @Description: 需要匹配的目标值,参数必须是目标值中的一个
	* @return
	* int[]
	 */
	String[] options();
	
	

	/**
	 * 
	* @Function: EnumValid.java
	* @Title: ignoreEmpty
	* @Description: 是否忽略null或空值,默认忽略
	* @return boolean 
	 */
	boolean ignoreEmpty() default true;
	
	/**
	 * 
	* @Function: OptionStrValid.java
	* @Title: ignoreCase
	* @Description: 是否区分大小写
	* @return boolean
	 */
	boolean ignoreCase() default false;

	@Slf4j
	class EnumValidator implements ConstraintValidator<OptionStrValid, String> {

		private OptionStrValid optionStrValid;

		@Override
		public void initialize(OptionStrValid constraintAnnotation) {
			optionStrValid = constraintAnnotation;
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
			boolean result = true;
			List<String> listStrings = Arrays.asList(optionStrValid.options());
			if(optionStrValid.ignoreCase()) {
				value=value.toUpperCase();
				listStrings = listStrings.stream().map(String::toUpperCase).collect(Collectors.toList());
			}
			if(value==null&&!optionStrValid.ignoreEmpty()) {//value等于null时
				result=false;
			}
			if(value!=null&&!listStrings.contains(value)) {
				result=false;
			}
			return result;
		}
	}

}
