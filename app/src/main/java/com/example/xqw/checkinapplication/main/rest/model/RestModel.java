package com.example.xqw.checkinapplication.main.rest.model;

import android.util.Log;

import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.utils.httpUtils.HttpMethods;
import com.example.xqw.checkinapplication.utils.httpUtils.Service;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xqw on 2016/5/26.
 */
public class RestModel implements IRestModel {
    @Override
    public void getRestList(Subscriber<ItemEntity<List<Map>>> subscriber, String service, String method, String page, String pageSize) {
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
    public void postRestInfo(Subscriber<ItemEntity<String>> subscriber, String service, String method, String rows) {
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
    public void getRestType(Subscriber<List<Map>> subscriber, String service, String method) {
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
    public String buildInfo(Map map,boolean isAdd){
        Map<String, List> result = new HashMap<>();
        List<Map> item = new ArrayList<>();
        Map<String,String> restItem=new HashMap<>();
        restItem.put("userid",map.get("userid").toString());
        restItem.put("qjkssj",map.get("qjkssj").toString());
        restItem.put("qjjssj",map.get("qjjssj").toString());
        restItem.put("qjts",map.get("qjts").toString());
        restItem.put("qjlx",map.get("qjlx").toString());
        restItem.put("qjsy",map.get("qjsy").toString());
        restItem.put("sqsj",map.get("sqsj").toString());
        restItem.put("status",map.get("status").toString());

        if(isAdd){
            item.add(restItem);
            result.put("inserted", item);
            result.put("updated", new ArrayList());
            result.put("deleted", new ArrayList());
        }else {
            restItem.put("guid",map.get("guid").toString());
            item.add(restItem);
            result.put("inserted", new ArrayList());
            result.put("updated", item);
            result.put("deleted", new ArrayList());
        }
        Gson gson = new Gson();
        Log.e("msg",gson.toJson(result));
        return gson.toJson(result);
    }
}
