package com.test.demo.user.enums;

/**
 * 
 * @Description: 400 参数错误细分PARAMETER_WARN
 * <p>项目名称: common-base </p>
 * <p>文件名称: ParameterWarnEnum.java </p> 
 * <p>类型描述: [400 参数错误细分PARAMETER_WARN] </p>
 */
@Deprecated
public enum ParameterWarnEnum {

	SYS001("1001", ""), SYSCODE001("1002", "sysCode delete error"), PARAM_NULL(
			"1003", "sysCode delete error"
	), PARAM_ERROR("1004", "请求参数解析错误"), INVALID("1005", "授权码失效"), USER("1006", "用户名或密码无效"), TOKEN_CODE(
			"1007", "验证码无效"
	), SYS_ERROR("1008", "系统错误,请重试"), CLIENT("1009", "无该客户端授权"), NOT_EXIST("1010", "不存在该记录"), CHILD_EXIT(
			"1011", "存在子列表"
	), USER_TEST("1012", "用户密码错误，请输入验证码"), USER_RETEST("1013", "操作过于频繁，请输入验证码"), CRON_ERROR(
			"1014", "无效cron表达式"
	), NUMBER_ERROR("1015", "一次性导入数量过多"), EMPLOYEE_EXIST("1016", "导入失败,机构下已存在员工不能覆盖导入"), RECORD_NULL(
			"1017", "导入失败,第{0}行机构不存在"
	), UNIQUE_ERROR("1018", "导入失败,第{0}行不能唯一确定机构,建议直接使用机构编号"), POSITION_NULL("1019", "导入失败,第{0}行职位不存在"), JOB_NULL(
			"1020", "导入失败,第{0}行岗位不存在"
	), LEADER_NULL("1021", "导入失败,第{0}行上级领导不存在"), NO_DATA("1022", "导入失败,数据不存在"), PUNIT_NULL(
			"1023", "导入失败,第{0}行父机构不存在"
	), PJOB_NULL("1024", "导入失败,第{0}行上级岗位不存在"), NO_EMPLOYEE("1025", "导入失败,第{0}行员工不存在"), IS_EMPLOYEE(
			"1026", "选择职员后必须选定对应员工"
	), USER_EMPLOYEE("1027", "此员工已添加用户"), EMPLOYEE_USER("1028", "导入失败,第{0}行员工已添加过用户"
	),PASSWORD_WRONG("1029", "原密码输入错误"), PASSWORD_SAME("1030", "新密码与原密码相同"),PASSWORD_DIFFERENCE("1031", "两次输入的新密码不一致"),EMPLOYEE_MAINFLAG("1030","主岗所属部门必须与所属机构一致"),
UNIT_NAME_EXIT("1031","同一机构名称已存在"), UNIT_EMPLOYEE_EXIT("1032","不可删除有员工的机构"),UNIT_CIRCLE_DRAW("1033","不可循环引用机构"
		), NO_SECURITY("1034", "未添加安全策略配置,请先添加后在执行当前操作"),TOKENFAIL("1035","token失效，请重新登录");

	private String code;

	private String description;

	private ParameterWarnEnum(String code, String description) {
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
