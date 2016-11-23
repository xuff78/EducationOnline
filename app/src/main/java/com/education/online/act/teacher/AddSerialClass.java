package com.education.online.act.teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.ArraryCourseTimeBean;
import com.education.online.bean.CourseTimeBean;
import com.education.online.inter.WheelTimeSelectorCallback;
import com.education.online.inter.weekdayDialogCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectWeekdayDialog;
import com.education.online.inter.WheelDateSelecterdCallback;
import com.education.online.view.WheelDateSelectorDialog;
import com.education.online.view.WheelTimeSelectorDialog;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/7.
 */

public class AddSerialClass extends BaseFrameAct implements View.OnClickListener {
    Activity activity;

    private RelativeLayout courseFrequency, courseDuration, courseTime;
    private TextView textfrequency, textstartendtime, textduration;
    private ArrayList<CourseTimeBean> courseList = new ArrayList<>();
    private ArrayList<String> weekdaylist = new ArrayList<>();
    private ArrayList<String> tempstringlist = new ArrayList<>();
    private int startdatetime, enddatetime;
    private String hour, min, longtime;
    private ArrayList<CourseTimeBean> timelist = new ArrayList<>();
    private boolean isDateSet, isWeekdaySet, isTimeSet;
    private CourseTimeBean tempBean;
    private Intent intent = new Intent();
    private SelectWeekdayDialog selectWeekdayDialog;
    private WheelTimeSelectorDialog wheelTimeSelectorDialog;
    private WheelDateSelectorDialog wheelDateSelectorDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_serial_class);
        _setHeaderTitle("添加多次课");
        _setRightHomeTextColor(getResources().getColor(R.color.normal_red));
        _setRightHomeText("完成", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDateSet && isTimeSet && isWeekdaySet) {
                    returnresult();
                   //全都设置了就回传数据结束应用
                } else {
                    Toast.makeText(AddSerialClass.this, "请填写完整信息！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //_setLeftBackListener();
        initi();
    }

    private void returnresult() {
        Calendar cal = Calendar.getInstance();
        Format dateData = new SimpleDateFormat("yyyy-MM-dd");
        Format dateShow = new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        cal.setTime(date);//获取当前时间
        cal.add(Calendar.DATE, startdatetime);
        int length = enddatetime - startdatetime + 1;
        String stringtemp;//计算上结束当天
        for (int i = 0; i < length; i++) {
            cal.add(Calendar.DATE, 1);
            stringtemp = ActUtil.getWeekDay(cal.get(Calendar.DAY_OF_WEEK));
            for (String s : weekdaylist) {
                if (s == stringtemp) {
                    tempBean = new CourseTimeBean();
                    tempBean.setDatetime(dateData.format(cal.getTime()));
                    tempBean.setMin(min);
                    tempBean.setHour(hour);
                    tempBean.setLongtime(longtime);
                    timelist.add(tempBean);
                }
            }
        }
        if(timelist.size()>0) {
            Bundle bundle = new Bundle();
            ArraryCourseTimeBean list = new ArraryCourseTimeBean();
            list.setTimelist(timelist);
            bundle.putSerializable("TimeListArray", list);
            intent.putExtras(bundle);
            setResult(0x11, intent);
            finish();
        }else
        {
            ToastUtils.displayTextShort(AddSerialClass.this,"当前时间选择内并无有效课程时间！");
        }
    }

    private void initi() {
        textfrequency = (TextView) findViewById(R.id.textfrequency);
        textstartendtime = (TextView) findViewById(R.id.textstartendtime);
        textduration = (TextView) findViewById(R.id.textduration);
        courseFrequency = (RelativeLayout) findViewById(R.id.courseFrequency);
        courseFrequency.setOnClickListener(this);
        courseDuration = (RelativeLayout) findViewById(R.id.courseDuration);
        courseDuration.setOnClickListener(this);
        courseTime = (RelativeLayout) findViewById(R.id.courseTime);
        courseTime.setOnClickListener(this);
        tempstringlist.add("周一");
        tempstringlist.add("周二");
        tempstringlist.add("周三");
        tempstringlist.add("周四");
        tempstringlist.add("周五");
        tempstringlist.add("周六");
        tempstringlist.add("周日");


        selectWeekdayDialog = new SelectWeekdayDialog(AddSerialClass.this, new weekdayDialogCallback() {
            @Override
            public void StringBack(ArrayList<String> objects, boolean flag) {
                weekdaylist=objects;
                isWeekdaySet = flag;
            }
        });

        wheelTimeSelectorDialog = new WheelTimeSelectorDialog(AddSerialClass.this, new WheelTimeSelectorCallback() {
            @Override
            public void ReturnTime(String s1, String s2, String s3, boolean flag) {
                hour = s1;
                min = s2;
                longtime = s3;
                isTimeSet = flag;

            }
        });
        wheelDateSelectorDialog = new WheelDateSelectorDialog(AddSerialClass.this, new WheelDateSelecterdCallback() {
            @Override
            public void ReturnStartEndDate(int s1, int s2, boolean flag) {
                startdatetime = s1;
                enddatetime = s2;
                isDateSet = flag;
            }
        });


        selectWeekdayDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (isWeekdaySet) {
                    ArrayList<String> weekdaylisttemp = new ArrayList<>();
                    weekdaylisttemp.addAll(tempstringlist);
                    weekdaylisttemp.retainAll(weekdaylist);
                    if (weekdaylisttemp.size() > 0) {
                        String temp = "每周";
                        for (int i = 0; i < weekdaylisttemp.size(); i++) {
                            if (i == 0) {
                                temp = temp + weekdaylisttemp.get(i);
                            } else {
                                temp = temp + " 、" + weekdaylisttemp.get(i);
                            }
                        }
                        textfrequency.setText(temp + "上课");
                    }
                }
            }
        });
        wheelDateSelectorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (isDateSet) {
                    Format dateShow = new SimpleDateFormat("MM月dd日");
                    Calendar cal = Calendar.getInstance();
                    Date date = new Date(System.currentTimeMillis());
                    cal.setTime(date);//获取当前时间
                    cal.add(Calendar.DATE, startdatetime + 1);
                    String starttemp = dateShow.format(cal.getTime()) + " " + ActUtil.getWeekDay(cal.get(Calendar.DAY_OF_WEEK));
                    cal.add(Calendar.DATE, enddatetime - startdatetime);
                    String endtemp = dateShow.format(cal.getTime()) + " " + ActUtil.getWeekDay(cal.get(Calendar.DAY_OF_WEEK));
                    textstartendtime.setText(starttemp + " 开始——" + endtemp + " 结束");
                }
            }
        });

        wheelTimeSelectorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(isTimeSet) {
                    textduration.setText("课程由" + hour + "时" + min + "分开始，时长" + longtime + "小时");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.courseFrequency:
                selectWeekdayDialog.show();
                break;
            case R.id.courseDuration:
                wheelDateSelectorDialog.show();
                break;
            case R.id.courseTime:
                wheelTimeSelectorDialog.show();

                break;
        }
    }
}