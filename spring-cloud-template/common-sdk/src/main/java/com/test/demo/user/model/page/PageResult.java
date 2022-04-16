package com.test.demo.user.model.page;


import java.util.List;

import lombok.Data;

@Data
public class PageResult<T> {
    /**
     * 查询数据列表
     */
    private List<T> list;

    /**
     * 总数
     */
    private long total;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize;

    /**
     * 当前页
     */
    private long pageNum;

}
