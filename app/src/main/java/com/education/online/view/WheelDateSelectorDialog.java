package com.education.online.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.bean.CourseTimeBean;
import com.education.online.inter.WheelDateSelecterdCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class WheelDateSelectorDialog extends Dialog implements View.OnClickListener{
	private WheelDateSelecterdCallback cb;
	private Activity context;
	private WheelView startdatetime, enddatetime;
	private ArrayList<String> dateArray=new ArrayList<>();
	private ArrayList<String> dateTxtArray=new ArrayList<>();
	private CourseTimeBean bean=null;
	private int padding=0;
	private boolean flag = false;

	public WheelDateSelectorDialog(Activity context, WheelDateSelecterdCallback  cb) {
		super(context, R.style.view_dialog);
		this.context=context;
		this.cb=cb;
		padding= ImageUtil.dip2px(context, 5);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.wheel_course_startend_date);

		initView();
	}

	private void initView() {
		Window window = getWindow();
		window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.dialog_popfrombottom);  //添加动画
		window.setLayout(ScreenUtil.getWidth(context), WindowManager.LayoutParams.WRAP_CONTENT);

		startdatetime = (WheelView)findViewById(R.id.startdate);
		enddatetime = (WheelView) findViewById(R.id.enddate);


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
		startdatetime.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
		startdatetime.setWheelSize(5);
		startdatetime.setSelection(2);
		startdatetime.setSkin(WheelView.Skin.Holo);
		WheelView.WheelViewStyle style = startdatetime.getStyle();
		style.selectedTextSize=15;
		style.textSize=14;
		style.holoBorderColor=context.getResources().getColor(R.color.verylight_gray);
		startdatetime.setStyle(style);
		startdatetime.setWheelData(dateTxtArray);

		enddatetime.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
		enddatetime.setWheelSize(5);
		enddatetime.setSelection(2);
		enddatetime.setSkin(WheelView.Skin.Holo);
		WheelView.WheelViewStyle style2 = enddatetime.getStyle();
		style2.selectedTextSize=15;
		style2.textSize=14;
		style2.holoBorderColor=context.getResources().getColor(R.color.verylight_gray);
		enddatetime.setStyle(style);
		enddatetime.setWheelData(dateTxtArray);


		findViewById(R.id.cancelBtn).setOnClickListener(this);
		findViewById(R.id.confirmBtn).setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cancelBtn) {
			dismiss();
		} else if (id == R.id.confirmBtn) {

			if(enddatetime.getCurrentPosition()< startdatetime.getCurrentPosition()) {
				flag = false;//开课日期早于结课日期
				Toast.makeText(context, "结束日期早于开始日期！", Toast.LENGTH_SHORT).show();
			}
			else {
				flag = true;
				cb.ReturnStartEndDate(startdatetime.getCurrentPosition(), enddatetime.getCurrentPosition(), flag);
				dismiss();
			}
		}
	}
}
