package com.test.demo.user.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>文件名称: SrpingBeanUtil.java </p>
 * <p>类型描述: [从spring容器中获取bean工具] </p>
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

	/**
	 * <p>字段描述:[应用程序上下文]</p>
	 * @Fields applicationContext :
	 */
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeanUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * <p>功能描述: [通过bean名称从spring容器中获取bean对象] </p>
	 * @Title getBean
	 * @param <T> .
	 * @param type .
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> type) throws BeansException {
		if (null == applicationContext){
			System.out.println("---------------/applicationContext is null");
			return null;
		}
		return applicationContext.getBean(type);
	}



}
