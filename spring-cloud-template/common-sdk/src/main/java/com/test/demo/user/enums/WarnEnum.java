package com.test.demo.user.enums;

/**
 * @description : 提示信息s
 * @author wgg
 *
 */
public enum WarnEnum {
	
	CATEGORY_VALUE_FOR_ADD_NAME_EXISTS_WARN("2015","新增失败，类别名称已存在"),
	CATEGORY_VALUE_FOR_ADD_NO_EXISTS_WARN("2016","新增失败，类别编号已存在"),
	CATEGORY_VALUE_FOR_UPDATE_NAME_EXISTS_WARN("2017","修改失败，类别名称已存在"),
	CATEGORY_VALUE_FOR_UPDATE_NO_EXISTS_WARN("2018","修改失败，类别编号已存在"),
	CATEGORY_VALUE_FOR_UPDATE_ID_EXISTS_WARN("2019","修改失败，类别值不存在"),
	CATEGORY_VALUE_FOR_DELETE_ID_EXISTS_WARN("2020","删除失败，类别值不存在");

	private String code;

	private String description;

	private WarnEnum(String code, String description) {
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
