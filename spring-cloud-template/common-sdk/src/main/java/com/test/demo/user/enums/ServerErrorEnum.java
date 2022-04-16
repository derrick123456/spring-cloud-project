package com.test.demo.user.enums;

/**
 * <p>文件名称: ServerErrorEnum.java </p>
 * <p>类型描述: [服务异常状态码] </p>
 */
@Deprecated
public enum ServerErrorEnum {

	SYS001("1001", "");

	private String code;

	private String description;

	private ServerErrorEnum(String code, String description) {
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
