package com.test.demo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.demo.user.enums.*;
import com.test.demo.user.util.EnvUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


/**
 * <p>文件名称: CommonRsp.java </p>
 * <p>类型描述: [ 公共响应参数,响应规则"status:服务节点代码四位字母加数字+错误级别代码三位+细节错误代码4位 如S0014000001
 *  其中标识S001系统管理模块,400表示参数错误0001表示用户名为空",不提供公共处理仅400,409公共静态方法,消息由业务自行处理] </p>
 */
@SuppressWarnings("deprecation")
@Data
@ToString
@JsonIgnoreProperties(value = {"handler"})
public class CommonRsp<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码,S0014011001,其中S001:服务编码,401:错误级别代码参考httpstatus,1001:详细错误代码", required = false)
    private String code;

    @ApiModelProperty(value = "状态描述", required = false)
    private String description;

    @ApiModelProperty(value = "业务数据", required = false)
    private T data;

    private CommonRsp() {}

    private CommonRsp(HttpStatus status, String errorCode, String errorMsg) {
        this.code = EnvUtil.getKey("module-code", String.class, "XXXX") + status.value();
        this.description = errorMsg;
    }

    private CommonRsp(HttpStatus status, String errorCode, String errorMsg, T data) {
        this.code = EnvUtil.getKey("module-code", String.class, "XXXX") + status + errorCode;
        this.description = errorMsg;
        this.data = data;
    }

    /**
     * <p>功能描述: [无数据成功返回消息] </p>
     * @Title successSys
     */
    public static CommonRsp<?> successSys() {
        return new CommonRsp<>(HttpStatus.OK, SuccessEnum.SUCCESS.getCode(), SuccessEnum.SUCCESS.getDescription());
    }

    /**
     * @Title: successSys
     * @Description: 无数据成功返回消息
     * @param data .
     * @return
     * @return CommonRsp    返回类型
     */
    public static <T> CommonRsp<T> successSys(T data) {
        return new CommonRsp<T>(HttpStatus.OK, SuccessEnum.SUCCESS.getCode(), SuccessEnum.SUCCESS.getDescription(),
                data);
    }

    /**
     * @Title: errorDataSys400 资源错误
     * @Description: 已过时,后续不再支持,未来可能被删除
     * @param warnEnum .
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static <T> CommonRsp<T> errorDataSys400(ParameterWarnEnum warnEnum, T data) {
        return new CommonRsp<T>(HttpStatus.BAD_REQUEST, warnEnum.getCode(), warnEnum.getDescription(), data);
    }

    /**
     * @Title: errorSys404 资源错误
     * @Description: 已过时,后续不再支持,未来可能被删除
     * @param warnEnum .
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys404(ResourceWarnEnum warnEnum) {
        return new CommonRsp<>(HttpStatus.NOT_FOUND, warnEnum.getCode(), warnEnum.getDescription());
    }

    /**
     * @Title: errorSys404 资源错误
     * @Description errorSys404 资源错误
     * @param errorCode .如1001
     * @param description .如描述,如某某错误
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys404(String errorCode, String description) {
        return new CommonRsp<>(HttpStatus.NOT_FOUND, errorCode, description);
    }

    /**
     * @Description: 已过时,后续不再支持,未来可能被删除
     * @Description: 鉴权错误
     * @param warnEnum .
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys401(AuthorizeWarnEnum warnEnum) {
        return new CommonRsp<>(HttpStatus.UNAUTHORIZED, warnEnum.getCode(), warnEnum.getDescription());
    }

    /**
     * @Title: errorSys401
     * @Description: 鉴权错误
     * @param errorCode .如1001
     * @param description .如描述,如某某错误
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys401(String errorCode, String description) {
        return new CommonRsp<>(HttpStatus.UNAUTHORIZED, errorCode, description);
    }

    /**
     * @Description: 已过时,后续不再支持,未来可能被删除
     * @return
     */
    @Deprecated
    public static CommonRsp<?> errorSys403() {
        return new CommonRsp<>(HttpStatus.FORBIDDEN, "1001", "没有访问资源权限");
    }

    /**
     * @Description: 已过时,后续不再支持,未来可能被删除 应该由系统异常处理
     * @param errorCode
     * @param description
     * @return
     */
    @Deprecated
    public static CommonRsp<?> errorSys403(String errorCode, String description) {
        return new CommonRsp<>(HttpStatus.FORBIDDEN, errorCode, description);
    }

    /**
     * @Title: errorSys409
     * @Description: 已过时,后续不再支持,未来可能被删除
     * @param warnEnum .
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys409(UniqueWarnEnum warnEnum) {
        return new CommonRsp<>(HttpStatus.CONFLICT, warnEnum.getCode(), warnEnum.getDescription());
    }

    /**
     * @Title: errorSys409
     * @Description: 唯一值错误
     * @param errorCode .如1001
     * @param description .如描述,如某某错误
     * @return CommonRsp    返回类型
     */
    public static CommonRsp<?> errorSys409(String errorCode, String description) {
        return new CommonRsp<>(HttpStatus.CONFLICT, errorCode, description);
    }

    /**
     * <p>功能描述: [描述] </p>
     * @Title errorSys4001003
     * @Description 已过时,后续不再支持,未来可能被删除
     * @param errorMsg .
     */
    @Deprecated
    public static CommonRsp<?> errorSys4001003(String errorMsg) {
        return new CommonRsp<>(HttpStatus.BAD_REQUEST, ParameterWarnEnum.PARAM_NULL.getCode(), errorMsg);
    }

    /**
     * @Title: errorSys400
     * @Description 已过时,后续不再支持,未来可能被删除
     * @param warnEnum .
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys400(ParameterWarnEnum warnEnum) {
        return new CommonRsp<>(HttpStatus.BAD_REQUEST, warnEnum.getCode(), warnEnum.getDescription());
    }

    /**
     * @Title: errorSys400
     * @Description 返回校验信息,警告信息,如S0014001001 用户名不能为空
     * @param errorCode .如1001
     * @param description .如描述,如某某错误
     * @return CommonRsp    返回类型
     */
    public static CommonRsp<?> errorSys400(String errorCode, String description) {
        return new CommonRsp<>(HttpStatus.BAD_REQUEST, errorCode, description);
    }

    /**
     * @Title: errorSys400
     * @Description 已过时,后续不再支持,未来可能被删除
     * @param errorCode .如1001
     * @param description .如描述,如某某错误
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorDataSys400(String errorCode, String description, Object data) {
        return new CommonRsp<>(HttpStatus.BAD_REQUEST, errorCode, description, data);
    }

    /**
     * @Title: errorSys405
     * @Description 已过时,后续不再支持,未来可能被删除 应该由系统异常处理
     * @param warnEnum .
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys405(Warn405Enum warnEnum) {
        return new CommonRsp<>(HttpStatus.METHOD_NOT_ALLOWED, warnEnum.getCode(), warnEnum.getDescription());
    }

    /**
     * @Title: errorSys405
     * @Description 已过时,后续不再支持,未来可能被删除 应该由系统异常处理
     * @param errorCode .如1001
     * @param description .如描述,如某某错误
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys405(String errorCode, String description) {
        return new CommonRsp<>(HttpStatus.METHOD_NOT_ALLOWED, errorCode, description);
    }

    /**
     * @Title: errorSys405
     * @Description: 已过时,后续不再支持,未来可能被删除 应该由系统异常处理
     * @param warnEnum .
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys415(Warn415Enum warnEnum) {
        return new CommonRsp<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE, warnEnum.getCode(), warnEnum.getDescription());
    }

    /**
     * @Title: errorSys415
     * @Description: 已过时,后续不再支持,未来可能被删除 应该由系统异常处理
     * @param errorCode .如1001
     * @param description .如描述,如某某错误
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys415(String errorCode, String description) {
        return new CommonRsp<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE, errorCode, description);
    }

    /**
     * @Title: errorSys500
     * @Description: 已过时,后续不再支持,未来可能被删除 应该由系统异常处理
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys500(SystemWarnEnum warnEnum) {
        return new CommonRsp<>(HttpStatus.INTERNAL_SERVER_ERROR, warnEnum.getCode(), warnEnum.getDescription());
    }

    /**
     * @Title: errorSys500
     * @Description: 系统错误,未知错误级别 已过时,后续不再支持,未来可能被删除,应该由系统异常处理
     * @param errorCode .如1001,四位
     * @param description .如某某错误
     * @return CommonRsp    返回类型
     */
    @Deprecated
    public static CommonRsp<?> errorSys500(String errorCode, String description) {
        return new CommonRsp<>(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, description);
    }

    /**
     * 自定义其他类别消息,如400403,404,500,415,一般项目工程无需调用此方法
     * @param status
     * @param code
     * @param msg
     * @return
     */
    public static CommonRsp<?> errorMsg(HttpStatus status, String code, String msg) {
        return new CommonRsp<>(status, code, msg);
         }
}
