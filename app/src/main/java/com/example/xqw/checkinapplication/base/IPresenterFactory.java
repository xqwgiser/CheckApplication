package com.example.xqw.checkinapplication.base;

/**
 * Created by xqw on 2016/5/3.
 */
public interface IPresenterFactory<T extends Presenter> {
    T create();
}
