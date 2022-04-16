/**
 * @Description :
 * @author :
 * @date :2019年9月11日
 */
package com.test.demo.user.enums;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: SocketCodeEnum.java </p> 
 * <p>类型描述: [描述] </p>
 */
@Deprecated
public enum SocketCodeEnum {

	REFRESH_EVENT("1001", "刷新消息"), PASSWORD_NOTICE("1002", "修改密码"), UPDATE_PASSWORD(
			"1003", "您的密码即将过期,请及时修改密码"
	), SUCCESS("1004", "消息发送成功"), FAILED("1005", "消息发送失败");

	private String code;

	private String description;

	private SocketCodeEnum(String code, String description) {
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
