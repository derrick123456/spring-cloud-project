package com.test.demo.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * redis配置
 * </p>
 *
 * @author wgg
 * @since 2022-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RedisConfig对象", description="redis配置")
public class RedisConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id",name="id",dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "redis服务唯一标识",name="code",dataType="String")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "ip地址",name="host",dataType="String")
    @TableField("host")
    private String host;

    @ApiModelProperty(value = "端口号",name="port",dataType="String")
    @TableField("port")
    private String port;

    @ApiModelProperty(value = "账号",name="account",dataType="String")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "密码",name="password",dataType="String")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "库位",name="dataBase",dataType="Integer")
    @TableField("data_base")
    private Integer dataBase;

    @ApiModelProperty(value = "最大活跃数",name="maxActive",dataType="Integer")
    @TableField("max_active")
    private Integer maxActive;

    @ApiModelProperty(value = "最大闲置数",name="maxIdle",dataType="Integer")
    @TableField("maxIdle")
    private Integer maxIdle;

    @ApiModelProperty(value = "超时等待时间",name="timeout",dataType="Long")
    @TableField("timeout")
    private Long timeout;

    @ApiModelProperty(value = "数据删除标识0无效1有效",name="enabledFlag",dataType="Integer")
    @TableField("enabled_flag")
    private Integer enabledFlag;

    @ApiModelProperty(value = "创建人",name="createdBy",dataType="String")
    @TableField("created_by")
    private String createdBy;

    @ApiModelProperty(value = "创建时间",name="creationDate",dataType="LocalDateTime")
    @TableField("creation_date")
    private LocalDateTime creationDate;

    @ApiModelProperty(value = "更新人",name="updatedBy",dataType="String")
    @TableField("updated_by")
    private String updatedBy;

    @ApiModelProperty(value = "更新时间",name="updationDate",dataType="LocalDateTime")
    @TableField("updation_date")
    private LocalDateTime updationDate;


}
