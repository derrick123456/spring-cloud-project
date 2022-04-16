package com.test.demo.user.model.page;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonIgnoreProperties(value = { "handler" })
@Data
public class SearchBase {

    @ApiModelProperty(value = "关键字", name = "searchValue", dataType = "String", required = false, hidden = false)
    @TableField(exist = false)
    @JsonIgnore 
    private String searchValue = "";
}
