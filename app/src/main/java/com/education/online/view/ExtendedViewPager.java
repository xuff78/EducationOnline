package com.education.online.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


public class ExtendedViewPager extends AutoScrollViewPager {

	private float mDownX,mDownY;
	private boolean isAutoScroll=false;

	public ExtendedViewPager(Context context) {
	    super(context);
	}
	
	public ExtendedViewPager(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}
	
	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {

		return super.canScroll(v, checkV, dx, x, y);
	}

//	public void startAutoScroll(int interval) {
//		isAutoScroll = true;
//	}

	//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		switch (ev.getAction()){
//			case MotionEvent.ACTION_DOWN:
//				mDownX=ev.getX();
//				mDownY=ev.getY();
//				getParent().requestDisallowInterceptTouchEvent(true);
//				break;
//			case MotionEvent.ACTION_MOVE:
//				if(Math.abs(ev.getY()-mDownY)<Math.abs(ev.getX()-mDownX))
//					getParent().requestDisallowInterceptTouchEvent(true);
//				else
//					getParent().requestDisallowInterceptTouchEvent(false);
//				break;
//			case MotionEvent.ACTION_UP:
//			case MotionEvent.ACTION_CANCEL:
//				getParent().requestDisallowInterceptTouchEvent(false);
//				break;
//
//		}
//		return super.dispatchTouchEvent(ev);
//	}
}
