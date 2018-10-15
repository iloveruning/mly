package com.hfutonline.mly.modules.news.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/10/15
 */
@Data
public class CatalogVo implements Serializable {

    private static final long serialVersionUID = -4156270906843271238L;
    private Integer catalogId;
    private String catalogName;

}
