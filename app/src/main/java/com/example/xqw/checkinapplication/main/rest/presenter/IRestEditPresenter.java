package com.example.xqw.checkinapplication.main.rest.presenter;

import android.content.Intent;

import com.example.xqw.checkinapplication.base.Presenter;

/**
 * Created by xqw on 2016/5/26.
 */
public interface IRestEditPresenter extends Presenter {
    void postRestInfo(Intent intent);
    void setRestInfo(Intent intent);
}
