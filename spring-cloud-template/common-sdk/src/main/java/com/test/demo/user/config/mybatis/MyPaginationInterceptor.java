package com.test.demo.user.config.mybatis;

import com.baomidou.mybatisplus.core.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.test.demo.user.config.mybatisplus.MybatisPlusPagePluginInterceptor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 继承com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
 * 重写queryTotal()方法
 * 实现：设置请求的页面大于最大页后操作， overflow==true调回到末页，overflow==false 继续请求  默认false
 */

@Setter
@Accessors(chain = true)
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MyPaginationInterceptor extends MybatisPlusPagePluginInterceptor {
    /**
     * 查询总记录条数
     *
     * @param sql             count sql
     * @param mappedStatement MappedStatement
     * @param boundSql        BoundSql
     * @param page            IPage
     * @param connection      Connection
     */
    @Override
    protected void queryTotal(boolean overflowCurrent, String sql, MappedStatement mappedStatement, BoundSql boundSql, IPage<?> page, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), boundSql);
            parameterHandler.setParameters(statement);
            long total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
            page.setTotal(total);
            /*
             * 溢出总页数，设置第一页
             */
            long pages = page.getPages();
            if (overflowCurrent && page.getCurrent() > pages) {
                // 设置为第一条
                //page.setCurrent(1);
                //设置为最后一页
                page.setCurrent(pages);
            }
            
        } catch (Exception e) {
            throw ExceptionUtils.mpe("Error: Method queryTotal execution error of sql : \n %s \n", e, sql);
        }
    }
}
