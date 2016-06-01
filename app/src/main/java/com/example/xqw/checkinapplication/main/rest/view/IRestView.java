package com.example.xqw.checkinapplication.main.rest.view;

import java.util.List;
import java.util.Map;

/**
 * Created by xqw on 2016/5/26.
 */
public interface IRestView {
    void showRestList(List<Map> list);
    void showDeleteResult(String s);
}
