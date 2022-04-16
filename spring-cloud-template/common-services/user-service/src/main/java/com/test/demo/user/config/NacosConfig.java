package com.test.demo.user.config;

/**
 * @author wgg
 * @Description
 * @date 2022/4/15 8:34
 **/

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@Data
@ConfigurationProperties(prefix = "test")
public class NacosConfig {

    private List<String> list;

    private Map<String, String> map;

    @Override
    public String toString() {
        return "TestConfig{" + "config=" + list + ", map=" + map + '}';
    }

}
