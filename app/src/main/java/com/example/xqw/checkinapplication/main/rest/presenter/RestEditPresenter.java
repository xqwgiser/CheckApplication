package com.example.xqw.checkinapplication.main.rest.presenter;

import android.content.Intent;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.rest.model.IRestModel;
import com.example.xqw.checkinapplication.main.rest.model.RestModel;
import com.example.xqw.checkinapplication.main.rest.view.IRestEditView;

import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/26.
 */
public class RestEditPresenter implements IPresenterFactory<IRestEditPresenter> {
    private IRestModel restModel;
    private IRestEditView restEditView;

    public RestEditPresenter(IRestEditView restEditView) {
        this.restEditView = restEditView;
        restModel=new RestModel();
    }

    @Override
    public IRestEditPresenter create() {
        return new IRestEditPresenter() {
            @Override
            public void postRestInfo(Intent intent) {
                Subscriber<ItemEntity<String>> subscriber=new Subscriber<ItemEntity<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ItemEntity<String> stringItemEntity) {
                        restEditView.showResult(stringItemEntity.getRows());
                    }
                };
                restModel.postRestInfo(subscriber,"service.QjsqService","saveChanges",
                        restModel.buildInfo(restEditView.getRestInfo(),intent.getBooleanExtra("isAdd",true)));
            }

            @Override
            public void setRestInfo(Intent intent) {
                if(!intent.getBooleanExtra("isAdd",true)){
                    restEditView.setRestInfo(intent);
                }
            }

            @Override
            public void onViewAttached() {
                Subscriber<List<Map>> subscriber=new Subscriber<List<Map>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Map> listItemEntity) {
                        restEditView.setRestType(listItemEntity);
                    }
                };
                restModel.getRestType(subscriber,"service.QjsqService","queryQjlx");
            }

            @Override
            public void onViewDetached() {

            }

            @Override
            public void onDestroyed() {

            }
        };
    }
}
