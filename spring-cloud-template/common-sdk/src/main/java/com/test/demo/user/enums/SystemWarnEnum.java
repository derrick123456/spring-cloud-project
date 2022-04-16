package com.test.demo.user.enums;

/**
 * <p>文件名称: SystemWarnEnum.java </p>
 * <p>类型描述: [系统异常状态码] </p>
 */
@Deprecated
public enum SystemWarnEnum {

    SYS001("1001", ""), SYS005("1005", "数据库连接异常"), TENANT("1002", "企业标识不存在,请先注册企业!"),
    REGISTER_IP("1006", "实例IP与实际IP不符!"), REGISTER_SERVERNAME_SAME("1007", "非生产环境不允许相同服务名称注册!"),
    REGISTER_NAME_RULE("1008", "服务名称不符合注册规则!"),NULL("1009", "空指针异常!");

    private String code;

    private String description;

    private SystemWarnEnum(String code, String description) {
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
