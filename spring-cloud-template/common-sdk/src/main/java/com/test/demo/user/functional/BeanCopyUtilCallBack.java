/**   
* 
* @Description: 函数式接口
* @Package: com.idea.platform.common.functional 
*/
package com.test.demo.user.functional;

/**   
* Copyright: Copyright (c) 2020 
* 
* @ClassName: BeanCopyUtilCallBack.java
* @Description: 函数式接口,用于beanutils
*/
@FunctionalInterface
public interface BeanCopyUtilCallBack<S, T> {

    /**
     * 定义默认回调方法
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
