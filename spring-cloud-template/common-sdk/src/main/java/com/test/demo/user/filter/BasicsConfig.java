package com.test.demo.user.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 
 * @author wgg
 * @date: 2020年04月05日
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.config.token")
public class BasicsConfig {

    private String securityKey;
    
    private Integer expireTime;
    
}
