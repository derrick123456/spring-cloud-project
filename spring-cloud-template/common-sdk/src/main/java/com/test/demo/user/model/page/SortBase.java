package com.test.demo.user.model.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wgg
 * @date 2021/04/05
 */
@Data
public class SortBase implements Serializable{

	private static final long serialVersionUID = -2417111182324482436L;

	@ApiModelProperty(value = "排序字段,根据返回的字段名称传入需要排序的字段,如:用户账号返回字段对应的是userid,那么需要用用户账号排序则传入userid,导出时如果需要导出数据与展示数据顺序一致,需要传入当前排序规则", required = false)
	private String sortName;

	@ApiModelProperty(value = "排序方式,0:升序(数字由低到高,字母从a到z) 1:降序,且支持asc desc ascending descending", required = false)
	private String sortType;
}
