package com.example.xqw.checkinapplication.main.notice.model;

import com.example.xqw.checkinapplication.bean.ItemEntity;

import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/27.
 */
public interface INoticeModel {
    void getNoticeList(Subscriber<ItemEntity<List<Map>>> subscriber, String service, String method,
                    String page, String pageSize);
}
