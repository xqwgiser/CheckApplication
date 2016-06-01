package com.example.xqw.checkinapplication.main.check.presenter;

import com.example.xqw.checkinapplication.base.Presenter;

import java.util.Map;

/**
 * Created by xqw on 2016/5/3.
 */
public interface ICheckPresenter extends Presenter {
    void showCheckList();
    void deleteCheckInfo(Map map);
}
