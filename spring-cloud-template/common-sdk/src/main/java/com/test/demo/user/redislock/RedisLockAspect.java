package com.test.demo.user.redislock;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * @Author: wgg
 * @Date: 2022/1/7
 * @Descr:
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {


    private static final String REDIS_LOCK_KEY = "REDIS_LOCK";
    private static final String LOCK_SUCCESS = "OK";
    private static final long LOCK_EXPIRE_SECONDS = 3000 * 1000L;
    private static final long WAITING_TIME = 10 * 1000L;
    private static final long WAITING_TIMEOUT = 30 * 1000L;
    private String currentLockId ;

    @Autowired(required = false)
    private Jedis jedis;

    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;


    @Around("@annotation(redisLock)")
    public Object redisLockPoint(ProceedingJoinPoint point, RedisTrylock redisLock) {
        String lockKey = null;
        boolean flag = false;
        String lockId=null;
        System.out.println("当前线程ID:" + Thread.currentThread().getId());
        try {
//            String serverInfo = String.format("%s_%s_%s", "dev", "127.0.0.1", "8080");
            String method = point.getSignature().toString().trim();
            String uuId = UUID.randomUUID().toString();
            Long threadId = Thread.currentThread().getId();
            //示例：REDIS_LOCK_dev_10.32.51.176_13503_Map_com.kyexpress.bigdata.report.provider.controller.RedisTestController.del(BaseIdsBO)
            lockKey = String.format("%s_%s", REDIS_LOCK_KEY, method).replace(" ", "_");
            //示例：290491b4-1058-4b1f-b8c1-37b8bf1b9916_dev_10.32.51.176_13503_Map com.kyexpress.bigdata.report.provider.controller.RedisTestController.del(BaseIdsBO)_272
             lockId  = String.format("%s_%s_%s", uuId, method, threadId);
            System.out.print("前lockId:"+lockId);
            flag = tryLock(lockKey, lockId);
            System.out.print("后lockId:"+lockId);
            if (flag) {
                System.out.println("---------------------------------------------------------加锁成功线程ID：" + threadId+"----lockId:"+lockId);
                log.info("---------------redis lock result:{}", flag);
                return point.proceed();
            } else {
                System.out.println("@@@@@@@获取锁失败等待超时发送消息:" + threadId);
            }
        } catch (Exception e) {
            log.error("RedisLockAspect.redisLockPoint error!", e);
        } catch (Throwable throwable) {
            log.error("业务方法执行异常：" + point.getSignature().getName());
        } finally {
            if (flag) {
                unlock(lockKey,lockId);
                log.info("redis unlock:{}", lockKey);
            }
        }
        return null;
    }

    /**
     * 加锁
     * 机制：
     *
     * @param key
     * @param value
     * @return
     */
    public boolean tryLock(String key, String value) {

        //请求锁时间
        long requestTime = System.currentTimeMillis();
        while (true) {
            //等待锁时间
            long watiTime = System.currentTimeMillis()- requestTime;
            //如果等待锁时间超过30s，加锁失败
            if (watiTime > WAITING_TIMEOUT ) {
                log.info("redis wait timeout! kye:{},value:{}", key, value);
                System.out.println("30s等待超时啦！！" + Thread.currentThread().getId());
                return false;
            }
            RedisConnection connection = null;
            String result = "";
            try {
//                connection = RedisConnectionUtils.getConnection(redisTemplate.getConnectionFactory());
//                Jedis jds = (Jedis) connection.getNativeConnection();
//                jds.set("aaa", "123");
//                System.out.println(jds.get("aaa"));
//                connection = redisTemplate.getConnectionFactory().getConnection();
//                Object nativeConnection = connection.getNativeConnection();
//
//                if (nativeConnection instanceof Jedis) {
//                    result = ((Jedis) nativeConnection).set(key, String.valueOf(System.currentTimeMillis()), "NX", "EX", EXPIRE_SECONDS);
//                } else {
//                    log.error("unknown redis client type! " + nativeConnection.getClass());
//                }
//                long lockTime = System.currentTimeMillis();
                result = jedis.set(key, value, "NX", "PX", LOCK_EXPIRE_SECONDS);
                if (LOCK_SUCCESS.equalsIgnoreCase(result)) {
                    currentLockId = value;
                    log.info("redis tryLock success kye:{},value:{}", key, value);
                    return true;
                } else {
                    System.out.println("-------没有获取到锁，线程ID：" + Thread.currentThread().getId() + "--准备再次获取锁。");
                }
            } catch (Exception e) {
                log.error("redis tryLock error!", e);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable t) {
                        log.error("redis connection close error!", t);
                    }
                }
            }
            //加锁时间超过过期时间，删除key，防止死锁
//            String lockValue = jedis.get(key);
//            String str[] = lockValue.split(":");
//            long lockTime = Long.parseLong(str[str.length - 1]);
//            long waitTie = System.currentTimeMillis() - lockTime;
//            if (waitTie > LOCK_EXPIRE_SECONDS) {
//                try {
//                    jedis.del(key);
//                    log.info("redis tryLock timeout delete success! key:{}, value:{}", key, value);
//                    System.out.println("====等待了一分钟删锁成功");
//                } catch (Exception e) {
//                    log.error("redis delete lock error! key:{}, value:{}", key, value);
//                    e.printStackTrace();
//                }
//                return false;
//            }
            //获取锁失败，等待5秒继续请求
            try {
                log.info("waiting 5s key:{},value:{}", key, value);
                Thread.sleep(WAITING_TIME);
            } catch (InterruptedException e) {
                log.error("wait 2s error! key:{},value:{}", key, value);
            }
        }
    }


    /**
     * 解锁
     *
     * @param key
     */
    public void unlock(String key,String v) {
        try {
            String value = jedis.get(key);
            if (!StringUtils.isEmpty(value)) {
                if (value.equals(v)) {
                    jedis.del(key);
                    System.out.println("===========================================================当前线程ID解锁成功:" + Thread.currentThread().getId()+"---lockId:"+v);
                }else {
                    System.out.print("##########################解锁ID不一致：v:"+v+" value:"+value);
                }
            } else {
                System.out.println("---锁已经过期--key:" + key);
            }
        } catch (Exception e) {
            log.error("unlock error!!!" + e);
        }
    }


}
