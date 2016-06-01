package com.example.xqw.checkinapplication.main.check.presenter;

import android.util.Log;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.check.model.CheckModel;
import com.example.xqw.checkinapplication.main.check.model.ICheckModel;
import com.example.xqw.checkinapplication.main.check.view.ICheckView;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/3.
 */
public class CheckPresenter implements IPresenterFactory<ICheckPresenter> {
    private ICheckView checkView;
    private ICheckModel checkModel;
    public CheckPresenter(ICheckView checkView) {
        this.checkView = checkView;
        checkModel=new CheckModel();
    }

    @Override
    public ICheckPresenter create() {
        return new ICheckPresenter() {

            @Override
            public void showCheckList() {
                Subscriber<ItemEntity<List<Map>>> subscriber=new Subscriber<ItemEntity<List<Map>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ItemEntity<List<Map>> listCheckItemEntity) {
                        checkView.showCheckList(listCheckItemEntity.getRows());
                    }
                };
                checkModel.getCheckList(subscriber,"service.GzqdService","queryGzqdList","1","10");
            }

            @Override
            public void deleteCheckInfo(Map map) {
                Subscriber<ItemEntity<String>> subscriber=new Subscriber<ItemEntity<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ItemEntity<String> checkItemEntity) {
                        checkView.showDeletedInfo(checkItemEntity);
                    }
                };
                checkModel.postCheckInfo(subscriber,"service.GzqdService","saveChanges",buildInfo(map));
            }


            @Override
            public void onViewAttached() {
                checkView.changeButton(SharedPreferencesUtils.readBoolean("isCheckIn"));
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
                    result.put("updated", new ArrayList());
                    result.put("deleted", item);

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
