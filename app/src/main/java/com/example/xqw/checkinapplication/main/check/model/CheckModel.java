package com.example.xqw.checkinapplication.main.check.model;

import android.support.annotation.NonNull;

import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;
import com.example.xqw.checkinapplication.utils.Utils;
import com.example.xqw.checkinapplication.utils.httpUtils.HttpMethods;
import com.example.xqw.checkinapplication.utils.httpUtils.Service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xqw on 2016/5/5.
 */
public class CheckModel implements ICheckModel {


    @Override
    public void getCheckType(Subscriber<List<Map>> subscriber, String service, String method) {
        HttpMethods.getInstance()
                .getRetrofit()
                .create(Service.class)
                .getType(service, method)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getCheckList(Subscriber<ItemEntity<List<Map>>> subscriber, String service, String method, String page, String pageSize) {
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
    public void postCheckInfo(Subscriber<ItemEntity<String>> subscriber, String service, String method, String rows) {
        HttpMethods.getInstance()
                .getRetrofit()
                .create(Service.class)
                .postInfo(service,method,rows)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @Override
    public void saveCheckInInfo(Map map) {
        Calendar calendar=Calendar.getInstance();
        SharedPreferencesUtils.writeBoolean("isCheckIn",false);
        SharedPreferencesUtils.writeString("qdsj",map.get("qdsj").toString());
        SharedPreferencesUtils.writeString("qdwz",map.get("qdwz").toString());
        SharedPreferencesUtils.writeString("qdlx",map.get("qdlx").toString());
        SharedPreferencesUtils.writeInt("time",calendar.get(Calendar.HOUR_OF_DAY));
    }

    @Override
    public void saveCheckOutInfo() {
        SharedPreferencesUtils.writeBoolean("isCheckIn", true);
        SharedPreferencesUtils.writeString("qtsj", Utils.getSystemTime());
        SharedPreferencesUtils.writeString("qtwz", "113.25,23.33");
        SharedPreferencesUtils.writeString("gzsc", getWorkTime());
    }

    @NonNull
    private String getWorkTime() {
        Calendar calendar = Calendar.getInstance();
        String hour=null;
        try {
            hour=new String("小时".getBytes(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) -
                SharedPreferencesUtils.readInt("time", 9))+hour;
    }
}
