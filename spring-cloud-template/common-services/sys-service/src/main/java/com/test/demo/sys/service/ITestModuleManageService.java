package com.test.demo.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.test.demo.user.model.page.PageResult;
import com.test.demo.sys.model.entity.TestModuleManage;
import com.test.demo.sys.model.dto.TestDto;
import com.test.demo.sys.model.dto.TestModulePageDto;

import java.util.List;

/**
 * <p>
 * 程序包模块表 服务类
 * </p>
 *
 * @author wgg
 * @since 2021-04-05
 */
public interface ITestModuleManageService extends IService<TestModuleManage> {

    /**
    * 
    * @Function: ITestModuleManageService.java
    * @Title: list
    * @Description: 根据条件查询list
    * @param param
    * @return
    * List<TestModuleManage>
    */
    public List<TestModuleManage> list(TestDto param);

    /**
     * 分页列表
     * @param param
     * @return
     */
    public PageResult<TestModuleManage> page(TestModulePageDto param);

    /**
    * @Function: ITestModuleManageService.java
    * @Title: getByIds
    * @Description: 根据多个Id查询多条记录
    * @param ids
    * @return
    * List<TestModuleManage>
     */
    public List<TestModuleManage> getByIds(List<String> ids);


    /**
     * 
    * @Function: ITestModuleManageService.java
    * @Title: deleteModuleAndVersion
    * @Description: 多表删除
    * @param id
    * @return
    * Integer
     */
    public Integer deleteModuleAndVersion(String id);

    Object test();
}
