package com.test.demo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.demo.user.model.page.PageResult;
import com.test.demo.user.model.page.PageUtils;
import com.test.demo.sys.model.entity.TestModuleManage;
import com.test.demo.sys.service.ITestModuleManageService;
import com.test.demo.user.thread.ThreadPoolsUtils;
import com.test.demo.user.util.BeanUtil;
import com.test.demo.sys.mapper.TestModuleManageMapper;
import com.test.demo.sys.model.dto.TestDto;
import com.test.demo.sys.model.dto.TestModulePageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 程序包模块表 服务实现类
 * </p>
 *
 * @author wgg
 * @since 2021-04-05
 */
@Service
@Slf4j
public class TestModuleManageServiceImpl extends ServiceImpl<TestModuleManageMapper, TestModuleManage>
        implements ITestModuleManageService {

    @Autowired
    private TestModuleManageMapper testModuleManageMapper;

    @Resource
    private ThreadPoolsUtils threadPoolsUtils;

    @Override
    public List<TestModuleManage> list(TestDto param) {

        TestModuleManage testModuleManage = BeanUtil.convertVO(param, new TestModuleManage());
        QueryWrapper<TestModuleManage> qMoQueryWrapper = new QueryWrapper<>();
        //封装排序参数
        qMoQueryWrapper.lambda().orderByDesc(TestModuleManage::getCreateTime);
        //封装其他过滤参数
        qMoQueryWrapper.setEntity(testModuleManage);
        String searchValue = param.getSearchValue();
        // 封装searchValue模糊查询
        if (null != searchValue) {
            qMoQueryWrapper.lambda().and(obj -> obj.like(TestModuleManage::getModuleName, searchValue).or()
                    .like(TestModuleManage::getModuleCode, searchValue).or().like(TestModuleManage::getDescr, searchValue));
        }
        List<TestModuleManage> list = testModuleManageMapper.selectList(qMoQueryWrapper);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageResult<TestModuleManage> page(TestModulePageDto param) {
        TestModuleManage moble = BeanUtil.convertVO(param, new TestModuleManage());
        // 封装分页参数
        IPage<TestModuleManage> page = new Page<>(moble.getPageNum(), moble.getPageSize());
        // 封装排序参数
        QueryWrapper<TestModuleManage> qMoQueryWrapper = new QueryWrapper<>();
        qMoQueryWrapper.lambda().orderByDesc(TestModuleManage::getCreateTime);
        // 封装searchValue模糊查询
        qMoQueryWrapper.lambda()
                .and(obj -> obj.like(TestModuleManage::getModuleName, param.getSearchValue()).or()
                        .like(TestModuleManage::getModuleCode, param.getSearchValue()).or()
                        .like(TestModuleManage::getDescr, param.getSearchValue()));
        // 封装其他过滤参数
        qMoQueryWrapper.setEntity(moble);
        IPage<TestModuleManage> ipage = testModuleManageMapper.selectPage(page, qMoQueryWrapper);
        return PageUtils.convert(page, ipage.getRecords());
    }

    @Override
    public List<TestModuleManage> getByIds(List<String> ids) {
        return testModuleManageMapper.selectModuleListByIds(ids);
    }


    @Transactional(rollbackFor = Exception.class) // 运行时和非运行时异常都会回滚
    @Override
    public Integer deleteModuleAndVersion(String id) {
        // 先删除test_module_manager
//        Integer result = testModuleManageMapper.deleteById(id);
//        // 再删除test_module_version
//        Map<String, Object> columnMap = new HashMap<String, Object>();
//        columnMap.put("module_id", id);
//        result = testModuleVersionMapper.deleteByMap(columnMap);
        return 0;
    }

    @Override
    public Object test() {
        threadPoolsUtils.getThreadPoolExecutor().submit(() -> {

            for (int i = 1; i <= 30; i++) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getId() + "——" + i);
            }

        });
        System.out.println("结束");
        return null;
    }
}
