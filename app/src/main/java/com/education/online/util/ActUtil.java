package com.education.online.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ActUtil {

    public static void closeSoftPan(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void initData(Context con) {
//        SharedPreferencesUtil.setString(con, Constant.Url_API,
//                "http://182.48.115.38:8088/platform/api");
//        SharedPreferencesUtil.setString(con, Constant.Product_code,
//                "1100");
//        SharedPreferencesUtil.setString(con, Constant.App_Channel,
//                "10002");
//        SharedPreferencesUtil.setString(con, Constant.App_Key,
//                "600025");
        SharedPreferencesUtil.setString(con, Constant.WXAppId,
                "wxc1fdc3e0a84ff533");
        SharedPreferencesUtil.setString(con, Constant.WXAppSecret,
                "94a1f4de4f5011e6b0ee525400b263eb");
        SharedPreferencesUtil.setString(con, Constant.App_Secret,
                "9af39ddG3jfld3B5J0mE8jd6hHi3k8al");
        SharedPreferencesUtil.setString(con, Constant.Pic_Savepath,
                Constant.SavePath);

    }

    public static String getDate() {
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        long todayL=mCalendar.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(todayL);//获取当
        String dateTxt = formatter.format(curDate);
        LogUtil.i("Date", "today date: "+dateTxt);
        return dateTxt;
    }

    public static String getChangedDate(int type, int num) {
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.add(type, num);
        long todayL=mCalendar.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(todayL);//获取当
        String dateTxt = formatter.format(curDate);
        LogUtil.i("Date", "change date: "+dateTxt);
        return dateTxt;
    }

    public static String getChangedDateByMonthDay(int type, int num) {
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.add(type, num);
        long todayL=mCalendar.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        Date curDate = new Date(todayL);//获取当
        String dateTxt = formatter.format(curDate);
        LogUtil.i("Date", "change date: "+dateTxt);
        return dateTxt;
    }

    public static String getWeekDay(int i) {
        String weekDay="";
        switch (i){
            case 1:
                weekDay="周日";
                break;
            case 2:
                weekDay="周一";
                break;
            case 3:
                weekDay="周二";
                break;
            case 4:
                weekDay="周三";
                break;
            case 5:
                weekDay="周四";
                break;
            case 6:
                weekDay="周五";
                break;
            case 7:
                weekDay="周六";
                break;
        }
        return weekDay;
    }

    public static String getPrice(String price) {
        return "￥"+price;
    }

    public static String getCourseStatusTxt(String status) {
        String courseStatusTxt="";
        if(status!=null&&status.length()>0){
            switch (Integer.valueOf(status)){
                case 0:
                    courseStatusTxt="待审核";
                    break;
                case 1:
                    courseStatusTxt="通过";
                    break;
                case 2:
                    courseStatusTxt="拒绝";
                    break;
            }
        }
        return courseStatusTxt;
    }

    public static String getCourseTypeTxt(String course_type, TextView courseType) {
        String txt="";
        if(course_type!=null&&course_type.length()>0){
            switch (Integer.valueOf(course_type)) {
                case 1:
                    txt = "课件";
                    break;
                case 2:
                    txt = "视频";
                    break;
                case 3:
                    txt = "直播课";
                    break;
            }
            courseType.setText(txt);
        }else
            courseType.setVisibility(View.GONE);
        return txt;
    }
}
