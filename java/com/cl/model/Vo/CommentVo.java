package com.cl.model.Vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_comment")
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * comment表主键
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 评论针对哪篇文章
     */
    @Column(nullable = false)
    private Long contentId;

    /**
     * 评论生成时的GMT unix时间戳
     */
    @Column
    private String created;

    /**
     * 评论人名称
     */
    @Column
    private String name;

    /**
     * 评论者邮件
     */
    @Column
    private String mail;

    /**
     * 评论者网址
     */
    @Column
    private String url;

    /**
     * 评论内容
     */
    @Column(nullable = false,length = 500)
    private String content;


    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentVo{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", created='" + created + '\'' +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
