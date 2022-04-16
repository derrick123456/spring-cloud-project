package com.test.demo.user.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * spring-boot 启动入口
 *
 * @author wgg
 * @date 2021/04/05
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.test.demo.service.mapper")
@ComponentScan("com.test.demo")
public class ApplicationService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationService.class, args);
    }
}
