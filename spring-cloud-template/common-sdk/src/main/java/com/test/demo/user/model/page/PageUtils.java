package com.test.demo.user.model.page;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

public class PageUtils {

    public static <T> PageResult<T> convert(IPage<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setList(page.getRecords());
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        return result;
    }
    
    public static PageResult convert(IPage<?> page, List list) {
        PageResult<?> myPageBean = new PageResult<>();
        myPageBean.setList(list);
        myPageBean.setPageNum(page.getCurrent());
        myPageBean.setPageSize(page.getSize());
        myPageBean.setTotal(page.getTotal());
        myPageBean.setPages(page.getPages());
        return myPageBean;
    }
}