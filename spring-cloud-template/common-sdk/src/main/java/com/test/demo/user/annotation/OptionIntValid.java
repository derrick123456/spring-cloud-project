/**   
* 
* @Description: 校验整数型参数在指定的选项内,如用于性别校验1,2 只能填写1或者2
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

/**   
* Copyright: Copyright (c) 2021
* 
* @ClassName: EnumValidator.java
* @Description: 校验整数型参数在指定的选项内,如用于性别校验1,2 只能填写1或者2
*/
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { OptionStrValid.EnumValidator.class })
public @interface OptionIntValid {

	/**
	 * 
	* @Function: EnumValid.java
	* @Title: message
	* @Description: 异常消息
	* @return
	* String
	 */
	String message() default "参数请填写指定的整数数值选项";

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
	int[] options();

	/**
	 * 
	* @Function: EnumValid.java
	* @Title: ignoreEmpty
	* @Description: 是否忽略null或空值,默认忽略
	* @return
	* boolean
	 */
	boolean ignoreEmpty() default true;

	@Slf4j
	class EnumValidator implements ConstraintValidator<OptionIntValid, Integer> {

		private OptionIntValid optionStrValid;

		@Override
		public void initialize(OptionIntValid constraintAnnotation) {
			optionStrValid = constraintAnnotation;
		}

		@Override
		public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
			boolean result = true;
			Integer[] integers = Arrays.stream(optionStrValid.options()).boxed().toArray(Integer[]::new);
	        List<Integer> listStrings = Arrays.asList(integers);
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
