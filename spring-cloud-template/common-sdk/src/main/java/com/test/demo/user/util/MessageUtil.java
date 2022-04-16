package com.test.demo.user.util;

import com.test.demo.user.exception.ExceptionConfig;
import com.test.demo.user.model.CommonRsp;
import com.test.demo.user.valid.CommonValidAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * 异常消息处理用,仅限common使用
 * @author wgg
 * @date 2021/04/05
 */
@Slf4j
public class MessageUtil {

    /**
     * 返回400参数校验消息体,统一消息处理,仅供CommonValidAspect下用
     * @param msg
     * @return
     */
    public static CommonRsp<?> validMsg(String msg) {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().equals(CommonValidAspect.class.getName())) {
            return CommonRsp.errorMsg(HttpStatus.BAD_REQUEST, Message.PARAMETER_FORMAT_ERROR.getCode(), msg);
        } else {
            log.error("The class function is for use only under CommonValidAspect.class");
            return null;
        }
    }





    /**
     * 返回400参数格式化错误
     * @return
     */
    public static CommonRsp<?> error400() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().startsWith(ExceptionConfig.class.getName())) {
            return errorMsg(HttpStatus.BAD_REQUEST, Message.PARAMETER_FORMAT_ERROR);
        } else {
            log.error("The class function is for use only under ExceptionConfig.class");
            return null;
        }
    }
    
    public static CommonRsp<?> errorFileIndexOfSize() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().startsWith(ExceptionConfig.class.getName())) {
            return errorMsg(HttpStatus.BAD_REQUEST, Message.PARAMETER_FILE_MAX_SIZE, EnvUtil.getKey("spring.servlet.multipart.max-file-size", "50M"));
        } else {
            log.error("The class function is for use only under ExceptionConfig.class");
            return null;
        }
    }
    
    public static CommonRsp<?> errorFile() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().startsWith(ExceptionConfig.class.getName())) {
            return errorMsg(HttpStatus.INTERNAL_SERVER_ERROR, Message.FILE_ERROR);
        } else {
            log.error("The class function is for use only under ExceptionConfig.class");
            return null;
        }
    }
    

    /**
     * 返回404资源不存在
     * @return
     */
    public static CommonRsp<?> error404() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().startsWith(ExceptionConfig.class.getName())) {
            return errorMsg(HttpStatus.NOT_FOUND, Message.NOT_FOUND_ERROR);
        } else {
            log.error("The class function is for use only under ExceptionConfig.class");
            return null;
        }
    }

    /**
     * 返回405资源不存在
     * @return
     */
    public static CommonRsp<?> error405() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().startsWith(ExceptionConfig.class.getName())) {
            return errorMsg(HttpStatus.METHOD_NOT_ALLOWED, Message.METHOD_NOT_ALLOWED_ERROR);
        } else {
            log.error("The class function is for use only under ExceptionConfig.class");
            return null;
        }
    }

    /**
     * 返回415所需消息体
     * @return
     */
    public static CommonRsp<?> error415() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().startsWith(ExceptionConfig.class.getName())) {
            return errorMsg(HttpStatus.UNSUPPORTED_MEDIA_TYPE, Message.UNSUPPORTED_MEDIA_TYPE_ERROR);
        } else {
            log.error("The class function is for use only under ExceptionConfig.class");
            return null;
        }
    }

    /**
     * 内部错误
     * @return
     */
    public static CommonRsp<?> error500() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().startsWith(ExceptionConfig.class.getName())) {
            return errorMsg(HttpStatus.INTERNAL_SERVER_ERROR, Message.SYS_ERROR);
        } else {
            log.error("The class function is for use only under ExceptionConfig.class");
            return null;
        }
    }
    
    /**
     * 获取401异常消息
     * @param message
     * @return
     */
//    public static CommonRsp<?> unauthorized() {
//        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
//        if (stes[2].getClassName().startsWith(ForbiddenFilter.class.getName())) {
//            return errorMsg(HttpStatus.UNAUTHORIZED, Message.UNAUTHORIZED);
//        } else {
//            log.error("The class function is for use only under ForbiddenFilter.class");
//            return null;
//        }
//    }

    /**
     * 获取403异常消息
     * @param message
     * @return
     */
//    public static CommonRsp<?> forbidden() {
//        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
//        if (stes[2].getClassName().startsWith(ForbiddenFilter.class.getName())) {
//            return errorMsg(HttpStatus.FORBIDDEN, Message.FORBIDDEN);
//        } else {
//            log.error("The class function is for use only under ForbiddenFilter.class");
//            return null;
//        }
        //return errorMsg(HttpStatus.FORBIDDEN, Message.FORBIDDEN);
//    }

    private static CommonRsp<?> errorMsg(HttpStatus status, Message message) {
        return CommonRsp.errorMsg(status, message.getCode(), message.getDescription());
    }
    
    private static CommonRsp<?> errorMsg(HttpStatus status, Message message, String maxSize) {
        return CommonRsp.errorMsg(status, message.getCode(), message.getDescription().replace("0", maxSize));
    }

    /**
     * 返回400参数校验消息体,统一消息处理,仅供CommonValidAspect下用
     * @param msg
     * @return
     */
    public static CommonRsp<?> validMsgEcception(String msg) {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        if (stes[2].getClassName().equals(ExceptionConfig.class.getName())||stes[2].getClassName().equals(ValidUtil.class.getName())) {
            return CommonRsp.errorMsg(HttpStatus.BAD_REQUEST, Message.PARAMETER_FORMAT_ERROR.getCode(), msg);
        } else {
            log.error("The class function is for use only under CommonValidAspect.class");
            return null;
        }
    }

    /**
     * 消息体，仅供common包下messageUtil用
     */
    private enum Message {
        PARAMETER_FORMAT_ERROR("9998", "参数格式错误"), // 400错误,如json格式,发送参数为非json格式
        PARAMETER_FILE_MAX_SIZE("9994", "文件上传失败,文件大小超出最大允许值[0]"),
        FILE_ERROR("9993", "文件上传失败,请稍后再试!"),
        VALID_WARN("9998", "BindingResult以返回错误为准"), // 400参数校验，自动校验，详见valid包下
        // jsjs
        METHOD_NOT_ALLOWED_ERROR("1001", "错误的method-type"), // 405错误,仅一种
        SYS_ERROR("1001", "系统繁忙,请稍后再试！"), // 500
        NOT_FOUND_ERROR("1001", "访问资源不存在"), // 404错误,只有一种提示消息
        UNSUPPORTED_MEDIA_TYPE_ERROR("1001", "不支持的媒体类型,Content-Type"), // 415错误,只有一种提示消息
        ANNOTATION_ERROR("9992", "不允许存在相同的注解再同一个类中(@TableKey,@LogicDelete)"),
        TABLEKEY_ERROR("9991", "实体没有标明逻辑删除字段或主键字段,或参数为空"),
        LOGICKEY_ERROR("9990", "实体没有标明逻辑删除字段或主键字段,或参数为空"),
        
        UNAUTHORIZED("1001", "授权码失效"),
        FORBIDDEN("1001", "没有权限访问该资源");

        private String code;

        private String description;

        private Message(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
    
}
