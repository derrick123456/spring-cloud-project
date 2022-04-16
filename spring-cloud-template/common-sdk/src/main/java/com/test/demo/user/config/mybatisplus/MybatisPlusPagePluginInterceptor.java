package com.test.demo.user.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlParserUtils;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author wgg
 * @Date 2021/04/05
 */
@Setter
@Slf4j
@Accessors(chain = true)
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MybatisPlusPagePluginInterceptor extends AbstractSqlParserHandler implements Interceptor {


	/**
	 * COUNT SQL 解析
	 */
	private ISqlParser countSqlParser;
	/**
	 * 溢出总页数，设置第一页
	 */
	private boolean overflow = false;
	/**
	 * 单页限制 500 条，小于 0 如 -1 不受限制
	 */
	private long limit = 500L;
	/**
	 * 方言类型
	 */
	private String dialectType;
	/**
	 * 方言实现类<br>
	 * 注意！实现 com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect 接口的子类
	 */
	private String dialectClazz;

	/**
	 * 查询SQL拼接Order By
	 *
	 * @param originalSql 需要拼接的SQL
	 * @param page        page对象
	 * @return ignore
	 */
	public static String concatOrderBy(String originalSql, IPage<?> page) {
		if (CollectionUtils.isNotEmpty(page.orders())) {
			try {
				List<OrderItem> orderList = page.orders();
				Select selectStatement = (Select) CCJSqlParserUtil.parse(originalSql);
				if (selectStatement.getSelectBody() instanceof PlainSelect) {
					PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
					List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
					List<OrderByElement> orderByElementsReturn = addOrderByElements(orderList, orderByElements);
					plainSelect.setOrderByElements(orderByElementsReturn);
					return plainSelect.toString();
				} else if (selectStatement.getSelectBody() instanceof SetOperationList) {
					SetOperationList setOperationList = (SetOperationList) selectStatement.getSelectBody();
					List<OrderByElement> orderByElements = setOperationList.getOrderByElements();
					List<OrderByElement> orderByElementsReturn = addOrderByElements(orderList, orderByElements);
					setOperationList.setOrderByElements(orderByElementsReturn);
					return setOperationList.toString();
				}
//				else if (selectStatement.getSelectBody() instanceof WithItem) {
//					// todo: don't known how to resole
//					return originalSql;
//				} 
				else {
					return originalSql;
				}

			} catch (JSQLParserException e) {
				log.warn("failed to concat orderBy from IPage, exception=", e);
			}
		}
		return originalSql;
	}

	@NotNull
	private static List<OrderByElement> addOrderByElements(List<OrderItem> orderList,
			List<OrderByElement> orderByElements) {
		List<OrderByElement> result = new ArrayList<OrderByElement>();
		for (OrderItem item : orderList) {
			OrderByElement element = new OrderByElement();
			element.setExpression(new Column(item.getColumn()));
			element.setAsc(item.isAsc());
			result.add(element);
		}
		result.addAll(orderByElements);
		return result;
	}

	/**
	 * Physical Page Interceptor for all the queries with parameter
	 * {@link RowBounds}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		String sql = (String) metaObject.getValue("delegate.boundSql.sql");

		// 获取租户标识开始
		String tenant = null;
		// 获取租户标识结束

		// SQL 解析
		this.sqlParser(metaObject);

		// 先判断是不是SELECT操作 (2019-04-10 00:37:31 跳过存储过程) 新增存储过程多租户支持
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()
				|| StatementType.CALLABLE == mappedStatement.getStatementType()) {
			metaObject.setValue("delegate.boundSql.sql", sql);
			long startTime = System.currentTimeMillis();
			try {
				return invocation.proceed();
			} finally {
				long endTime = System.currentTimeMillis();
				long sqlCost = endTime - startTime;
				log.debug("\r\n mybatis execute SQL:*****\r\n{}\r\n*****\r\n\r\nexecute time-consuming:{}ms", sql,
						sqlCost);
			}
		}
		// 针对定义了rowBounds，做为mapper接口方法的参数
		BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
		Object paramObj = boundSql.getParameterObject();

		sql = (String) metaObject.getValue("delegate.boundSql.sql");
		IPage<?> page = null;
		if (paramObj instanceof IPage) {
			page = (IPage<?>) paramObj;
		} else if (paramObj instanceof Map) {
			for (Object arg : ((Map<?, ?>) paramObj).values()) {
				if (arg instanceof IPage) {
					page = (IPage<?>) arg;
					break;
				}
			}
		}

		// 不需要分页,处理多租户
		if (null == page || page.getSize() < 0) {
			metaObject.setValue("delegate.boundSql.sql", sql);
			long startTime = System.currentTimeMillis();
			try {
				return invocation.proceed();
			} finally {
				long endTime = System.currentTimeMillis();
				long sqlCost = endTime - startTime;
				log.debug("\r\n mybatis execute SQL:*****\r\n{}\r\n*****\r\n\r\nexecute time-consuming:{}ms", sql,
						sqlCost);
			}
		}
		if (limit > 0 && limit <= page.getSize()) {
			page.setSize(limit);
		}

		String originalSql = boundSql.getSql();
		Connection connection = (Connection) invocation.getArgs()[0];
		DbType dbType = StringUtils.isNotEmpty(dialectType) ? DbType.getDbType(dialectType)
				: JdbcUtils.getDbType(connection.getMetaData().getURL());

		sql = (String) metaObject.getValue("delegate.boundSql.sql");
		if (page.isSearchCount()) {
			SqlInfo sqlInfo = SqlParserUtils.getOptimizeCountSql(page.optimizeCountSql(), countSqlParser, originalSql);
			String totalSQL  = sqlInfo.getSql();
			log.debug("MybatisPlusPagePluginInterceptor SQL:{}", totalSQL);
			sqlInfo.setSql(totalSQL);
			this.queryTotal(overflow, sqlInfo.getSql(), mappedStatement, boundSql, page, connection);
			if (page.getTotal() <= 0) {
				return null;
			}
		}

		String buildSql = concatOrderBy(originalSql, page);
		DialectModel model = DialectFactory.buildPaginationSql(page, buildSql, dbType, dialectClazz);
		Configuration configuration = mappedStatement.getConfiguration();
		List<ParameterMapping> mappings = new ArrayList<>(boundSql.getParameterMappings());
		Map<String, Object> additionalParameters = (Map<String, Object>) metaObject
				.getValue("delegate.boundSql.additionalParameters");
		model.consumers(mappings, configuration, additionalParameters);
		sql = model.getDialectSql();
		metaObject.setValue("delegate.boundSql.sql", sql);
		metaObject.setValue("delegate.boundSql.parameterMappings", mappings);
		long startTime = System.currentTimeMillis();
		try {
			return invocation.proceed();
		} finally {
			long endTime = System.currentTimeMillis();
			long sqlCost = endTime - startTime;
			log.debug("\r\n mybatis execute SQL:*****\r\n{}\r\n*****\r\n\r\nexecute time-consuming:{}ms", sql, sqlCost);
		}
	}

	/**
	 * 查询总记录条数
	 *
	 * @param sql             count sql
	 * @param mappedStatement MappedStatement
	 * @param boundSql        BoundSql
	 * @param page            IPage
	 * @param connection      Connection
	 */
	protected void queryTotal(boolean overflowCurrent, String sql, MappedStatement mappedStatement, BoundSql boundSql,
			IPage<?> page, Connection connection) {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement,
					boundSql.getParameterObject(), boundSql);
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
				page.setCurrent(1);
			}
		} catch (Exception e) {
			throw ExceptionUtils.mpe("Error: Method queryTotal execution error of sql : \n %s \n", e, sql);
		}
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties prop) {
		String dialectType = prop.getProperty("dialectType");
		String dialectClazz = prop.getProperty("dialectClazz");
		if (StringUtils.isNotEmpty(dialectType)) {
			this.dialectType = dialectType;
		}
		if (StringUtils.isNotEmpty(dialectClazz)) {
			this.dialectClazz = dialectClazz;
		}
	}

	/** 
	* @see AbstractSqlParserHandler#equals(Object)
	* @Function: MybatisPlusPagePluginInterceptor.java
	* @Description: 该函数的功能描述,解决sonar扫描
	* @param o
	* @return
	*/
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	/**
	* @see AbstractSqlParserHandler#hashCode()
	* @Function: MybatisPlusPagePluginInterceptor.java
	* @Description: 该函数的功能描述,解决sonar扫描
	* @return
	*/
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
