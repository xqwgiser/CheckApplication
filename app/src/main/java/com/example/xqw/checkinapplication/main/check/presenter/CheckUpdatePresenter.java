package com.example.xqw.checkinapplication.main.check.presenter;

import android.content.Intent;
import android.util.Log;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.check.model.CheckModel;
import com.example.xqw.checkinapplication.main.check.model.ICheckModel;
import com.example.xqw.checkinapplication.main.check.view.ICheckUpdateView;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/24.
 */
public class CheckUpdatePresenter implements IPresenterFactory<ICheckUpdatePresenter> {
    private ICheckUpdateView checkUpdateView;
    private ICheckModel checkModel;

    public CheckUpdatePresenter(ICheckUpdateView checkUpdateView) {
        this.checkUpdateView = checkUpdateView;
        checkModel=new CheckModel();
    }

    @Override
    public ICheckUpdatePresenter create() {
        return new ICheckUpdatePresenter() {
            @Override
            public void setCheckType() {
                Subscriber<List<Map>> subscriber = new Subscriber<List<Map>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Map> maps) {
                        checkUpdateView.setCheckType(maps);
                    }
                };
                checkModel.getCheckType(subscriber, "service.GzqdService", "queryGzqdlx");
            }

            @Override
            public void setCheckInfo(Intent intent) {
                checkUpdateView.setCheckInfo(intent);
            }

            @Override
            public void updateCheckInfo(Intent intent) {
                Subscriber<ItemEntity<String>> subscriber=new Subscriber<ItemEntity<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("e", "errorMsg", e);
                    }

                    @Override
                    public void onNext(ItemEntity<String> stringCheckItemEntity) {
                        checkUpdateView.showUpdateResult(stringCheckItemEntity);
                    }
                };
                checkModel.postCheckInfo(subscriber,"service.GzqdService","saveChanges",
                        buildInfo(checkUpdateView.getCheckInfo(intent)));
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
            private String buildInfo(Map map){
                Map<String,List> result = new HashMap<>();
                List<Map> item = new ArrayList<>();
                Map<String, String> infoItem = new HashMap<>();
                infoItem.put("guid",map.get("guid").toString());
                infoItem.put("qdsj", map.get("qdsj").toString());
                infoItem.put("qdwz", map.get("qdwz").toString());
                infoItem.put("qtsj", map.get("qtsj").toString());
                infoItem.put("qtwz", map.get("qtwz").toString());
                infoItem.put("gzsc", map.get("gzsc").toString());
                infoItem.put("qdlx",map.get("qdlx").toString());
                infoItem.put("userid", map.get("userid").toString());

                item.add(infoItem);
                try {
                    result.put("inserted", new ArrayList());
                    result.put("updated", item);
                    result.put("deleted", new ArrayList());

                } catch (JsonIOException e) {
                    e.printStackTrace();
                }
                Gson gson=new Gson();
                Log.e("update",gson.toJson(result));
                return gson.toJson(result);
            }
        };
    }
}
