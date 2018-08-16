package com.champion.blog.model.vo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 文章
 */
@Document(collection = "content")
public class ContentVo implements Serializable {

    /**
     * post表主键
     */
    @Field(value = "_id")
    private Integer cid;

    /**
     * 内容标题
     */
    @Field(value = "title")
    private String title;

    /**
     * 内容缩略名
     */
    @Field(value = "slug")
    private String slug;

    /**
     * 内容生成时的GMT unix时间戳
     */
    @Field(value = "created")
    private Integer created;

    /**
     * 内容更改时的GMT unix时间戳
     */
    @Field(value = "modified")
    private Integer modified;

    /**
     * 内容所属用户id
     */
    @Field(value = "authorId")
    private Integer authorId;

    /**
     * 内容类别
     */
    @Field(value = "type")
    private String type;

    /**
     * 内容状态
     */
    @Field(value = "status")
    private String status;

    /**
     * 标签列表
     */
    @Field(value = "tags")
    private String tags;

    /**
     * 分类列表
     */
    @Field(value = "categories")
    private String categories;

    /**
     * 点击次数
     */
    @Field(value = "hits")
    private Integer hits;

    /**
     * 内容所属评论数
     */
    @Field(value = "commentsNum")
    private Integer commentsNum;

    /**
     * 是否允许评论
     */
    @Field(value = "allowComment")
    private Boolean allowComment;

    /**
     * 是否允许ping
     */
    @Field(value = "allowPing")
    private Boolean allowPing;

    /**
     * 允许出现在聚合中
     */
    @Field(value = "allowFeed")
    private Boolean allowFeed;

    /**
     * 内容文字
     */
    @Field(value = "content")
    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getModified() {
        return modified;
    }

    public void setModified(Integer modified) {
        this.modified = modified;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Boolean getAllowPing() {
        return allowPing;
    }

    public void setAllowPing(Boolean allowPing) {
        this.allowPing = allowPing;
    }

    public Boolean getAllowFeed() {
        return allowFeed;
    }

    public void setAllowFeed(Boolean allowFeed) {
        this.allowFeed = allowFeed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
