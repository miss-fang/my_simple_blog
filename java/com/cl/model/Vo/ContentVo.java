package com.cl.model.Vo;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_content")
public class ContentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内容表主键
     */
    @Id
    @GeneratedValue
    @Column
    private Long id;

    /**
     * 内容标题，length好像没有作用，在表中改变吧
     */
    @Column(length = 100)
    private String title;
    /**
     * 内容文字，此内容为markdown语法内容，回显时要还原
     */
    @Column(length = 10000)
    private String content;
    /**

    /**
     * 内容生成时的时间戳
     */
    @Column(length = 30)
    private String created;


    /**
     * 内容更改时的时间戳
     */
    @Column(length = 30)
    private String modified;


    /**
     * 标签列表
     */
    @Column(length = 50)
    private String tags;

    /**
     * 分类列表
     */
    @Column(length = 20)
    private String categories;

    /**
     * 点击次数
     */
    @Column

    private Integer hits;

    /**
     * 内容所属评论数
     */
    @Column
    private Integer commentsNum;

    /**
     * 是否允许评论，默认允许，更改放在后台
     */
    @Column
    private Boolean allowComment;
    /**
     * 文章对应随机图片id，保存文章时自动生成
     */
    @Column
    private Integer rand;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRand() {
        return rand;
    }

    public void setRand(Integer rand) {
        this.rand = rand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
