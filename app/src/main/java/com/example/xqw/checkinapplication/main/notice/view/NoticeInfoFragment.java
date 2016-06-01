package com.example.xqw.checkinapplication.main.notice.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xqw.checkinapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xqw on 2016/4/22.
 */
public class NoticeInfoFragment extends Fragment {
    @Bind(R.id.notice_info)
    TextView noticeInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //// TODO: 2016/5/30 创建事件，返回已读标志 
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
