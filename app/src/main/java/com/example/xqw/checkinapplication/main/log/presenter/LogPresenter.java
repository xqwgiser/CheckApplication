package com.example.xqw.checkinapplication.main.log.presenter;

import android.util.Log;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.log.model.ILogModel;
import com.example.xqw.checkinapplication.main.log.model.LogModel;
import com.example.xqw.checkinapplication.main.log.view.ILogView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/3.
 */
public class LogPresenter implements IPresenterFactory<ILogPresenter> {
    private ILogView logView;
    private ILogModel logModel;
    public LogPresenter(ILogView logView) {
        this.logView = logView;
        logModel =new LogModel();
    }

    @Override
    public ILogPresenter create() {
        return new ILogPresenter() {
            @Override
            public void deleteLog(Map map) {
                Subscriber<ItemEntity<String>> subscriber=new Subscriber<ItemEntity<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error","message",e);
                    }

                    @Override
                    public void onNext(ItemEntity<String> listItemEntity) {
                        logView.showDeleteResult(listItemEntity.getRows());
                    }
                };
                logModel.postLogInfo(subscriber,"service.GzrzService", "saveChanges",buildInfo(map));
            }

            @Override
            public void onViewAttached() {
                Subscriber<ItemEntity<List<Map>>> subscriber=new Subscriber<ItemEntity<List<Map>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ItemEntity<List<Map>> listCheckItemEntity) {
                        logView.showLogList(listCheckItemEntity.getRows());
                    }
                };
                logModel.getLogList(subscriber,"service.GzrzService","queryGzrzList","1","10");
            }

            @Override
            public void onViewDetached() {

            }

            @Override
            public void onDestroyed() {

            }
            private String buildInfo(Map map) {
                Map<String, List> result = new HashMap<>();
                List<Map> item = new ArrayList<>();
                Map<String, String> logItem = new HashMap<>();
                logItem.put("guid", map.get("guid").toString());
                item.add(logItem);
                result.put("inserted", new ArrayList());
                result.put("updated", new ArrayList());
                result.put("deleted", item);
                Gson gson = new Gson();
                Log.e("msg",gson.toJson(result));
                return gson.toJson(result);
            }
        };
    }
}
