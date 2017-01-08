package com.wingsofts.zoomimageheader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by 可爱的蘑菇 on 2017/1/7.
 */
public class DragHeaderLayout extends LinearLayout {

    private int dragToListDis=0; //dialog转换成list移动距离
    private int listStartY=0;  //list层初始位置
    private int width=0, height=0, top=0; //滑动的view距离顶部初始位置
    private int topHeadHeight, topHeadWidth;
    private ScrollView listLayout;
    private boolean init=false;
    private float lastMoveY=0;
    private float currentY=0;
    private float expandScaleX,expandScaleY, expandImageScaleY;
    private ImageView scaleImg;
    private float mTouchSlop;
    private ViewPager viewpager;
    private View scaleLayout, contentLayout, topInfoLayout;
    private int imgHeight;

    public DragHeaderLayout(Context context) {
        super(context);
    }

    public DragHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置页面信息
    public void setListLayoutInfo(ScrollView listLayout, int topHeadHeight, int topHeadWidth, int imgHeight, DragViewCallback callback){
        this.listLayout=listLayout;
        this.topHeadHeight=topHeadHeight;
        this.topHeadWidth=topHeadWidth;
        this.imgHeight=imgHeight;
        this.callback=callback;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(!init) {
            topInfoLayout=findViewById(R.id.topInfoLayout);
            scaleLayout=findViewById(R.id.scaleLayout);
            contentLayout=findViewById(R.id.contentLayout);
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            viewpager=(ViewPager)findViewById(R.id.viewpager);
            scaleImg= (ImageView) findViewById(R.id.scaleImg);
            width = viewpager.getMeasuredWidth();
            height = viewpager.getMeasuredHeight();
            top = getTop()+viewpager.getTop();
            dragToListDis = top + height - topHeadHeight;

            expandImageScaleY=(imgHeight-scaleImg.getHeight())/(float)scaleImg.getHeight();
            expandScaleY=(topHeadHeight-height)/(float)height;
            expandScaleX=(topHeadWidth-width)/(float)width;
            Log.i("DragInfo", "width: " + width + "  height: " + height + "  top: " + top + "  dragToListDis: " + dragToListDis);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastMoveY= event.getRawY ();
                return true;
            case MotionEvent.ACTION_MOVE:
                float moveY= event.getRawY ()-lastMoveY;
                lastMoveY= event.getRawY ();
                Log.i("DragInfo", "moveY: " + moveY);
                if(listLayout.getScrollY()!=0){
                    listLayout.scrollBy(0, -(int) moveY);
                }else if(!(currentY==-dragToListDis&&moveY<0)) {
                    if(moveY>0&&currentY>=0)
                        moveY=moveY/3;
                    currentY = currentY + moveY;
                    scaleHeadView(moveY);
                    if (currentY < -dragToListDis) {
                        currentY = -dragToListDis;
                    }
                    setTranslationY(currentY);
//                    Log.i("DragInfo", "currentY: " + currentY);
                    return true;
                }else{
                    listLayout.scrollBy(0, -(int) moveY);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastMoveY = (int) ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                if (Math.abs(moveY - lastMoveY) > mTouchSlop) {

                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void scaleHeadView(float moveY) {
        if(currentY<=-dragToListDis) {
            scaleImg.buildDrawingCache(true);
            scaleImg.buildDrawingCache();
            Bitmap bmpTmp=scaleImg.getDrawingCache();
            Bitmap bmp=Bitmap.createBitmap(bmpTmp);
            scaleImg.buildDrawingCache(false);
            if(callback!=null)
                callback.showTopImage(bmp);
            setAlpha(0);
        }else if(currentY<0){
            setAlpha(1);
            float scaleX=-currentY/dragToListDis*expandScaleX+1;
            float scaleY=-currentY/dragToListDis*expandScaleY+1;

            contentLayout.setTranslationX((scaleLayout.getWidth()-scaleX*scaleLayout.getWidth())/2);
            topInfoLayout.setTranslationY((scaleLayout.getHeight()-scaleY*scaleLayout.getHeight())+2);

            Log.i("DragInfo", "scaleX: " + scaleX);
            scaleLayout.setPivotY(scaleLayout.getHeight());
            scaleLayout.setScaleX(scaleX);
            scaleLayout.setScaleY(scaleY);

            float ImageScaleY=-currentY/dragToListDis*expandImageScaleY+1;
//            scaleImg.setPivotY((scaleImg.getHeight())/2);
            scaleImg.setScaleY(ImageScaleY);


        }else {
            scaleLayout.setScaleX(1);
            scaleLayout.setScaleY(1);
        }

    }

    private DragViewCallback callback;


    public interface DragViewCallback{
        void showTopImage(Bitmap bmp);
    }
}
