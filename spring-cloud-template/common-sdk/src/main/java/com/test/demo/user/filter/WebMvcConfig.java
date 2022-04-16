package com.test.demo.user.filter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 更改自己的拦截器和拦截路径
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Bean
	public CommonInterceptor getCommonInterceptor() {
		return new CommonInterceptor();
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
//        CommonInterceptor loginInterceptor = new CommonInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(getCommonInterceptor());
        // 拦截路径
        loginRegistry.addPathPatterns("/**");
        // 排除路径
        loginRegistry.excludePathPatterns("/");
        loginRegistry.excludePathPatterns("/login");
        loginRegistry.excludePathPatterns("/loginout");
        // 排除资源请求
        loginRegistry.excludePathPatterns("/css/login/*.css");
        loginRegistry.excludePathPatterns("/js/login/**/*.js");
        loginRegistry.excludePathPatterns("/image/login/*.png");
    }
}
