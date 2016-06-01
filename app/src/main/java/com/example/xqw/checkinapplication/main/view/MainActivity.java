package com.example.xqw.checkinapplication.main.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.BaseActivity;
import com.example.xqw.checkinapplication.main.check.view.CheckInFragment;
import com.example.xqw.checkinapplication.main.log.view.LogFragment;
import com.example.xqw.checkinapplication.main.notice.view.NoticeFragment;
import com.example.xqw.checkinapplication.main.rest.view.RestFragment;
import com.example.xqw.checkinapplication.main.setting.SettingFragment;
import com.gigamole.library.NavigationTabBar;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    FloatingActionMenu fabMenu;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor(R.color.colorPrimaryDark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        if (mViewPager != null) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_check),
                Color.parseColor(colors[0]), "签到"));
        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_log), Color.parseColor(colors[1]), "日志"));
        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_rest), Color.parseColor(colors[2]), "请假"));
        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_notice), Color.parseColor(colors[3]), "公告"));
        models.add(new NavigationTabBar.Model(
                getResources().getDrawable(R.drawable.ic_mine), Color.parseColor(colors[4]), "个人"));
        if (navigationTabBar != null) {
            navigationTabBar.setModels(models);
            navigationTabBar.setViewPager(mViewPager,0);
        }
        fabMenu = (FloatingActionMenu) findViewById(R.id.fab);
        if (fabMenu != null) {
            fabMenu.hideMenu(false);
            fabMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return id == R.id.action_close || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            Fragment fragment=null;
            switch (position) {
                case 0:
                    fragment = new CheckInFragment();
                    break;
                case 1:
                    fragment = new LogFragment();
                    break;
                case 2:
                    fragment = new RestFragment();
                    break;
                case 3:
                    fragment = new NoticeFragment();
                    break;
                case 4:
                    fragment = new SettingFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Snackbar.make(fabMenu, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else {
            super.onBackPressed();
        }
    }
}
