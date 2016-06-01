package com.example.xqw.checkinapplication.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.Calendar;

/**
 * Created by xqw on 2016/3/21.
 */
public class Utils {
    /**
     * 显示snackBar
     *
     * @param rootView 依赖的view
     * @param textId   显示的内容
     */
    public static void showSnack(View rootView, int textId) {
        Snackbar.make(rootView, textId, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 较长时间显示snackBar
     *
     * @param rootView 依赖的view
     * @param textId   显示的内容
     */
    public static void showSnackLong(View rootView, int textId) {
        Snackbar.make(rootView, textId, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnack(View rootView, String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackLong(View rootView, String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_LONG).show();
    }

    public static String getSystemTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }

    public static String getHourMin() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour + ":" + minute;
    }

    public static String getYearMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day + " ";
    }
    public static long getTimeMillis(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}
