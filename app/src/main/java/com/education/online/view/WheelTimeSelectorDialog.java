package com.education.online.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.CourseTimeBean;
import com.education.online.inter.WheelTimeSelectorCallback;
import com.education.online.inter.WhellCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.lang.reflect.Array;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class WheelTimeSelectorDialog extends Dialog implements View.OnClickListener{
    private Activity context;
    private WheelTimeSelectorCallback cb;
    private WheelView  hour, min, longtime;
    private ArrayList<String> hourArray=new ArrayList<>();
    private ArrayList<String> minArray=new ArrayList<>();
    private ArrayList<String> longtimeArray=new ArrayList<>();
    private int padding=0;
    private boolean flag = false;

    public WheelTimeSelectorDialog(Activity context, WheelTimeSelectorCallback cb) {
        super(context, R.style.view_dialog);
        this.context=context;
        this.cb=cb;
        padding= ImageUtil.dip2px(context, 5);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.wheel_course_starttime_duration);

        initView();
    }

    private void initView() {
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialog_popfrombottom);  //添加动画
        window.setLayout(ScreenUtil.getWidth(context), WindowManager.LayoutParams.WRAP_CONTENT);

        hour= (WheelView)findViewById(R.id.hour);
        min= (WheelView)findViewById(R.id.min);
        longtime = (WheelView)findViewById(R.id.longtime);

        Format dateData=new SimpleDateFormat("yyyy-MM-dd");
        Format dateShow=new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        WheelView.WheelViewStyle style = hour.getStyle();
        style.selectedTextSize=15;
        style.textSize=14;
        style.holoBorderColor=context.getResources().getColor(R.color.verylight_gray);
        for (int i=0;i<23;i++){
            hourArray.add(String.valueOf(i));
        }
        hour.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        hour.setWheelSize(5);
        hour.setSelection(2);
        hour.setSkin(WheelView.Skin.Holo);
        hour.setStyle(style);
        hour.setWheelData(hourArray);

        for (int i=0;i<60;i=i+5){
            minArray.add(String.valueOf(i));
        }
        min.setWheelAdapter(new ArrayWheelAdapter(getContext()));
        min.setWheelSize(5);
        min.setSelection(2);
        min.setSkin(WheelView.Skin.Holo);
        min.setStyle(style);
        min.setWheelData(minArray);

        longtimeArray.add("0.5");
        longtimeArray.add("1.0");
        longtimeArray.add("1.5");
        longtimeArray.add("2.0");
        longtime.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
        longtime.setWheelSize(5);
        longtime.setSelection(2);
        longtime.setSkin(WheelView.Skin.Holo);
        longtime.setStyle(style);
        longtime.setWheelData(longtimeArray);

        findViewById(R.id.cancelBtn).setOnClickListener(this);
        findViewById(R.id.confirmBtn).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancelBtn) {
            dismiss();
        } else if (id == R.id.confirmBtn) {
            flag = true;
            cb.ReturnTime(hourArray.get(hour.getCurrentPosition()),minArray.get(min.getCurrentPosition()),longtimeArray.get(longtime.getCurrentPosition()),flag);
            dismiss();
        }
    }

}
