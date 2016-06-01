package com.example.xqw.checkinapplication.main.notice.model;

import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.utils.httpUtils.HttpMethods;
import com.example.xqw.checkinapplication.utils.httpUtils.Service;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xqw on 2016/5/27.
 */
public class NoticeModel implements INoticeModel {

    @Override
    public void getNoticeList(Subscriber<ItemEntity<List<Map>>> subscriber, String service, String method, String page, String pageSize) {
        HttpMethods.getInstance()
                .getRetrofit()
                .create(Service.class)
                .getList(service,method,page,pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
