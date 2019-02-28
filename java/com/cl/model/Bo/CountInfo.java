package com.cl.model.Bo;
/**
 * 没用到
 * @author chenlun
 *
 */
public class CountInfo {
    private int articleCount;
    private int commentCount;
    /**
     * 标签数
     */
    private int tagCount;
    /**
     * 分类数
     */
    private int categoryCount;
    /**
     * 链接数
     */
    private int linkCount;
    /**
     * 附件数
     */
    private int attacheCount;


    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLinkCount() {
        return linkCount;
    }

    public void setLinkCount(int linkCount) {
        this.linkCount = linkCount;
    }

    public int getAttacheCount() {
        return attacheCount;
    }

    public void setAttacheCount(int attacheCount) {
        this.attacheCount = attacheCount;
    }
}
