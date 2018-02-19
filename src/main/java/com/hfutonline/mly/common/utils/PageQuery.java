package com.hfutonline.mly.common.utils;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author chenliangliang
 * @date 2018/2/19
 */
public class PageQuery<T> {

    /**
     * mybatis-plus分页参数
     */
    private Page<T> pageParam;

    /**
     * 当前页码
     */
    private int page = 1;
    /**
     * 每页条数
     */
    private int size = 10;

    public PageQuery(Map<String, Object> params) {
        //分页参数
        if (params.get("page") != null) {
            page = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("size") != null) {
            size = Integer.parseInt((String) params.get("size"));
        }
        String sidx = (String)params.get("sidx");
        String order = (String)params.get("order");

        this.pageParam=new Page<>(page,size);

        //排序
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
            this.pageParam.setOrderByField(sidx);
            this.pageParam.setAsc("ASC".equalsIgnoreCase(order));
        }
    }


    public Page<T> getPageParam() {
        return pageParam;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
