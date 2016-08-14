package com.education.online.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by 可爱的蘑菇 on 2016/8/14.
 */
public class ScreenUtil {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static float SCALE;

    public static int getWidth(Context act){
        int width=SharedPreferencesUtil.getInt(act, "SCREEN_WIDTH", 400);
        return width;
    }

    public static int getHeight(Context act){
        int height=SharedPreferencesUtil.getInt(act, "SCREEN_HEIGHT", 800);
        return height;
    }

    /*
     * 打印屏幕宽高   
     */
    public static void logScreenSize(Activity act) {
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH=dm.widthPixels;
        SCREEN_HEIGHT=dm.heightPixels;
        SharedPreferencesUtil.setInt(act, "SCREEN_WIDTH", SCREEN_WIDTH);
        SharedPreferencesUtil.setInt(act, "SCREEN_HEIGHT", SCREEN_HEIGHT);
    }
}
