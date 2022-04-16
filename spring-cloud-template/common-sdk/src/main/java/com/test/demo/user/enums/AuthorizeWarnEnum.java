package com.test.demo.user.enums;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: AuthorizeWarnEnum.java </p> 
 * <p>类型描述: [401错误细分AUTHORIZE_WARN] </p>
 */
@Deprecated
public enum AuthorizeWarnEnum {

    INVALID("1001", "授权码失效"),
    USER("1002", "用户名或密码无效"),
    TOKEN_CODE("1003", "验证码无效"),
    SYS_ERROR("1004", "系统错误"),
    CLIENT("1005", "无该客户端授权");

    private String code;

    private String description;

    private AuthorizeWarnEnum(String code, String description) {
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
