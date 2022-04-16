package com.test.demo.user.enums;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: ResourceWarnEnum.java </p> 
 * <p>类型描述: [400 参数错误细分Resource_WARN] </p>
 */
@Deprecated
public enum ResourceWarnEnum {

	SYS001("1001", "资源不存在");

	private String code;

	private String description;

	private ResourceWarnEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
