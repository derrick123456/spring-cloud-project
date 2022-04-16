package com.test.demo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>文件名称: BaseVO.java </p>
 * <p>类型描述: [基类,封装一些公共属性] </p>
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class BaseVO implements Serializable {

	private static final long serialVersionUID = 2846162422336422786L;

	/**
	 * <p>字段描述:[租户ID,用于识别数据库,前端由用户登录时获取,默认使用common公共库]</p>
	 * @Fields tenant 
	 */
	// @ApiModelProperty(value = "租户ID", required = false, hidden = true)
	// @JsonInclude(Include.NON_NULL)
	// private String tenant;

	@ApiModelProperty(value = "客户端code:支持webApp,移动应用mobileApp,webApp", required = false, hidden = true)
	@JsonInclude(Include.NON_NULL)
	private String client;

	@ApiModelProperty(value = "分页的当前页码", required = false, hidden = true)
	@JsonInclude(Include.NON_NULL)
	private Integer pageNum;

	@ApiModelProperty(value = "每页大小", required = false, hidden = true)
	@JsonInclude(Include.NON_NULL)
	private Integer pageSize;

	@ApiModelProperty(value = "统一搜索框", required = false, hidden = true)
	@JsonInclude(Include.NON_NULL)
	private String searchValue;

	@ApiModelProperty(value = "排序字段,传入需要排序的字段", required = false, hidden = true)
	@JsonInclude(Include.NON_NULL)
	private String sortName;

	@ApiModelProperty(value = "排序方式,0:升序(数字由低到高,字母从a到z) 1:降序,且支持asc desc ascending descending", required = false, hidden = true)
	@JsonInclude(Include.NON_NULL)
	private String sortType;
	
	@ApiModelProperty(value = "日期区间查询字段,开始时间", required = false, hidden = true)
	@JsonInclude(Include.NON_NULL)
	private String startTimeValue;
	
	@ApiModelProperty(value = "日期区间查询字段,结束时间", required = false, hidden = true)
	@JsonInclude(Include.NON_NULL)
	private String endTimeValue;
}
