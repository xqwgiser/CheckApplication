package com.example.xqw.checkinapplication.main.check.view;

import com.example.xqw.checkinapplication.bean.ItemEntity;

/**
 * Created by xqw on 2016/5/20.
 */
public interface ICheckOutView {
    void setCheckInfo();
    void showResult(ItemEntity<String> itemEntity);
}
