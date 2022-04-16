/**
 * <p>controller层公共校验处理 <BR/>
 *  根据自身业务所需将校验包注解作用于DTO或者VO下字段上,参考:<BR/>
 *      -----javax.validation.constraints <BR/>
 *      -----org.hibernate.validator.constraints<BR/>
 * controller业务接口方法示例:<BR/>
 *      -----public CommonRsp<?> postClient(@RequestBody @Validated({CheckSequence.class}) ClientPostDto abc, BindingResult bindingResult)
 *      接口会自行返回校验信息
 * </p>
 *
 */
package com.test.demo.user.valid;
