package com.test.demo.user.config.swagger;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.test.demo.user.util.EnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>文件名称: SwaggerConfig.java </p>
 * <p>类型描述: [swagger公共配置项,默认开启swagger文档,当produce环境配置时强制关闭swagger文档,可以通过swagger.enable=false配置来手工关闭] </p>
 */
@Configuration
@Slf4j
public class SwaggerConf {
	
	public static boolean getSwaggerEnable() {
		return EnvUtil.isProduce("produce") ? EnvUtil.getKey("swagger.enable", Boolean.class, false)
				: EnvUtil.getKey("swagger.enable", Boolean.class, true);
	}

	@Bean
	public Docket createRestApi() {
		List<ResponseMessage> responseMessageList = new ArrayList<>();
		responseMessageList
				.add(new ResponseMessageBuilder().code(404).message("找不到资源-确保服务能正常访问以及服务本身是否存在该API资源").build());
		responseMessageList.add(new ResponseMessageBuilder().code(400).message("参数错误--校验错误").build());
		responseMessageList.add(new ResponseMessageBuilder().code(401).message("没有认证-请传入API认证码(TOKEN)").build());
		responseMessageList.add(new ResponseMessageBuilder().code(500)
				.message("服务器内部错误--G001500等服务正常访问后再试,其他500为服务本身发生异常,请自行查看日志排查").build());
		responseMessageList.add(new ResponseMessageBuilder().code(403).message("没有访问权限-请配置接口权限").build());
		responseMessageList.add(new ResponseMessageBuilder().code(200).message("请求成功").build());

		boolean enable=getSwaggerEnable();
		log.info("------------------Register swagger2 Docket config------------------");
		log.info("-*-*-*-*-*-*-*-*-*-*-*-*-*-swagger.enable:{}-*-*-*-*-*-*-*-*-*-*-*-*-*-", enable);
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(enable).select()
				.apis(basePackage("com.test.**.controller,com.test.**.controller.**")) // 扫描文档注解的包路径
				.paths(PathSelectors.any()).build()
				.securitySchemes(Lists.newArrayList(new ApiKey("apikey", "Authorization", "header")))
				.globalResponseMessage(RequestMethod.GET, responseMessageList)
				.globalResponseMessage(RequestMethod.DELETE, responseMessageList)
				.globalResponseMessage(RequestMethod.HEAD, responseMessageList)
				.globalResponseMessage(RequestMethod.PATCH, responseMessageList)
				.globalResponseMessage(RequestMethod.POST, responseMessageList)
				.globalResponseMessage(RequestMethod.PUT, responseMessageList)
				.globalResponseMessage(RequestMethod.TRACE, responseMessageList)
				.globalOperationParameters(Arrays.asList(new ParameterBuilder().name("version")
						.description("API版本号,版本号不存在则走最高有效版本V,不区分大小写").modelRef(new ModelRef("string"))
						.parameterType("path").defaultValue("v1").required(true).build()));
	}

	/**
	 * <p>功能描述: [指定了页面显示的信息，标题、描述] </p>
	 * @Title apiInfo
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {
		String appName = EnvUtil.getKey("spring.application.name", String.class, "undefinition");
		return new ApiInfoBuilder()
				.title(appName + "服务---在线文档 &nbsp; &nbsp;<a href='../sys/api-doc.html' target='_self'>返回查看所有服务文档</a>")
				.description("1, 通过网关测试接口请点击右上角 Authorization填入token,不通过网关访问无需该步骤").version("v1.0").build();
	}

	/**
	 * Predicate that matches RequestHandler with given base package name for the
	 * class of the handler method. This predicate includes all request handlers
	 * matching the provided basePackage
	 * <p>功能描述:  </p>
	 * @Title basePackage
	 * @param basePackage
	 * @return Predicate<RequestHandler>
	 */
	public static Predicate<RequestHandler> basePackage(final String basePackage) {
		return new Predicate<RequestHandler>() {
			@Override
			public boolean apply(RequestHandler input) {
				return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
			}
		};
	}

	/**
	 * <p>功能描述: [ 处理包路径配置规则,通配符,多包规则如com.abc.**.controller,com.def**.controller] </p>
	 * @Title handlerPackage
	 * @param basePackage
	 * @return Function<Class<?>,Boolean>
	 */
	private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {

		return new Function<Class<?>, Boolean>() {
			@Override
			public Boolean apply(Class<?> input) {
				Boolean match = false;
				AntPathMatcher pathMatcher = new AntPathMatcher();
				for (String strPackage : basePackage.split(",")) {// 改进支持通配符配置模式
					match = pathMatcher.match(strPackage, input.getPackage().getName());
					if (match) {
						return match;
					}
				}
				return match;
			}
		};
	}

	/**
	 * @Title declaringClass
	 * @param input
	 * @return Optional<? extends Class<?>>
	 */
	@SuppressWarnings("deprecation")
	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.fromNullable(input.declaringClass());
	}

}
