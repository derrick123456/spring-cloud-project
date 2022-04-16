package com.test.demo.user.model;


import com.test.demo.user.enums.MessageEnum;
import com.test.demo.user.exception.BusinessException;

import java.text.MessageFormat;

/**
  * 业务自定义消息处理
 * @author wgg
 * @date 2021-04-05
 */
public class MessageUtil {

    /**
     * 返回409表单错误信息,表示唯一性,错误信息请抛出异常,throw new BusinessException(MessageUtil.msg409);
     * @param message
     * @return
     */
    public static void warn400(MessageEnum message) {
        throw new BusinessException(CommonRsp.errorSys400(message.getCode(), message.getDescription()));
    }
    
    /**
     * <p>自定义400错误信息,用于提示错误出现所在具体行号</p>
     * @param message
     * @return
     */
    public static void warn400(MessageEnum message, Integer row) {
        throw new BusinessException(
            CommonRsp.errorSys400(message.getCode(), MessageFormat.format(message.getDescription(), row)));
    }

    /**
     * 返回409表单错误信息,表示唯一性,错误信息请抛出异常,throw new BusinessException(MessageUtil.msg409);
     * @return
     */
    public static CommonRsp<?> warn409(MessageEnum message) {
        throw new BusinessException(CommonRsp.errorSys409(message.getCode(), message.getDescription()));
    }

    /**
     * 返回成功消息,带返回数据体
     * @param <T>
     * @param data
     * @return
     */
    public static <T> CommonRsp<T> success(T data) {
        return CommonRsp.successSys(data);
    }

    /**
     * 返回成功消息,不带返回数据体
     * @return
     */
    public static CommonRsp<?> success() {
        return CommonRsp.successSys();
    }
}
