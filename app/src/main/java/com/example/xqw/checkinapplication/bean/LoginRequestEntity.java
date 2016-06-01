package com.example.xqw.checkinapplication.bean;

/**
 * Created by xqw on 2016/4/27.
 */
public class LoginRequestEntity<T> {
    private String service;
    private String method;
    private T data;
    private String userid;
    private String password;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
