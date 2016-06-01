package com.example.xqw.checkinapplication.main.check.presenter;

import android.util.Log;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.check.model.CheckModel;
import com.example.xqw.checkinapplication.main.check.model.ICheckModel;
import com.example.xqw.checkinapplication.main.check.view.ICheckOutView;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/20.
 */
public class CheckOutPresenter implements IPresenterFactory<ICheckOutPresenter> {
    private ICheckOutView checkOutView;
    private ICheckModel checkModel;

    public CheckOutPresenter(ICheckOutView checkOutView) {
        this.checkOutView = checkOutView;
        checkModel = new CheckModel();
    }

    @Override
    public ICheckOutPresenter create() {
        return new ICheckOutPresenter() {
            @Override
            public void onViewAttached() {
                checkOutView.setCheckInfo();
            }

            @Override
            public void onViewDetached() {

            }

            @Override
            public void onDestroyed() {

            }

            @Override
            public void postCheckInfo() {
                checkModel.saveCheckOutInfo();
                Subscriber<ItemEntity<String>> subscriber = new Subscriber<ItemEntity<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("e", "errorMsg", e);
                    }

                    @Override
                    public void onNext(ItemEntity<String> mapCheckItemEntity) {
                        checkOutView.showResult(mapCheckItemEntity);
                    }
                };
                checkModel.postCheckInfo(subscriber, "service.GzqdService", "saveChanges", buildInfo());
            }

            private String buildInfo() {
                Map<String,List> result = new HashMap<>();
                List<Map> item = new ArrayList<>();
                Map<String, String> infoItem = new HashMap<>();

                infoItem.put("qdsj", SharedPreferencesUtils.readString("qdsj"));
                infoItem.put("qdwz", SharedPreferencesUtils.readString("qdwz"));
                infoItem.put("qtsj", SharedPreferencesUtils.readString("qtsj"));
                infoItem.put("qtwz", SharedPreferencesUtils.readString("qtwz"));
                infoItem.put("gzsc", SharedPreferencesUtils.readString("gzsc"));
                infoItem.put("userid", SharedPreferencesUtils.readString("userId"));

                item.add(infoItem);
                try {
                    result.put("inserted", item);
                    result.put("updated", new ArrayList());
                    result.put("deleted", new ArrayList());

                } catch (JsonIOException e) {
                    e.printStackTrace();
                }
                Gson gson=new Gson();
                String temp=null;
                Log.e("提交信息",gson.toJson(result));
                try {
                    temp= new String(gson.toJson(result).getBytes(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return temp;
            }
        };
    }
}
