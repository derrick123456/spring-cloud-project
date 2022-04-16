package com.test.demo.user.enums;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: GeneratorEmun.java </p> 
 * <p>类型描述: [创建vo所用路径枚举] </p>
 */
public enum GeneratorEmun {
    DATA("@Data", "lombok.Data"),
    APIMODEL("@ApiModel", "io.swagger.annotations.ApiModel"),
    APIMODELPEOPERTY("@ApiModelProperty", "io.swagger.annotations.ApiModelProperty"),
	TABLEKEY("@TableKey","com.idea.platform.common.annotation.TableKey"),
	SUPPERMAPPER("BaseMapper","com.idea.platform.common.mapper.BaseMapper"),
	VOPATH("","com.idea.platform.sys.vo.");
	
    private String name;

    private String clazz;

    GeneratorEmun(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }
    
}