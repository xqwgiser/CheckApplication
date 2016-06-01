package com.example.xqw.checkinapplication.main.check.presenter;

import android.content.Intent;

import com.example.xqw.checkinapplication.base.Presenter;

/**
 * Created by xqw on 2016/5/24.
 */
public interface ICheckUpdatePresenter extends Presenter {
    void setCheckType();
    void setCheckInfo(Intent intent);
    void updateCheckInfo(Intent intent);
}
