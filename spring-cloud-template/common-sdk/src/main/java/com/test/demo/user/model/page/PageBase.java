package com.test.demo.user.model.page;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wgg
 * @date 2021/04/05
 */
@Data
public class PageBase extends SearchBase  implements Serializable {

	private static final long serialVersionUID = 3735143568034365201L;

	@ApiModelProperty(value = "分页的当前页码", required = false)
	@TableField(exist = false)
	private Integer pageNum;

	@ApiModelProperty(value = "每页大小", required = false)
	@TableField(exist = false)
	private Integer pageSize;

}
