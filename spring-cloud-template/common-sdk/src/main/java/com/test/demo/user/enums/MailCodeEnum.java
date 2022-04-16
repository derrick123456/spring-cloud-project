package com.test.demo.user.enums;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: MailCodeEnum.java </p> 
 * <p>类型描述: [邮件] </p>
 */
@Deprecated
public enum MailCodeEnum {

	MAIL_SUCCESS("1001", "邮件发送成功"), MAIL_FAILED("1002", "邮件发送失败"), MAIL_NOTICE("1003", "邮件通知"), PASSWORD_MESSAGE(
			"1004", "您好,您的用户密码即将过期,请及时修改密码!"
	);

	private String code;

	private String description;

	private MailCodeEnum(String code, String description) {
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
}
