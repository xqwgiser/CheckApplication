package com.example.xqw.checkinapplication.main.rest.model;

import com.example.xqw.checkinapplication.bean.ItemEntity;

import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/26.
 */
public interface IRestModel {
    void getRestList(Subscriber<ItemEntity<List<Map>>> subscriber, String service, String method,
                     String page, String pageSize);
    void postRestInfo(Subscriber<ItemEntity<String>> subscriber, String service, String method,
                      String rows);
    void getRestType(Subscriber<List<Map>> subscriber, String service, String method);
    String buildInfo(Map map,boolean isAdd);
}
