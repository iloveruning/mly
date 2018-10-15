package com.hfutonline.mly.modules.news.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/10/15
 */
@Data
public class TagVo implements Serializable {

    private static final long serialVersionUID = 9136033327457344844L;
    private Integer tagId;
    private String tagName;

}
