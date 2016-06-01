package com.example.xqw.checkinapplication.main.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.BaseActivity;
import com.example.xqw.checkinapplication.base.Common;
import com.example.xqw.checkinapplication.main.check.view.CheckInEditFragment;
import com.example.xqw.checkinapplication.main.check.view.CheckOutEditFragment;
import com.example.xqw.checkinapplication.main.check.view.CheckUpdateFragment;
import com.example.xqw.checkinapplication.main.log.view.LogEditFragment;
import com.example.xqw.checkinapplication.main.notice.view.NoticeInfoFragment;
import com.example.xqw.checkinapplication.main.rest.view.RestEditFragment;
import com.example.xqw.checkinapplication.main.setting.PasswordFragment;

/**
 * Created by xqw on 2016/4/21.
 */
public class ContainerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setStatusBarColor(R.color.colorPrimaryDark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int tag=getIntent().getIntExtra("tag",0);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        switch (tag){
            case Common.PASSWORD_SETTING:
                fragmentTransaction.replace(R.id.container,new PasswordFragment(),"password_setting");
                fragmentTransaction.commit();
                break;
            case Common.NOTICE_INFO:
                fragmentTransaction.replace(R.id.container,new NoticeInfoFragment(),"notice_info");
                fragmentTransaction.commit();
                break;
            case Common.CHECK_IN_EDIT:
                fragmentTransaction.replace(R.id.container,new CheckInEditFragment(),"check_in_edit");
                fragmentTransaction.commit();
                break;
            case Common.LOG_EDIT:
                fragmentTransaction.replace(R.id.container,new LogEditFragment(),"log_edit");
                fragmentTransaction.commit();
                break;
            case Common.REST_EDIT:
                fragmentTransaction.replace(R.id.container,new RestEditFragment(),"rest_edit");
                fragmentTransaction.commit();
                break;
            case Common.CHECK_OUT_EDIT:
                fragmentTransaction.replace(R.id.container,new CheckOutEditFragment(),"check_out_edit");
                fragmentTransaction.commit();
                break;
            case Common.CHECK_UPDATE:
                fragmentTransaction.replace(R.id.container,new CheckUpdateFragment(),"check_update");
                fragmentTransaction.commit();
                break;
        }
    }
}
