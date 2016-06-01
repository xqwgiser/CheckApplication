package com.example.xqw.checkinapplication.main.log.presenter;

import android.content.Intent;
import android.util.Log;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.log.model.ILogModel;
import com.example.xqw.checkinapplication.main.log.model.LogModel;
import com.example.xqw.checkinapplication.main.log.view.ILogEditView;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/25.
 */
public class LogEditPresenter implements IPresenterFactory<ILogEditPresenter> {
    private ILogEditView logEditView;
    private ILogModel logModel;

    public LogEditPresenter(ILogEditView logEditView) {
        this.logEditView = logEditView;
        logModel = new LogModel();
    }

    @Override
    public ILogEditPresenter create() {
        return new ILogEditPresenter() {
            @Override
            public void postLogInfo(Intent intent) {
                Subscriber<ItemEntity<String>> subscriber = new Subscriber<ItemEntity<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ItemEntity<String> listCheckItemEntity) {
                        logEditView.showResult(listCheckItemEntity);
                    }
                };
                logModel.postLogInfo(subscriber, "service.GzrzService", "saveChanges",
                        buildInfo(logEditView.getLogInfo(),intent.getBooleanExtra("isAdd",true)));
            }

            @Override
            public void setLogInfo(Intent intent) {
                if (!intent.getBooleanExtra("isAdd", true)) {
                    logEditView.setLogInfo(intent);
                }
            }

            @Override
            public void onViewAttached() {

            }

            @Override
            public void onViewDetached() {

            }

            @Override
            public void onDestroyed() {

            }

            private String buildInfo(Map map,boolean isAdd) {
                Map<String, List> result = new HashMap<>();
                List<Map> item = new ArrayList<>();
                Map<String, String> logItem = new HashMap<>();
                logItem.put("txsj", map.get("txsj").toString());
                logItem.put("userid", map.get("userid").toString());
                logItem.put("gznr", map.get("gznr").toString());

                try {
                    if(isAdd) {
                        item.add(logItem);
                        result.put("inserted", item);
                        result.put("updated", new ArrayList());
                        result.put("deleted", new ArrayList());
                    }else {
                        logItem.put("guid", map.get("guid").toString());
                        item.add(logItem);
                        result.put("inserted", new ArrayList());
                        result.put("updated", item);
                        result.put("deleted", new ArrayList());
                    }

                } catch (JsonIOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Log.e("msg",gson.toJson(result));
                return gson.toJson(result);
            }
        };
    }
}
