package com.education.online.act.teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.CourseTimeBean;
import com.education.online.inter.WheelTimeSelectorCallback;
import com.education.online.inter.weekdayDialogCallback;
import com.education.online.util.ActUtil;
import com.education.online.view.SelectWeekdayDialog;
import com.education.online.inter.WheelDateSelecterdCallback;
import com.education.online.view.WheelDateSelectorDialog;
import com.education.online.view.WheelTimeSelectorDialog;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/7.
 */

public class AddSerialClass extends BaseFrameAct implements View.OnClickListener,Serializable{
    Activity activity;

    private RelativeLayout courseFrequency,courseDuration, courseTime;
    private ArrayList<CourseTimeBean> courseList = new ArrayList<>();
    private ArrayList<String > weekdaylist = new ArrayList<>();
    private int startdatetime, enddatetime;
    private String hour, min, longtime;
    private ArrayList<CourseTimeBean> timelist = new ArrayList<>();
    private boolean isDateSet,isWeekdaySet,isTimeSet;
    private CourseTimeBean tempBean;
    private Intent intent = new Intent();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_serial_class);
        _setHeaderTitle("添加多次课");
        _setLeftBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDateSet && isTimeSet && isWeekdaySet){
                    setResult(0x11,intent);
                    finish();//全都设置了就回传数据结束应用
                }
                else{
                   //弹出对话框
                    new AlertDialog.Builder(AddSerialClass.this,AlertDialog.THEME_HOLO_LIGHT)
                            .setTitle("信息不全是否仍然退出？" ).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            AddSerialClass.this.finish();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();

                    Toast.makeText(AddSerialClass.this, "信息不全", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //_setLeftBackListener();
        initi();
    }


    private void initi() {
        courseFrequency = (RelativeLayout) findViewById(R.id.courseFrequency);
        courseFrequency.setOnClickListener(this);
        courseDuration = (RelativeLayout) findViewById(R.id.courseDuration);
        courseDuration.setOnClickListener(this);
        courseTime = (RelativeLayout) findViewById(R.id.courseTime);
        courseTime.setOnClickListener(this);

        Format dateData=new SimpleDateFormat("yyyy-MM-dd");
        Format dateShow=new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//获取当前时间
        int length = enddatetime- startdatetime+1;//计算上结束当天
        for (int i = 0; i<length;i++){
            cal.add(Calendar.DATE, 1);
            for (String s : weekdaylist){
                if (s == ActUtil.getWeekDay(cal.get(Calendar.DAY_OF_WEEK))){
                    tempBean.setDatetime(dateShow.format(cal.getTime())+" "+ ActUtil.getWeekDay(cal.get(Calendar.DAY_OF_WEEK)));
                    tempBean.setMin(min);
                    tempBean.setHour(hour);
                    tempBean.setLongtime(longtime);
                    timelist.add(tempBean);
                    tempBean=null;
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("TimeList",(Serializable) timelist);
        intent.putExtras(bundle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.courseFrequency:
                new SelectWeekdayDialog(AddSerialClass.this, new weekdayDialogCallback() {
                    @Override
                    public void StringBack(ArrayList<String> objects,boolean flag) {
                        weekdaylist.addAll(objects);
                        isWeekdaySet = flag;
                    }
                }).show();
                break;
            case R.id.courseDuration:
                new WheelDateSelectorDialog(AddSerialClass.this, new WheelDateSelecterdCallback() {
                    @Override
                    public void ReturnStartEndDate(int s1, int s2,boolean flag) {
                        startdatetime = s1;
                        enddatetime = s2;
                        isDateSet = flag;
                    }
                }).show();


                break;
            case R.id.courseTime:
                new WheelTimeSelectorDialog(AddSerialClass.this, new WheelTimeSelectorCallback() {
                    @Override
                    public void ReturnTime(String s1, String s2, String s3,boolean flag) {
                         hour = s1;
                        min = s2;
                        longtime = s3;
                        isTimeSet = flag;

                    }
                }).show();

                break;
        }
    }
}