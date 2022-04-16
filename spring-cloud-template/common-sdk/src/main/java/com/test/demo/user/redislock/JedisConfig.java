package com.test.demo.user.redislock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @Author: wgg
 * @Date: 2022/1/15
 * @Descr:
 */
@Configuration
public class JedisConfig {

    @Bean
    public Jedis jedisClient(){
        return new Jedis("127.0.0.1",6379);
    }
}
