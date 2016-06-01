package com.example.xqw.checkinapplication.main.rest.view;

import android.content.Intent;

import java.util.List;
import java.util.Map;

/**
 * Created by xqw on 2016/5/26.
 */
public interface IRestEditView {
    void setRestType(List<Map> list);
    Map getRestInfo();
    void setRestInfo(Intent intent);
    void showResult(String s);
}
