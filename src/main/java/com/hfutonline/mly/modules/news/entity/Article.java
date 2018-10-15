package com.hfutonline.mly.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.hfutonline.mly.common.validator.group.Add;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章管理
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
@TableName("article")
public class Article implements Serializable {


    private static final long serialVersionUID = 8956305525538320811L;
    /**
     * 新闻id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空", groups = {Add.class})
    private String title;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 内容
     */
    @NotBlank(message = "文章内容不能为空", groups = {Add.class})
    private String content;
    /**
     * 作者
     */
    private String author = "明理苑";
    /**
     * 状态：0-未审核，1-审核未通过，2-审核通过，3-优秀
     */
    private Integer state;
    /**
     * 文章权重1-100
     */
    private Integer weight;
    /**
     * 文章来源
     */
    private String copyFrom = "明理苑";
    /**
     * 新闻封面
     */
    private String cover;
    /**
     * 阅读量
     */
    private Integer read = 0;
    /**
     * 发布文章的管理员
     */
    @NotBlank(message = "发布文章的用户不能为空",groups = {Add.class})
    private String username;
    /**
     * 文章未通过的理由
     */
    private String reason;
    /**
     * 文章上显示的时间
     */
    private Date publishTime;
    /**
     * 新闻提交到后台的时间
     */
    private Date createTime;
    /**
     * 新闻最后被修改的时间
     */
    private Date updateTime;


    /**
     * 文章对应的标签id
     */
    @NotEmpty(message = "文章标签列表不能为空",groups = {Add.class})
    @TableField(exist = false)
    private List<Integer> tagIdList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCopyFrom() {
        return copyFrom;
    }

    public void setCopyFrom(String copyFrom) {
        this.copyFrom = copyFrom;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }
}
