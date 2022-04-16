package com.test.demo.user.enums;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: LevelCodeEnum.java </p> 
 * <p>类型描述: [响应级别代码,如401权限错误,400参数错误,409重复冲突,500服务器错误] </p>
 */
@Deprecated
public enum LevelCodeEnum {

	AUTHORIZE_WARN("401", "AUTHORIZE WARN"), PARAMETER_WARN(
			"400", "PARAMETER WARN"
	), UNIQUE_WARN("409", "UNIQUE WARN"), RESOURCE_WARN(
			"404", "RESOURCE WARN"
	), NOTMETHOD_WARN("405", "RESOURCE WARN"), CONTENTTYPE_WARN(
			"415", "不支持媒体类型"
	), SYSTEM_ERROR("500", "SYSTEM ERROR"), SUCCESS(
			"200", "SUCCESS"
	), FORBIDDEN("403", "FORBIDDEN");

	private String code;

	private String description;

	private LevelCodeEnum(String code, String description) {
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
