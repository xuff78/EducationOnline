package com.wingsofts.zoomimageheader;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.wingsofts.zoomimageheader.support.ZoomOutPageTransformer;

/**
 * Created by 可爱的蘑菇 on 2017/1/8.
 */
public class ZoomViewPager extends ViewPager {

    public boolean canScroll = true;
    public ZoomViewPager(Context context) {
        super(context);
    }

    public ZoomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return
                canScroll &&
                        super.onTouchEvent(event);
    }
}
