package com.test.demo.user.enums;

/**
 * <p>文件名称: SuccessEnum.java </p>
 * <p>类型描述: [成功状态码] </p>
 */
public enum SuccessEnum {

    SUCCESS("1001", "success"),
    LOGIN_SUCCESS("1002", "用户登陆成功");

    private String code;

    private String description;

    private SuccessEnum(String code, String description) {
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
