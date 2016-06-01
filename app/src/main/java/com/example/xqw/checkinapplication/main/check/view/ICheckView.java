package com.example.xqw.checkinapplication.main.check.view;

import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.zhy.base.adapter.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by xqw on 2016/5/3.
 */
public interface ICheckView {
    void showCheckList(List<Map> list);
    void changeButton(boolean isCheck);
    Map saveCheckInfo(ViewHolder viewHolder);
    void showDeletedInfo(ItemEntity<String> itemEntity);
}
