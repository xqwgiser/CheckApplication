package com.example.xqw.checkinapplication.main.rest.presenter;

import android.util.Log;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.rest.model.IRestModel;
import com.example.xqw.checkinapplication.main.rest.model.RestModel;
import com.example.xqw.checkinapplication.main.rest.view.IRestView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/26.
 */
public class RestPresenter implements IPresenterFactory<IRestPresenter> {
    private IRestView restView;
    private IRestModel restModel;

    public RestPresenter(IRestView restView) {
        this.restView = restView;
        restModel=new RestModel();
    }

    @Override
    public IRestPresenter create() {
        return new IRestPresenter() {
            @Override
            public void deleteRestInfo(Map id) {
                Subscriber<ItemEntity<String>> subscriber=new Subscriber<ItemEntity<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ItemEntity<String> stringItemEntity) {
                        restView.showDeleteResult(stringItemEntity.getRows());
                    }
                };
                restModel.postRestInfo(subscriber,"service.QjsqService","saveChanges",buildInfo(id));
            }

            @Override
            public void onViewAttached() {
                Subscriber<ItemEntity<List<Map>>> subscriber=new Subscriber<ItemEntity<List<Map>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("e","error",e);
                    }

                    @Override
                    public void onNext(ItemEntity<List<Map>> listItemEntity) {
                        restView.showRestList(listItemEntity.getRows());
                    }
                };
                restModel.getRestList(subscriber,"service.QjsqService","queryQjsqList","1","10");
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
                item.add(map);
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
