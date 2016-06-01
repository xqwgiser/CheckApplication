package com.example.xqw.checkinapplication.bean;

/**
 * Created by xqw on 2016/4/18.
 */
public class CheckBean {
    private String checkTime;
    private String checkContent;
    private String workTime;
    private String all;

    public String getAll() {
        return checkTime+checkContent+"工作时长："+workTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
}
