package com.example.xqw.checkinapplication.main.log.model;

import com.example.xqw.checkinapplication.bean.ItemEntity;

import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/25.
 */
public interface ILogModel {
    void getLogList(Subscriber<ItemEntity<List<Map>>> subscriber, String service, String method,
                    String page, String pageSize);
    void postLogInfo(Subscriber<ItemEntity<String>> subscriber, String service, String method,
                     String rows);
}
