package com.test.demo.gateway.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.test.demo.gateway.config.GatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author wgg
 * @Description
 * @date 2022/4/16 8:57
 **/
@Slf4j
@DependsOn({"gatewayConfig"})
@Component
public class DynamicRouteServiceImplByNacos {
    /**
     * Nacos 配置服务
     */
    private ConfigService configService;

    @Autowired
    private GatewayConfig gatewayConfig;

    private final DynamicRouteServiceImpl dynamicRouteService;

    // 通过构造方法注入
    public DynamicRouteServiceImplByNacos(DynamicRouteServiceImpl dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    /**
     * 初始化 Nacos Config
     */
    private ConfigService initConfigService(){

        try{
            Properties properties = new Properties();
            properties.setProperty("serverAddr",gatewayConfig.getNacosServerAddr());
            properties.setProperty("namespace",gatewayConfig.getNacosNamespace());
            return configService = NacosFactory.createConfigService(properties);

        }catch (Exception ex){
            log.error("init gateway nacos config error: [{}]",ex.getMessage(),ex);
            return null;
        }
    }

    /**
     * 监听 Nacos 下发的动态路由配置
     * @param dataId
     * @param group
     */
    private void dynamicRouteByNacosListener(String dataId, String group){
        try {
            //给 Nacos Config客户端增加一个监听器
            configService.addListener(dataId, group, new Listener() {

                /**
                 * 自己提供线程池执行操作
                 * @return
                 */
                @Override
                public Executor getExecutor() {
                    return null;
                }

                /**
                 * 监听器收到配置更新
                 * @param configInfo Nacos 中最新的配置定义
                 */
                @Override
                public void receiveConfigInfo(String configInfo) {

                    log.info("start to update config: [{}]", configInfo);
                    List<RouteDefinition> definitionList =
                            JSON.parseArray(configInfo,RouteDefinition.class);
                    log.info("update route:[{}]",definitionList.toString());
                    dynamicRouteService.updateList(definitionList);
                }
            });
        }catch (NacosException ex){
            log.error("dynamic update gateway config error: [{}]", ex.getMessage(),ex);
        }
    }


    /**
     * Bean在容器中构造完成之后会执行 init 方法
     */
    @PostConstruct
    public void init(){

        log.info("gateway route init....");

        try{
            //初始化 Nacos 配置客户端
            configService = initConfigService();
            if (null == configService){
                log.error("init config service fail");
                return;
            }

            //通过 Nacos Config 并指定路由配置去获取路由配置
            String configInfo = configService.getConfig(
                    gatewayConfig.getNacosRouteDataId(),
                    gatewayConfig.getNacosRouteGroup(),
                    GatewayConfig.getDefaultTimeout()
            );

            log.info("get current gateway config: [{}]", configInfo);
            List<RouteDefinition> definitionList =
                    JSON.parseArray(configInfo, RouteDefinition.class);

            if (!CollectionUtils.isEmpty(definitionList)){
                for (RouteDefinition definition : definitionList){
                    log.info("init gateway config: [{}]",definition.toString());
                    dynamicRouteService.addRouteDefinition(definition);
                }
            }

        }catch (Exception ex){
            log.error("gateway route init has some error:[{}]", ex.getMessage(), ex);
        }

        // 设置监听器
        dynamicRouteByNacosListener(gatewayConfig.getNacosRouteDataId(),
                gatewayConfig.nacosRouteGroup);
    }


}
