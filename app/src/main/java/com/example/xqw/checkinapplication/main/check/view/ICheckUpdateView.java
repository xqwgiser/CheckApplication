package com.example.xqw.checkinapplication.main.check.view;

import android.content.Intent;

import com.example.xqw.checkinapplication.bean.ItemEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by xqw on 2016/5/24.
 */
public interface ICheckUpdateView {
    void setCheckType(List<Map> list);
    void setCheckInfo(Intent intent);
    Map getCheckInfo(Intent intent);
    void showUpdateResult(ItemEntity<String> itemEntity);
}
