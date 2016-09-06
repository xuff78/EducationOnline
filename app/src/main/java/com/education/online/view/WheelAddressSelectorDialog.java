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
import com.education.online.inter.WhellCallback;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.wheel.ArrayWheelAdapter;
import com.education.online.view.wheel.OnWheelScrollListener;
import com.education.online.view.wheel.WheelView;


public class WheelAddressSelectorDialog extends Dialog implements View.OnClickListener{
	
	private WhellCallback cb;
	private Activity context;
	private WheelView date, hour, min, longtime;

	private ValueAdapter dateAdapter, hourAdapter, minAdapter, longtimeAdapter;
	

	private int padding=0;

	public WheelAddressSelectorDialog(Activity context, WhellCallback cb) {
		super(context, R.style.view_dialog);
		this.context=context;
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

		date = (WheelView)findViewById(R.id.date);
		hour= (WheelView)findViewById(R.id.hour);
		min= (WheelView)findViewById(R.id.min);
		longtime = (WheelView)findViewById(R.id.longtime);

		String[] test={"1234j32j3","a234sfesds","213424","jfsldffds","323482fhs"};
		dateAdapter = new ValueAdapter(getContext(), test, 0);
		dateAdapter.setTextSize(16);
		date.setViewAdapter(dateAdapter);
		date.setCurrentItem(0);
		date.addScrollingListener(privinceScrollListener);

		String[] test2={"11","21","42","45","77"};
		hourAdapter = new ValueAdapter(getContext(), test2, 0);
		hourAdapter.setTextSize(16);
		hour.setViewAdapter(hourAdapter);
		hour.setCurrentItem(0);
		hour.addScrollingListener(cityScrollListener);

		minAdapter = new ValueAdapter(getContext(), test2, 0);
		minAdapter.setTextSize(16);
		min.setViewAdapter(minAdapter);
		min.setCurrentItem(0);

		String[] test4={"1sdf1","21fds","4as2","45dsg","732sd7"};
		longtimeAdapter = new ValueAdapter(getContext(), test4, 0);
		longtimeAdapter.setTextSize(16);
		longtime.setViewAdapter(longtimeAdapter);
		longtime.setCurrentItem(0);

		findViewById(R.id.cancelBtn).setOnClickListener(this);
		findViewById(R.id.confirmBtn).setOnClickListener(this);
	}
	
	OnWheelScrollListener privinceScrollListener = new OnWheelScrollListener() {
		
		@Override
		public void onScrollingStarted(WheelView wheel) {
		}
		
		@Override
		public void onScrollingFinished(WheelView wheel) {
			int currentItem = wheel.getCurrentItem();
		}
	};
	
	OnWheelScrollListener cityScrollListener = new OnWheelScrollListener() {
		
		@Override
		public void onScrollingStarted(WheelView wheel) {
		}
		
		@Override
		public void onScrollingFinished(WheelView wheel) {
			int currentItem = wheel.getCurrentItem();
		}
	};
	
	
	public class ValueAdapter extends ArrayWheelAdapter<String> {
		private int currentValue;
		
		public ValueAdapter(Context context, String[] datalist, int current) {
			super(context, datalist);
			this.currentValue = current;
		}
		
		
		public void setCurrentValue(int value){
			this.currentValue = value;
		}
		
		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			view.setPadding(0, padding, 0, padding);
		}
		
		@Override
		public View getItem(int index, View convertView, ViewGroup parent) {
			return super.getItem(index, convertView, parent);
		}
		
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cancelBtn) {
			dismiss();
		} else if (id == R.id.confirmBtn) {
//			cb.selectotListener(province, city, area);
			dismiss();
		}
	}

}
