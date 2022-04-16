package com.test.demo.user.enums;

/**
 * <p>文件名称: Warn405Enum.java </p>
 * <p>类型描述: [405错误码] </p>
 * @author wgg
 * @version V1.0
 */
@Deprecated
public enum Warn405Enum {

	WARN("1001", "不支持的method-type");

	private String code;

	private String description;

	private Warn405Enum(String code, String description) {
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
