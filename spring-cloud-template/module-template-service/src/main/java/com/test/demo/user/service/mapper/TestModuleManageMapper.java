package com.test.demo.user.service.mapper;

import com.test.demo.user.service.model.entity.TestModuleManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 程序包模块表 Mapper 接口
 * </p>
 *
 * @author wgg
 * @since 2021-04-05
 */
public interface TestModuleManageMapper extends BaseMapper<TestModuleManage> {

    /**
     * 根据模块Id查询多条记录
     * @param ids
     * @return
     */
    List<TestModuleManage> selectModuleListByIds(@Param("ids") List<String> ids);

}
