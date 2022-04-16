package com.test.demo.user.valid;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;


/**
 * <p>文件名称: CommonValidAspect.java </p>
 * <p>controller层公共校验处理 <BR/>
 *  根据自身业务所需将校验包注解作用于DTO或者VO下字段上,参考:<BR/>
 *      -----javax.validation.constraints <BR/>
 *      -----org.hibernate.validator.constraints<BR/>
 * controller业务接口方法示例:<BR/>
 *      -----public CommonRsp<?> postClient(@RequestBody @Validated({CheckSequence.class}) ClientPostDto abc, BindingResult bindingResult)
 *      接口会自行返回校验信息
 * </p>
 * <p>类型描述: [统一校验,BindingResult] </p>
 */
@Aspect
@Component
@Order(4)
public class CommonValidAspect {

    @Pointcut("execution(public * com..*.controller..*.*(..))")
    public void commonValid() {}

    @Before("commonValid()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] paramObj = joinPoint.getArgs();
        BindingResult result = null;
        // 当controller方法体内没有参数时,跳出切面类
        if (paramObj == null || paramObj.length <= 0) {
            return;
        }
        for (Object object : paramObj) {
            if (object instanceof BindingResult) {
                result = (BindingResult)object;
                break;
            }
        }
        // 当controller有校验信息时
        if (result != null && result.hasErrors()) {
            StringBuffer validMsg = new StringBuffer();
            // result.getAllErrors().forEach(x -> erorMsg.append(" " + x.getDefaultMessage()));
            validMsg.append(result.getAllErrors().get(0).getDefaultMessage());
//            throw new BusinessException(MessageUtil.validMsg(validMsg.toString()));
        }
    }
}
