package com.example.xqw.checkinapplication.main.log.presenter;

import android.content.Intent;

import com.example.xqw.checkinapplication.base.Presenter;

/**
 * Created by xqw on 2016/5/25.
 */
public interface ILogEditPresenter extends Presenter {
    void postLogInfo(Intent intent);
    void setLogInfo(Intent intent);
}
