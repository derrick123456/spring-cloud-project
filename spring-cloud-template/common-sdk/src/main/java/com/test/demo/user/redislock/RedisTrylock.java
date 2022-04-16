package com.test.demo.user.redislock;

import java.lang.annotation.*;

/**
 * @Author: wgg
 * @Date: 2022/1/14
 * @Descr:分布式锁
 * 1.使用分布式锁方式，在controller层所需要的方法上标注此注解即可；
 * 2.锁默认过期时间为60秒，60秒内没有续期将会强制释放；
 * 3.超时等待时间是30秒，在这30秒内每个5秒重试一次获取锁，超过30秒拿不到锁的线程将不会执行业务
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisTrylock{
}