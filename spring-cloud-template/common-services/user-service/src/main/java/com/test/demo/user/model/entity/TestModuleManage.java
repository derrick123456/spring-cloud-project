package com.test.demo.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.test.demo.user.model.page.PageBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 程序包模块表
 * </p>
 *
 * @author wgg
 * @since 2021-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "TestModuleManage对象", description = "程序包模块表")
public class TestModuleManage extends PageBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id", name = "id", dataType = "String")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "程序包名称", name = "moduleName", dataType = "String")
    @TableField("module_name")
    @NotBlank(message = "模块名称不能为空")
    @Length(max = 20, message = "编码长度最大为20")
    private String moduleName;

    @ApiModelProperty(value = "服务code", name = "moduleCode", dataType = "String")
    @TableField("module_code")
    @NotBlank(message = "模块编码不能为空")
    @Length(max = 20, message = "编码长度最大为20")
    private String moduleCode;

    @ApiModelProperty(value = "模块描述", name = "descr", dataType = "String")
    @TableField("descr")
    @Length(max = 200, message = "编码长度最大为200")
    private String descr;

    @ApiModelProperty(value = "模块类型（1后台服务，2前端web）", name = "moduleType", dataType = "Integer")
    @TableField("module_type")
    private Integer moduleType;

    @ApiModelProperty(value = "发布状态（1已发布，2已下架）", name = "valid", dataType = "Integer")
    @TableField("valid")
    private Integer valid;

    @ApiModelProperty(value = "应用程序发布者创建者", name = "creatorId", dataType = "String")
    @TableField("creator_id")
    private String creatorId;

    @ApiModelProperty(value = "创建时间", name = "createTime", dataType = "LocalDateTime")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者", name = "editorId", dataType = "String")
    @TableField("editor_id")
    private String editorId;

    @ApiModelProperty(value = "修改时间", name = "editorTime", dataType = "LocalDateTime")
    @TableField("editor_time")
    private LocalDateTime editorTime;

}
