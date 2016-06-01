package com.example.xqw.checkinapplication.bean;

/**
 * Created by xqw on 2016/4/26.
 */
public class LoginResponseEntity {
    private String password;
    private String userid;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
