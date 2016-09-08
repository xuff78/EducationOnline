package com.education.online.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.inter.DialogCallback;
import com.education.online.inter.weekdayDialogCallback;
import com.education.online.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/27.
 */
public class SelectWeekdayDialog extends Dialog implements View.OnClickListener {
    private LinearLayout monlayout, tueslayout, wedneslayout, thurslayout, frilayout, satlayout, sunlayout;
    private ImageView monview, tuesview, wednesview, thursview, friview, satview, sunview;
    private TextView confirmtext, canceltext;
    private ArrayList<String> WeekdayList = new ArrayList<>();
    private boolean flag =false;


    private Activity act;
    private weekdayDialogCallback callback;

    public SelectWeekdayDialog(Activity act, weekdayDialogCallback callback) {
        super(act, R.style.view_dialog);
        this.act = act;
        this.callback=callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_weekday_selected);
        initView();

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialog_popfrombottom);  //添加动画
        window.setLayout(ScreenUtil.getWidth(act), -2);

    }

    /**
     * 初始化加载View
     */
    private void initView() {

        monlayout = (LinearLayout) findViewById(R.id.monlayout);
        monlayout.setOnClickListener(this);
        tueslayout = (LinearLayout) findViewById(R.id.tueslayout);
        tueslayout.setOnClickListener(this);
        wedneslayout = (LinearLayout) findViewById(R.id.wedneslayout);
        wedneslayout.setOnClickListener(this);
        thurslayout = (LinearLayout) findViewById(R.id.thurslayout);
        thurslayout.setOnClickListener(this);
        frilayout = (LinearLayout) findViewById(R.id.frilayout);
        frilayout.setOnClickListener(this);
        satlayout = (LinearLayout) findViewById(R.id.satlayout);
        satlayout.setOnClickListener(this);
        sunlayout = (LinearLayout) findViewById(R.id.sunlayout);
        sunlayout.setOnClickListener(this);
        monview = (ImageView) findViewById(R.id.monview);
        monview.setSelected(false);
        tuesview = (ImageView) findViewById(R.id.tuesview);
        tuesview.setSelected(false);
        wednesview = (ImageView) findViewById(R.id.wednesview);
        wednesview.setSelected(false);
        thursview = (ImageView) findViewById(R.id.thursview);
        thursview.setSelected(false);
        friview = (ImageView) findViewById(R.id.friview);
        friview.setSelected(false);
        satview = (ImageView) findViewById(R.id.satview);
        satview.setSelected(false);
        sunview = (ImageView) findViewById(R.id.sunview);
        sunview.setSelected(false);
        confirmtext = (TextView) findViewById(R.id.confirmtext);
        confirmtext.setOnClickListener(this);
        confirmtext.setTextColor(getContext().getResources().getColor(R.color.normal_red));
        canceltext = (TextView) findViewById(R.id.canceltext);
        canceltext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.monlayout) {
            if (monview.isSelected()) {
                monview.setSelected(false);
                WeekdayList.remove("星期一");
            } else {
                monview.setSelected(true);
                WeekdayList.add("星期一");
            }
        } else if (i == R.id.tueslayout) {
            if (tuesview.isSelected()) {
                tuesview.setSelected(false);
                WeekdayList.remove("星期二");
            } else {
                tuesview.setSelected(true);
                WeekdayList.add("星期二");
            }
        } else if (i == R.id.wedneslayout) {
            if (wednesview.isSelected()) {
                wednesview.setSelected(false);
                WeekdayList.remove("星期三");
            } else {
                wednesview.setSelected(true);
                WeekdayList.add("星期三");
            }
        } else if (i == R.id.thurslayout) {
            if (thursview.isSelected()) {
                thursview.setSelected(false);
                WeekdayList.remove("星期四");
            } else {
                thursview.setSelected(true);
                WeekdayList.add("星期四");
            }
        } else if (i == R.id.frilayout) {
            if (friview.isSelected()) {
                friview.setSelected(false);
                WeekdayList.remove("星期五");
            } else {
                friview.setSelected(true);
                WeekdayList.add("星期五");
            }
        } else if (i == R.id.satlayout) {
            if (satview.isSelected()){
                satview.setSelected(false);
                WeekdayList.remove("星期六");
            }else{
                satview.setSelected(true);
                WeekdayList.add("星期六");
            }
        } else if (i == R.id.sunlayout) {
            if (satview.isSelected()){
                satview.setSelected(false);
                WeekdayList.remove("星期日");
            }else{
                satview.setSelected(true);
                WeekdayList.add("星期日");
            }
        } else if (i == R.id.confirmtext) {
            if(!WeekdayList.isEmpty()){
                flag = true;
            }
            callback.StringBack(WeekdayList, flag);
            //回传数据给activity
            dismiss();
        } else if (i == R.id.canceltext) {
            dismiss();
        }
    }


}
