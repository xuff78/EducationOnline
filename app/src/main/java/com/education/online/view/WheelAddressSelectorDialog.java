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


public class WheelAddressSelectorDialog extends Dialog implements View.OnClickListener{
	private WhellCallback cb;
	private Activity context;
	private WheelView datetime, hour, min, longtime;
	private ArrayList<String> dateArray=new ArrayList<>();
	private ArrayList<String> dateTxtArray=new ArrayList<>();
	private ArrayList<String> hourArray=new ArrayList<>();
	private ArrayList<String> minArray=new ArrayList<>();
	private ArrayList<String> longtimeArray=new ArrayList<>();
	private CourseTimeBean bean=null;
	private int padding=0;

	public WheelAddressSelectorDialog(Activity context, WhellCallback cb) {
		super(context, R.style.view_dialog);
		this.context=context;
		this.cb=cb;
		padding= ImageUtil.dip2px(context, 5);
	}

	public WheelAddressSelectorDialog(Activity context, WhellCallback cb, CourseTimeBean bean) {
		super(context, R.style.view_dialog);
		this.context=context;
		this.bean=bean;
		this.cb=cb;
		padding= ImageUtil.dip2px(context, 5);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.wheel_once_timeset);

		initView();
	}

	private void initView() {
		 Window window = getWindow();
		 window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
		 window.setWindowAnimations(R.style.dialog_popfrombottom);  //添加动画
		 window.setLayout(ScreenUtil.getWidth(context), WindowManager.LayoutParams.WRAP_CONTENT);

		datetime = (WheelView)findViewById(R.id.date);
		hour= (WheelView)findViewById(R.id.hour);
		min= (WheelView)findViewById(R.id.min);
		longtime = (WheelView)findViewById(R.id.longtime);

		Format dateData=new SimpleDateFormat("yyyy-MM-dd");
		Format dateShow=new SimpleDateFormat("MM月dd日");
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (int i=1;i<31;i++){
			cal.add(Calendar.DATE, 1);
			dateArray.add(dateData.format(cal.getTime()));
			dateTxtArray.add(dateShow.format(cal.getTime())+" "+ ActUtil.getWeekDay(cal.get(Calendar.DAY_OF_WEEK)));
		}
		datetime.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
		datetime.setWheelSize(5);
		datetime.setSelection(2);
		datetime.setSkin(WheelView.Skin.Holo);
		WheelView.WheelViewStyle style = datetime.getStyle();
		style.selectedTextSize=15;
		style.textSize=14;
		style.holoBorderColor=context.getResources().getColor(R.color.verylight_gray);
		datetime.setStyle(style);
		datetime.setWheelData(dateTxtArray);

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
//设置初始值
		if(bean!=null){
			for(int i=0;i<dateArray.size();i++){
				if(dateArray.get(i).equals(bean.getDatetime())) {
					datetime.setSelection(i);
					break;
				}
			}
			for(int i=0;i<hourArray.size();i++){
				if(hourArray.get(i).equals(bean.getHour())) {
					hour.setSelection(i);
					break;
				}
			}
			for(int i=0;i<minArray.size();i++){
				if(minArray.get(i).equals(bean.getMin())) {
					min.setSelection(i);
					break;
				}
			}
			for(int i=0;i<longtimeArray.size();i++){
				if(longtimeArray.get(i).equals(bean.getLongtime())) {
					longtime.setSelection(i);
					break;
				}
			}
		}
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cancelBtn) {
			dismiss();
		} else if (id == R.id.confirmBtn) {
			CourseTimeBean ct=new CourseTimeBean();
			ct.setDatetime(dateArray.get(datetime.getCurrentPosition()));
			ct.setHour(hourArray.get(hour.getCurrentPosition()));
			ct.setMin(minArray.get(min.getCurrentPosition()));
			ct.setLongtime(longtimeArray.get(longtime.getCurrentPosition()));
			if(bean==null)
				cb.onFinish(ct);
			else
				cb.onChanged(ct);
			dismiss();
		}
	}

}
