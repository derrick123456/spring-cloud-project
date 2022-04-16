package com.test.demo.user.enums;

/**
 * <p>文件名称: ServerCodeEnum.java </p>
 * <p>类型描述: [系统代码服务代码用于同一状态码返回详见commonrsp解释] </p>
 */
@Deprecated
public enum ServerCodeEnum {

	SYSMANAGER("S001", "sysmanager-server"), WORKFLOW(
			"S002", "workflow-server"
	), MANAGE("S003", "manage-server"), GATEWAY("G001", "GATEWAY-error");

	private String code;

	private String description;

	private ServerCodeEnum(String code, String description) {
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
