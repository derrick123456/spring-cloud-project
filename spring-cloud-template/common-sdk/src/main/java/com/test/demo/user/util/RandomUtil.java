package com.test.demo.user.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;


/**
 * <p>文件名称: RandomUtil.java </p>
 * <p>类型描述: [随机字符或数字生成工具] </p>
 */
public class RandomUtil extends RandomStringUtils {

	private static final String RANDOM_NUMBER = "0123456789";

	private static final String RANDOM_NUMBER_LETTER = "0123456789zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP";

	/**
	 * <p>功能描述: [生成指定范围的随机数,正整数] </p>
	 * @Title nextInt
	 * @param limitStart .
	 * @param limitEnd .
	 * @return Integer
	 */
	public static Integer nextInt(Integer limitStart, Integer limitEnd) {
		Integer limitStartTemp = limitStart == null ? 300 : limitStart;
		Integer limitEndTemp = limitEnd == null ? 900 : limitEnd;
		Random random = new Random();
		return random.nextInt(limitEndTemp) % (limitEndTemp - limitStartTemp + 1) + limitStartTemp;
	}

	/**
	 * 
	* @Function: RandomUtil.java
	* @Title: randomNumber
	* @Description: 生成指定长度的仅含数字的字符串
	 */
	public static String randomNumber(int length) {
		return random(length, RANDOM_NUMBER);
	}

	/**
	 * 
	* @Function: RandomUtil.java
	* @Title: randomNumberLetter
	* @Description: 生成指定长度的含数字和大小写字母混合的字符串
	* @return  String
	 */
	public static String randomNumberLetter(int length) {
		return random(length, RANDOM_NUMBER_LETTER);
	}

}
