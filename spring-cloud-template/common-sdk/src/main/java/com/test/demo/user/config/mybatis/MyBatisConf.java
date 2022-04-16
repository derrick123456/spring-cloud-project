package com.test.demo.user.config.mybatis;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.test.demo.user.config.mybatisplus.MybatisPlusPagePluginInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @Author wgg
 * @Date 2021/04/05
 */
@Configuration
@MapperScan(basePackages = "com.test.demo.sys.mapper")
public class MyBatisConf implements TransactionManagementConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisConf.class);
    @Autowired
    private DataSource dataSource;

    /**
     * 配置事务管理
     *
     * @return
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 调整 SqlSessionFactory 为 MyBatis-Plus 的 SqlSessionFactory
     *
     * @return
     * @throws Exception
     */
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception {
        logger.info("==============初始化 sqlSessionFactory===============");
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        //设置数据源
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);

        mybatisSqlSessionFactoryBean.setPlugins(new Interceptor[]{paginationInterceptor()});
        mybatisSqlSessionFactoryBean.setConfiguration(configuration());
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig());

        //对应mybatis.mapper-locations配置
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapping/*.xml"));
        return mybatisSqlSessionFactoryBean;
    }


    /**
     * 分页插件
     */
    private MybatisPlusPagePluginInterceptor paginationInterceptor() {
        logger.info("==============初始化 paginationInterceptor===============");
        //自定义分页插件
        MybatisPlusPagePluginInterceptor paginationInterceptor = new MybatisPlusPagePluginInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到 [末页]，false 继续请求  默认false
        paginationInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);
        return paginationInterceptor;
    }

    /**
     * MyBatis-Plus 全局策略配置
     *
     * @return
     */
    private GlobalConfig globalConfig() {
        logger.info("==============初始化 globalConfig===============");
        GlobalConfig globalConfig = new GlobalConfig();

        /**
         * MyBatis-Plus 全局策略中的 DB 策略配置
         */
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();

        /**
         * 字段验证策略之 update
         * @since 3.1.2
         * private FieldStrategy updateStrategy = FieldStrategy.NOT_NULL;
         */
        dbConfig.setUpdateStrategy(FieldStrategy.IGNORED);
        globalConfig.setDbConfig(dbConfig);


        return globalConfig;
    }

    /**
     * 原生 MyBatis 所支持的配置
     * @return
     */
    private MybatisConfiguration configuration() {
        logger.info("==============初始化 configuration===============");
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();

        /**
         * 开启驼峰命名
         * 此属性在 MyBatis 中原默认值为 false
         * 如果您的数据库命名符合规则无需使用 @TableField 注解指定数据库字段名
         */
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        return mybatisConfiguration;
    }

}
