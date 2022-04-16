/**   
* 
* @Description: controller DTO校验工具,适用于List以及DTO对象
* @Package: com.idea.platform.common.util 
* @author: wgg
* @date: 2020年10月22日 下午2:27:04 
*/
package com.test.demo.user.util;

import com.test.demo.user.annotation.ValidRequired;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
* Copyright: Copyright (c) 2021
* 
* @ClassName: ValidUtil.java
* @Description: controller DTO校验工具,适用于List以及DTO对象
*/
@Slf4j
public class ValidUtil {

	/**
	 * 
	* @Function: ValidUtil.java
	* @Title: validList
	* @param params 需要校验的List对象
	* @Description: <p>校验controller层普通List参数对象,如:public CommonRsp<?> post(@RequestBody List<InvestPostDto> params) {} </p>
	* <p>使用该方法手工校验可以免写@Valid注解,兼容List嵌套对象,如List嵌套List </p>
	* <p>使用该方法手工校验可以支持原生List,不用改成ValidArrayList </p>
	* void
	 */
	public static <T> void validList(List<T> params, boolean required) {
		validList(params, required, null);
	}

	public static <T> void validList(List<T> params, boolean required,String msg) {
		if (!CollectionUtils.isEmpty(params)) {
			for (int i = 0; i < params.size(); i++) {
//				valid(params.get(i), i);
				List<Map<String, Object>> dgList = getLists(params.get(i));
				for (int j = 0; j < dgList.size(); j++) {
					validList((List) dgList.get(j).get("object"), (Boolean) dgList.get(j).get("required"),msg);
				}
			}
		}
	}

	

	private static <T> List<Map<String, Object>> getLists(T object) {
		Field[] fields = object.getClass().getDeclaredFields();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Map<String, Object> temp = null;
		for (Field field : fields) {
			Class<?> type = field.getType();
			if (type.getName().equals("java.util.List")) {
				temp = new HashMap<String, Object>();
				ValidRequired validRequired = field.getAnnotation(ValidRequired.class);
				temp.put("required", validRequired == null ? true : validRequired.required());
				Object objectList = null;
				try {
					Method getList = object.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
					objectList = getList.invoke(object);
					// temp.put("object", objectList);
				} catch (Exception e) {
					log.error("", e);
				} finally {
					temp.put("object", objectList);
				}
				resultList.add(temp);
			}
		}
		return resultList;
	}

//	private static <T> void valid(T object, int index) {
//		Validator validator = SpringBeanUtil.getBean(Validator.class);
//		BindingResult result = (new DataBinder(object)).getBindingResult();
//		validator.validate(object, result);
//		if (result != null && result.hasErrors()) {
//			StringBuilder validMsg = new StringBuilder();
//			// result.getAllErrors().forEach(x -> erorMsg.append(" " +
//			// x.getDefaultMessage()));
//			String rowMsg = "";
////			Object[] codes = result.getAllErrors().get(0).getArguments();
////			if (codes != null && codes.length > 0) {
////				JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(codes[0]));
////				String code = jsonObject.getString("code");
////				List<String> list = extractMessageByRegular(code);
////				rowMsg = CollectionUtils.isEmpty(list)?"":list.get(0);
////				rowMsg=StringUtils.isEmpty(rowMsg)?"":"第["+rowMsg+"]行：";
////			}
//			validMsg.append(rowMsg + result.getAllErrors().get(0).getDefaultMessage());
////			throw new BusinessException(MessageUtil.validMsg("第[" + index + "]行：" + validMsg.toString()));
//		}
//	}

}
