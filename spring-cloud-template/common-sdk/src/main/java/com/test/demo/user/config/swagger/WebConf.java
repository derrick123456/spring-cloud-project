package com.test.demo.user.config.swagger;

import com.test.demo.user.util.EnvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * <p>文件名称: WebConfig.java </p>
 * <p>类型描述: [静态资源文件配置] </p>
 * <p>类型描述: 解决spring boot web默认配置失效问题以及Jackson JDK1.8日期时间格式支持 </p>
 */
@Configuration
public class WebConf implements WebMvcConfigurer {
    
    @Autowired
    private Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!EnvUtil.isProduce("produce")) {// 非生产环境才开启资源文件映射
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
            registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        }
        // 解决 SWAGGER 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/includeBootstrap.js")
            .addResourceLocations("classpath:/static/includeBootstrap.js");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#
     * addViewControllers(org.springframework.web.servlet.config.annotation.
     * ViewControllerRegistry)
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (!EnvUtil.isProduce("produce")) {
            registry.addViewController("/api-doc.html").setViewName("forward:static/api-doc.html");
        }
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }
    
    @SuppressWarnings("unchecked")
	@Bean
    public CorsFilter corsFilter() {
        List<String> allowedOrigins = Arrays.asList("*");
        allowedOrigins = environment.getProperty("common.config.cors.allowedOrigin", List.class, allowedOrigins);
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(corsConfigurationSource);
    }
}
