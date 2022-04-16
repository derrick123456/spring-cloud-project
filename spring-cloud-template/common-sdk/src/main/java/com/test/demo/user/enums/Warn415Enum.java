package com.test.demo.user.enums;

/**
 * <p>文件名称: Warn415Enum.java </p>
 * <p>类型描述: [415错误状态码] </p>
 */
@Deprecated
public enum Warn415Enum {

	WARN("1001", "不支持的媒体类型,Content-Type");

	private String code;

	private String description;

	private Warn415Enum(String code, String description) {
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
