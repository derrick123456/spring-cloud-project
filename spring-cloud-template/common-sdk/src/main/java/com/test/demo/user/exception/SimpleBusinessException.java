package com.test.demo.user.exception;


import com.test.demo.user.model.CommonRsp;

import java.text.MessageFormat;

/**
 * <p>文件名称: SimpleBusinessException.java </p>
 * <p>类型描述: [简单异常处理,不会记录日志,没有经过统一异常配置] </p>
 */
public class SimpleBusinessException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4876823388883069092L;

    private CommonRsp<?> errorInfo;

    public SimpleBusinessException() {}

    public SimpleBusinessException(CommonRsp<?> errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * @return the errorInfo
     */
    public CommonRsp<?> getErrorInfo() {
        return errorInfo;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}[{1}]", this.errorInfo.getCode(), this.errorInfo.getDescription());
    }

}
