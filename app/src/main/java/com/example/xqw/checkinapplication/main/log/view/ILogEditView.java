package com.example.xqw.checkinapplication.main.log.view;

import android.content.Intent;

import com.example.xqw.checkinapplication.bean.ItemEntity;

import java.util.Map;

/**
 * Created by xqw on 2016/5/25.
 */
public interface ILogEditView {
    Map getLogInfo();
    void showResult(ItemEntity<String> itemEntity);
    void setLogInfo(Intent intent);
}
