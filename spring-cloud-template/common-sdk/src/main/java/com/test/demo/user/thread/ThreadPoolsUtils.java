package com.test.demo.user.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qwj
 * @description: 线程池初始化
 * @date 2020/11/24  17:06
 */
@Component
public class ThreadPoolsUtils implements  InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolsUtils.class);

    private ThreadPoolExecutor threadPoolExecutor;

    private final int cpuNum=Runtime.getRuntime().availableProcessors();


    @Override
    public void afterPropertiesSet() throws Exception {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cpuNum);
        executor.setMaxPoolSize(2*cpuNum);
        executor.setKeepAliveSeconds(120);
        executor.setQueueCapacity(2*cpuNum);
        executor.setThreadNamePrefix("send.record.");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        System.out.println("线程池初始化");
        threadPoolExecutor = new ThreadPoolExecutor(5,5,5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(20),new ThreadPoolExecutor.AbortPolicy());
        logger.info("初始化threadPoolExecutor {}",threadPoolExecutor);
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

}
