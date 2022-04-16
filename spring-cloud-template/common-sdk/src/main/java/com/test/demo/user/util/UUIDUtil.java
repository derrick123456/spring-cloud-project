package com.test.demo.user.util;

import java.util.UUID;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: UUIDUitl.java </p> 
 * <p>类型描述: [UUID生成工具] </p>
 */
public class UUIDUtil {

	/**
	 * <p>功能描述: [生成uuid] </p>
	 * @Title randomUUID
	 * @param rmLine:是否删除横线,true删除
	 * @param upperCase:是否转换大写
	 * @return String
	 */
	public static String randomUUID(boolean rmLine, boolean upperCase) {
		String uuid = generateUUID();
		uuid = rmLine ? uuid.replaceAll("-", "") : uuid;
		uuid = upperCase ? uuid.toUpperCase() : uuid;
		return uuid;
	}

	/**
	 * <p>功能描述: [生成一个常规UUID,小写,无横线] </p>
	 * @Title randomUUID
	 * @return String UUID
	 */
	public static String randomUUID() {
		return randomUUID(true, false);
	}

	private static String generateUUID() {
		return UUID.randomUUID().toString();
	}
}
