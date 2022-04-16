package com.test.demo.sys.model.enums;

/**
 * 自定义消息枚举
 * @author wgg
 * @date 2021-04-05
 */
public enum MessageEnum {

    SYS001("1001", ""),
    PARAM("1002", ""),
    PARAM_ERROR("1003", "授权码失效"),
    TEST_CODE("1004", "测试码"),
    INVALID("401", "授权码失效");
    private String code;

    private String description;

    private MessageEnum(String code, String description) {
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
