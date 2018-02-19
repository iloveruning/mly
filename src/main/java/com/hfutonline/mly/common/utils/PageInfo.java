package com.hfutonline.mly.common.utils;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenliangliang
 * @date 2018/2/19
 */
@Data
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 4042924045710218720L;

    /**
     * 总记录数
     */
    private int total;
    /**
     * 每页条数
     */
    private int size;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 当前页数
     */
    private int page;
    /**
     * 具体数据
     */
    private List<T> list;

    public PageInfo(Page<T> page) {
        this.size = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.page = page.getCurrent();
        this.list = page.getRecords();
    }


}
