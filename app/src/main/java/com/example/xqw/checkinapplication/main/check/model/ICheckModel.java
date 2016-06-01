package com.example.xqw.checkinapplication.main.check.model;

import com.example.xqw.checkinapplication.bean.ItemEntity;

import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/5.
 */
public interface ICheckModel {
    void getCheckType(Subscriber<List<Map>> subscriber, String service, String method);
    void getCheckList(Subscriber<ItemEntity<List<Map>>> subscriber, String service, String method,
                      String page, String pageSize);
    void postCheckInfo(Subscriber<ItemEntity<String>> subscriber, String service, String method,
                       String rows);
    void saveCheckInInfo(Map map);
    void saveCheckOutInfo();

}
