package com.example.xqw.checkinapplication.main.log.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.main.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xqw on 2016/5/5.
 */
public class LogTabFragment extends Fragment {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.log_viewPager)
    ViewPager logViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_log_approval, container, false);
        ButterKnife.bind(this, view);
        List<String> titles=new ArrayList<>();
        titles.add("日志填写");
        titles.add("日志审批");
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new CommonLogFragment());
        fragments.add(new ApprovalLogFragment());
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getChildFragmentManager(),fragments,titles);
        logViewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(logViewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
