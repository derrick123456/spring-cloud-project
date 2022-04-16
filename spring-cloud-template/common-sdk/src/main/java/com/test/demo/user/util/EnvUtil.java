package com.test.demo.user.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

/**
 * @author wgg
 * @date 2021/04/05
 */
@DependsOn
public class EnvUtil {

    private static Environment environment = SpringBeanUtil.getBean(Environment.class);

    /**
     * @param <T>
     * @param key          属性名称,不支持驼峰规则
     * @param targetClass  目标数据类型,如String.class
     * @param defaultValue 如果配置属性不存在时,默认值是啥 如,abc
     * @return <T> T 属性value
     */
    public static <T> T getKey(String key, Class<T> targetClass, T defaultValue) {
        if (null == environment){
            System.out.println("environment is null======");
        }
        return environment.getProperty(key, targetClass, defaultValue);
    }

    /**
     * @param <T>         支持List类型获取,但是yml配置 如: abc: efg,jsj,ska仅支持该种配置
     * @param key         属性名称,不支持驼峰规则
     * @param targetClass 目标数据类型,如String.class
     * @return <T> T 属性value
     */
    public static <T> T getKey(String key, Class<T> targetClass) {
        return environment.getProperty(key, targetClass);
    }

    /**
     * 获取 spring-boot当前运行环境配置属性
     *
     * @param key 属性名称,不支持驼峰规则
     * @return <T> T 属性value
     */
    public static String getKey(String key, String defaultValue) {
            return environment.getProperty(key, defaultValue);
    }

    /**
     * 获取 spring-boot当前运行环境配置属性
     *
     * @param key 属性名称,不支持驼峰规则
     * @return String 属性value
     */
    public static String getKey(String key) {
        return environment.getProperty(key);
    }

    /**
     * 获取 spring-boot当前运行环境,如[test,dev]
     *
     * @return String 属性value
     */
    public static String[] getActiveProfiles() {
        return environment.getActiveProfiles();
    }

    /**
     * 获取 spring-boot当前运行环境,如test
     *
     * @return String 属性value
     */
    public static String getActiveProfile() {
        String[] profiles = environment.getActiveProfiles();
        return profiles == null || profiles.length < 0 ? null : profiles[0];
    }

    /**
     * 获取 spring-boot当前运行环境,如test
     *
     * @return String 属性value
     */
    public static String getActiveProfile(String defaultValue) {
        String[] profiles = environment.getActiveProfiles();
        return profiles == null || profiles.length < 0 ? defaultValue : profiles[0];
    }

    /**
     * 对比当前运行环境是否生产环境,pro,product,produce将当生产环境
     *
     * @param defaultValue
     * @return
     */
    public static boolean isProduce(String defaultValue) {
        String profile = getActiveProfile(defaultValue);
        profile = StringUtils.isEmpty(profile) ? "" : profile;
        return StringUtils.equalsIgnoreCase("pro", profile) || StringUtils.equalsIgnoreCase("product", profile)
                || StringUtils.equalsIgnoreCase("produce", profile);
    }

}
