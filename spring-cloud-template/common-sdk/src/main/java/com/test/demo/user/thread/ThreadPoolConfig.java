package com.test.demo.user.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolConfig
 *
 * @author 519001
 * @date 2021/2/22
 */
@Configuration
public class ThreadPoolConfig {
    private final int cpuNum=Runtime.getRuntime().availableProcessors();
    @Bean(CustomizeThreadPoolBeanName.REPORT_SEARCH_RECORD_POOL)
    public ThreadPoolTaskExecutor initReportSearchRecordPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cpuNum);
        executor.setMaxPoolSize(2*cpuNum);
        executor.setKeepAliveSeconds(120);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("record.push.");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }


    public interface CustomizeThreadPoolBeanName {
        /**
         * 报表调用日志推送线程池名
         */
        String REPORT_SEARCH_RECORD_POOL = "report.search.record.pool";
        String GROOVY_EXECUTOR_POOL = "groovy.executor.pool";
        String SEND_RECORD_KAFKA_POOL = "send.record.kafka.pool";
    }

    @Bean(CustomizeThreadPoolBeanName.SEND_RECORD_KAFKA_POOL)
    public ThreadPoolTaskExecutor initSendRecordKafkaPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cpuNum);
        executor.setMaxPoolSize(2*cpuNum);
        executor.setKeepAliveSeconds(120);
        executor.setQueueCapacity(2*cpuNum);
        executor.setThreadNamePrefix("send.record.");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
