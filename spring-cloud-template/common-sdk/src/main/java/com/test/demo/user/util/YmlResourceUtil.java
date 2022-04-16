package com.test.demo.user.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * <p>文件名称: YmlResourceUtil.java </p>
 * <p>类型描述: [描述] </p>
 */
@Slf4j
public class YmlResourceUtil {
    

    /**
     * <p>功能描述: [获取application.yml配置文件信息,(仅application信息)] </p>
     * @Title readKey
     * @param key
     * @return
     */
    public static String readKey(String key) {
        try {
            YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
            // 可以加载多个yml文件
            yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
            Properties properties = yamlMapFactoryBean.getObject();
            // 获取yml里的参数
            return properties.getProperty(key);
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("Make sure that the \""+key+"\" configuration item exists in the application.yml file",e);
            return "";
        }
    }
    
    public static String readCommonKey(String key) {
        try {
            YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
            // 可以加载多个yml文件
            yamlMapFactoryBean.setResources(new ClassPathResource("common-base.yml"));
            Properties properties = yamlMapFactoryBean.getObject();
            // 获取yml里的参数
            return properties.getProperty(key);
        } catch (Exception e) {
            // e.printStackTrace();
            log.error("Make sure that the \""+key+"\" configuration item exists in the common-base.yml file",e);
            return "";
        }
    }
}
