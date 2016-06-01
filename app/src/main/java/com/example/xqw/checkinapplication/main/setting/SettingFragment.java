package com.example.xqw.checkinapplication.main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.Common;
import com.example.xqw.checkinapplication.main.view.ContainerActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqw on 2016/4/18.
 */
public class SettingFragment extends Fragment {
    @Bind(R.id.password_setting)
    com.rey.material.widget.Button passwordSetting;
    @Bind(R.id.close_btn)
    com.rey.material.widget.Button closeBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.password_setting, R.id.close_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.password_setting:
                Intent intentPasswordSetting=new Intent(getActivity(),ContainerActivity.class);
                intentPasswordSetting.putExtra("tag",Common.PASSWORD_SETTING);
                startActivity(intentPasswordSetting);
                break;
            case R.id.close_btn:
                getActivity().finish();
                break;
        }
    }
}
