package com.test.demo.sys.controller;

import com.test.demo.user.annotation.ApiVersion;
import com.test.demo.user.model.CommonRsp;
import com.test.demo.user.model.page.PageResult;
import com.test.demo.sys.feign.UserFeignService;
import com.test.demo.sys.model.dto.TestDto;
import com.test.demo.sys.model.dto.TestModulePageDto;
import com.test.demo.sys.model.entity.TestModuleManage;
import com.test.demo.sys.service.ITestModuleManageService;
import com.test.demo.user.model.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 程序包模块表 前端控制器
 * </p>
 *框架使用样例
 * @author wgg
 * @since 2021-04-05
 */
@Api(value = "测试Demo1111", tags = "测试Demo111")
@ApiVersion(1) // 引入api版本定义
@RestController
@RequestMapping("{version}/module")
public class TestModuleManageController {

    @Autowired
    private ITestModuleManageService testModuleManageService;

    @Autowired
    private UserFeignService userFeignService;

    @ApiOperation(value = "新增模块信息                        作者：王广广              日期： 2020年04月05日", notes = "单条新增入参带校验样例")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonRsp<Boolean> save(@RequestBody @Validated TestModuleManage param, BindingResult bindingResult) {
        return MessageUtil.success(testModuleManageService.save(param));
    }

    @ApiOperation(value = "新增模块信息(批量)       作者：王广广              日期： 2020年04月05日", notes = "批量新增样例")
    @RequestMapping(value = "inserts", method = RequestMethod.POST)
    public CommonRsp<Boolean> inserts(@RequestBody List<TestModuleManage> param) {
        return MessageUtil.success(testModuleManageService.saveBatch(param));
    }

    @ApiOperation(value = "修改模块信息根据Id                        作者：王广广              日期： 2020年04月05日", notes = "单条记录修改样例")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public CommonRsp<Boolean> update(@RequestBody TestModuleManage param) {
        return MessageUtil.success(testModuleManageService.updateById(param));
    }

    @ApiOperation(value = "删除模块信息                        作者：王广广              日期： 2020年04月05日", notes = "单条删除样例")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public CommonRsp<Boolean> delete(@PathVariable("id") String id) {
        return MessageUtil.success(testModuleManageService.removeById(id));
    }

    @ApiOperation(value = "删除模块信息(批量)        作者：王广广              日期： 2020年04月05日", notes = "批量删除样例")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public CommonRsp<Boolean> delete(@RequestBody List<String> ids) {
        return MessageUtil.success(testModuleManageService.removeByIds(ids));
    }

    @ApiOperation(value = "多表删除带事务操作                        作者：王广广              日期： 2020年04月05日", notes = "事务操作样例")
    @RequestMapping(value = "version/{id}", method = RequestMethod.DELETE)
    public CommonRsp<Integer> deleteModuleAndVersion(@RequestParam(value = "id") String id) {
        return MessageUtil.success(testModuleManageService.deleteModuleAndVersion(id));
    }

    @ApiOperation(value = "获取模块单条信息           作者：王广广              日期： 2020年04月05日", notes = "查询单条对象样例")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public CommonRsp<TestModuleManage> info(@PathVariable("id") String id) {
        return MessageUtil.success(testModuleManageService.getBaseMapper().selectById(id));
    }

    @ApiOperation(value = "获取模块列表带分页page           作者：王广广              日期： 2020年04月05日", notes = "分页查询包含模糊查询排序等样例")
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public CommonRsp<PageResult<TestModuleManage>> page(TestModulePageDto param) {
        return MessageUtil.success(testModuleManageService.page(param));
    }

    @ApiOperation(value = "获取模块列表list           作者：王广广              日期： 2020年04月05日", notes = "根据条件过滤获取list样例")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public CommonRsp<List<TestModuleManage>> list(TestDto param) {
        return MessageUtil.success(testModuleManageService.list(param));
    }

    @ApiOperation(value = "根据多个Id获取模块list           作者：王广广              日期： 2020年04月05日", notes = "批量查询样例")
    @RequestMapping(value = "list/by/ids", method = RequestMethod.GET)
    public CommonRsp<List<TestModuleManage>> getListByIds(@RequestParam("ids") List<String> ids) {
        return MessageUtil.success(testModuleManageService.getBaseMapper().selectBatchIds(ids));
    }

    @ApiOperation(value = "根据多个Id获取记录                      作者：王广广              日期： 2020年04月05日", notes = "使用xml写sql样例")
    @RequestMapping(value = "by/ids", method = RequestMethod.GET)
    public CommonRsp<List<TestModuleManage>> getByIds(@RequestParam("ids") List<String> ids) {
        return MessageUtil.success(testModuleManageService.getByIds(ids));
    }

    @ApiOperation(value = "测试                      作者：王广广              日期： 2020年04月05日", notes = "使用xml写sql样例")
    @RequestMapping(value = "test1", method = RequestMethod.GET)
    public CommonRsp<?> test() {
        return MessageUtil.success(userFeignService.test());
    }


    @ApiOperation(value = "测试远程调用page           作者：王广广              日期： 2020年04月05日", notes = "分页查询包含模糊查询排序等样例")
    @RequestMapping(value = "testpage", method = RequestMethod.GET)
    public CommonRsp<?> testpage(@RequestParam(value = "id") String id) {
        return MessageUtil.success(userFeignService.info(id));

    }
}
