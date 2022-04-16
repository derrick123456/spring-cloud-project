package com.test.demo.user.enums;

/**
 * <p>文件名称: UniqueWarnEnum.java </p>
 * <p>类型描述: [409, "UNIQUE WARN"] </p>
 */
@Deprecated
public enum UniqueWarnEnum {

    USDERID_WARN("1001", "用户账号已存在"),
    SYSCODE_WARN("1002", "编码或编号已存在"),
    SYSNAME_WARN("1003", "名称已存在"),
    SYSNAMECODE_WARN("1004", "名称或编码已存在"),
    SYSCODEVALUE_WARN("1005", "编码与实际值已存在"),
    SYSCODENAME_WARN("1006", "编号或名称已存在"),
    UNIQUE_USDERID_WARN("1007", "导入失败,第{0}行用户账号已存在"),
    UNIQUE_EMPLOYEE_WARN("1008", "导入失败,第{0}行员工编号已存在"),
    UNIQUE_UNIT_WARN("1009", "导入失败,第{0}行机构编号已存在"),
    NAME_WARN("1010", "导入失败,第{0}行机构与数据库中同一父机构下机构同名"),
    UNIQUE_NAME_WARN("1011", "导入失败,第{0}行机构与第{1}行机构同名"),
    UNIQUE_JOB_WARN("1012", "导入失败,第{0}行岗位已存在");

    private String code;

    private String description;

    private UniqueWarnEnum(String code, String description) {
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
