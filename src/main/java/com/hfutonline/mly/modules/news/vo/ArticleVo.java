package com.hfutonline.mly.modules.news.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenliangliang
 * @date 2018/10/15
 */
@Data
public class ArticleVo implements Serializable {

    private static final long serialVersionUID = 8372764325396239263L;
    private Integer articleId;
    private String title;
    private String summary;
    private String content;
    private String author;
    private String copyFrom;
    private Integer readNum;
    private String cover;
    private Date publishTime;
}
