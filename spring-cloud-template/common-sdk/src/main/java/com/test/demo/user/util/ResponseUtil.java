package com.test.demo.user.util;

import com.test.demo.user.enums.MessageEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@ToString
public class ResponseUtil<T> {

    @ApiModelProperty(value = "状态码,S0014011001,其中S001:服务编码,401:错误级别代码参考httpstatus,1001:详细错误代码", required = false)
    private String code;

    @ApiModelProperty(value = "状态描述", required = false)
    private String description;

    @ApiModelProperty(value = "业务数据", required = false)
    private T data;

    public ResponseUtil() {

    }

    private ResponseUtil(HttpStatus status, String errorCode, String errorMsg) {
        this.code = YmlResourceUtil.readKey("module-code") + status + errorCode;
        this.description = errorMsg;
    }

    private ResponseUtil(HttpStatus status, String errorCode, String errorMsg, T data) {
        this.code = YmlResourceUtil.readKey("module-code") + status + errorCode;
        this.description = errorMsg;
        this.data = data;
    }

    public static ResponseUtil<?> errorSys401(MessageEnum warnEnum) {

        return new ResponseUtil<>(HttpStatus.UNAUTHORIZED, warnEnum.getCode(), warnEnum.getDescription());
    }

}
