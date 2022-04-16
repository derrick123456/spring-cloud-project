package com.test.demo.user.service.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.net.URL;
import java.util.*;

public class CodeGeneratorMainUtils {
    //代码生成器
    private static AutoGenerator mpg = new AutoGenerator();
    //全局配置
    private static  GlobalConfig gc = new GlobalConfig();
    //作者、包名、去除表前缀
    private static final String author = "wgg";
    private static final String package_name = "com.test.demo";
    private static final String TABLE_PREFIX = "";
    //数据库
    private static final String url = "jdbc:mysql://10.83.193.24:13300/bigdata_report_dev?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false";
    private static final String driverName = "com.mysql.jdbc.Driver";
    private static final String userName = "bdata";
    private static final String password = "b_data7890";
    private static final String[] tables =new String[] {"redis_config"};

    public static void main(String[] args){
        // 数据源配置
        setDataSource();
        // 全局配置
        setGlobalConfig();
        // 策略配置
        setStrategy();
        //模板配置
        setTemplate();
        //执行
        mpg.execute();
    }

    private static void setDataSource() {
        DataSourceConfig dsc = new DataSourceConfig();

        dsc.setUrl(url);
        dsc.setDriverName(driverName);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);
    }

    private static void setTemplate() {
        // 配置模板
        TemplateConfig tc = new TemplateConfig();
         tc.setController("templates/controller.java");// /templates/entity.java 模板路径配置，默认再templates
        tc.setEntity("templates/entity.java");
        tc.setXml("");
        mpg.setTemplate(tc);

        // 模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    }

    private static void setStrategy() {
        StrategyConfig strategy = new StrategyConfig();

        // 类名：Tb_userController -> TbUserController
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 属性名：start_time -> startTime
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 代替 setter/getter等方法
        strategy.setEntityLombokModel(true);
        // 设置Controller为RestController
        strategy.setRestControllerStyle(true);
        //由数据库该表生成
        strategy.setInclude(tables);
        //是否生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);



        //去除表前缀 
        //strategy.setTablePrefix();


        mpg.setStrategy(strategy);


    }

    private static void setGlobalConfig() {
        URL urlPath = Thread.currentThread().getContextClassLoader().getResource("");
        //String projectPath = Objects.requireNonNull(urlPath).getPath().replace("target/classes", "src/main/java");
        String projectPath="D:/gener";
        gc.setBaseResultMap(true);
        gc.setOutputDir(projectPath);//代码生成位置
        gc.setFileOverride(false);//默认 false ,是否覆盖已生成文件
        gc.setAuthor(author);
        gc.setSwagger2(true);//开启 swagger2 模式
        gc.setIdType(IdType.UUID);//主键ID类型
        gc.setDateType(DateType.TIME_PACK);//设置时间类型为Date
        gc.setBaseColumnList(true); //默认false  和basemodel相似
//        gc.setEntityName("%sDO");//可以给实体bean加后缀

        mpg.setGlobalConfig(gc);

        PackageConfig pc = new PackageConfig();// 包配置
        pc.setParent(package_name);
        pc.setEntity("model.entity");
       // pc.setController("controller");
       // pc.setService("service");
        //pc.setServiceImpl("service.impl");

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-rb");
                this.setMap(map);
            }
        };

        // 调整 xml 生成目录演示
        String xmlPath = Objects.requireNonNull(urlPath).getPath().replace("target/classes", "src/main/resources");

        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath+ "/mapping/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        mpg.setPackageInfo(pc);
    }




}
