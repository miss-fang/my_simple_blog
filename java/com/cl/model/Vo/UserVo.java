package com.cl.model.Vo;


import javax.persistence.*;
import java.io.Serializable;

/**
 * 没有用户需注册登录，
 */
@Entity
@Table(name = "t_user")
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Integer id;
    @Column(length = 50)
    private String username;
    /**
     * 密码暂时只有admin的，因为没有用户啊
     * 禁用ip放在后台管理
     */
    @Column(length = 20)
    private String password;
    @Column(length = 50)
    private String email;
    @Column(length = 100)
    private String homeUrl;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    /**
     * 只在访问用户留下email或url时记录此用户，此时名字如没有则默认“热心网友”+id
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 只在访问用户留下email或url时记录此用户
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 只在访问用户留下email或url时记录此用户
     *
     * @return
     */
    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

}
