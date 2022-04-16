package com.test.demo.sys.config;

/**
 * @author wgg
 * @Description
 * @date 2022/4/15 8:34
 **/

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@Data
@NacosConfigurationProperties(prefix = "nacosTest", dataId = "application.yaml", groupId = "DEV_GROUP", autoRefreshed = true)
public class NacosConfig {

    private List<String> config;

    private Map<String, String> map;

    @Override
    public String toString() {
        return "TestConfig{" + "config=" + config + ", map=" + map + '}';
    }

}
