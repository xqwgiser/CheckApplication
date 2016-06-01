package com.example.xqw.checkinapplication.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xqw.checkinapplication.R;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqw on 2016/4/21.
 */
public class PasswordFragment extends Fragment {
    @Bind(R.id.password_old)
    ShowHidePasswordEditText passwordOld;
    @Bind(R.id.password_new)
    ShowHidePasswordEditText passwordNew;
    @Bind(R.id.password_confirm)
    ShowHidePasswordEditText passwordConfirm;
    @Bind(R.id.password_commit)
    Button passwordCommit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.password_commit)
    public void onClick() {
        //fragment跳转操作
    }
}
