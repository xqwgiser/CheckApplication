package com.example.xqw.checkinapplication.main.log.model;

import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.utils.httpUtils.HttpMethods;
import com.example.xqw.checkinapplication.utils.httpUtils.Service;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xqw on 2016/5/25.
 */
public class LogModel implements ILogModel{
    @Override
    public void getLogList(Subscriber<ItemEntity<List<Map>>> subscriber, String service, String method, String page, String pageSize) {
        HttpMethods.getInstance()
                .getRetrofit()
                .create(Service.class)
                .getList(service,method,page,pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void postLogInfo(Subscriber<ItemEntity<String>> subscriber, String service, String method, String rows) {
        HttpMethods.getInstance()
                .getRetrofit()
                .create(Service.class)
                .postInfo(service,method,rows)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
