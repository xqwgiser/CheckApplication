package com.example.xqw.checkinapplication.main.log.view;

import java.util.List;
import java.util.Map;

/**
 * Created by xqw on 2016/5/3.
 */
public interface ILogView {
    void showLogList(List<Map> list);
    void showDeleteResult(String result);
}
