package com.test.demo.user.service.model.dto;

import com.test.demo.user.model.page.SearchBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestDto extends SearchBase implements Serializable {

    @ApiModelProperty(value = "主键id", name = "id", dataType = "String")
    private String id;

    @ApiModelProperty(value = "程序包名称", name = "moduleName", dataType = "String")
    private String moduleName;

    @ApiModelProperty(value = "服务code", name = "moduleCode", dataType = "String")
    private String moduleCode;

    @ApiModelProperty(value = "模块描述", name = "descr", dataType = "String")
    private String descr;

    @ApiModelProperty(value = "模块类型（1后台服务，2前端web）", name = "moduleType", dataType = "Integer")
    private Integer moduleType;

    @ApiModelProperty(value = "发布状态（1已发布，2已下架）", name = "valid", dataType = "Integer")
    private Integer valid;
}
