package com.test.demo.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用来读取nacos相关的配置项，用于配置监听器
 */
@Configuration
public class GatewayConfig {

    /**
     * 读取配置的超时时间
     */
    public static final long DEFAULT_TIMEOUT = 30000;

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public String nacosServerAddr;

    @Value("${spring.cloud.nacos.discovery.namespace}")
    public  String nacosNamespace;

    @Value("${nacos.gateway.route.config.data-id}")
    public String nacosRouteDataId;

    @Value("${nacos.gateway.route.config.group}")
    public  String nacosRouteGroup;

    public static long getDefaultTimeout() {
        return DEFAULT_TIMEOUT;
    }

    public String getNacosServerAddr() {
        return nacosServerAddr;
    }

    public void setNacosServerAddr(String nacosServerAddr) {
        this.nacosServerAddr = nacosServerAddr;
    }

    public String getNacosNamespace() {
        return nacosNamespace;
    }

    public void setNacosNamespace(String nacosNamespace) {
        this.nacosNamespace = nacosNamespace;
    }

    public String getNacosRouteDataId() {
        return nacosRouteDataId;
    }

    public void setNacosRouteDataId(String nacosRouteDataId) {
        this.nacosRouteDataId = nacosRouteDataId;
    }

    public String getNacosRouteGroup() {
        return nacosRouteGroup;
    }

    public void setNacosRouteGroup(String nacosRouteGroup) {
        this.nacosRouteGroup = nacosRouteGroup;
    }
}
