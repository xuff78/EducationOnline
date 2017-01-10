package com.education.online.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.education.online.R;

/**
 * Created by 可爱的蘑菇 on 2017/1/7.
 */
public class DragHeaderLayout extends LinearLayout {

    private int dragToListDis=0; //dialog转换成list移动距离
    private int dragOutDis=300; //dialog划出屏幕
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
    private boolean setDetailImg=false;

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
    public void setListLayoutInfo(ScrollView listLayout, int listStartY, int topHeadHeight, int topHeadWidth, int imgHeight, DragViewCallback callback){
        this.listLayout=listLayout;
        this.topHeadHeight=topHeadHeight;
        this.topHeadWidth=topHeadWidth;
        this.imgHeight=imgHeight;
        this.callback=callback;
        this.listStartY=listStartY;
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
//                Log.i("DragInfo", "moveY: " + moveY);
                if(listLayout.getScrollY()!=0){
                    listLayout.scrollBy(0, -(int) moveY);
                }else if(!(currentY==-dragToListDis&&moveY<0)) {
                    if(moveY>0&&currentY>=0)
                        moveY=moveY/3;
                    currentY = currentY + moveY;
                    scaleHeadView();
                    if (currentY <= -dragToListDis) {  //从小变大重合的时候
                        if(moveY<0) {
                            currentY = -dragToListDis;
                            listLayout.setTranslationY(0);
                            listLayout.setAlpha(1);
                        }
                    }else if(currentY >= 0&&moveY>0){  //由大变成初始状态的时候
                        contentLayout.setTranslationX(0);
                        topInfoLayout.setTranslationY(0);
                        listLayout.setAlpha(0);
                        scaleLayout.setScaleX(1);
                        scaleLayout.setScaleY(1);
                    }else if(moveY>0&&-dragToListDis==currentY-moveY){  //从最大开始变小的时候
                        setAlpha(1);
                        callback.hideTopImage();
                    }
                    setTranslationY(currentY);
//                    Log.i("DragInfo", "currentY: " + currentY);
                    return true;
                }else{
                    listLayout.scrollBy(0, -(int) moveY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(currentY<=-dragToListDis/2&&currentY>-dragToListDis){
                    startTransYAnimation(currentY, -dragToListDis);
                }else if(currentY>-dragToListDis/2&&currentY<0){
                    startTransYAnimation(currentY, 0);
                }else if(currentY<dragOutDis/2&&currentY>0){
                    startTransYAnimation(currentY, 0);
                }else if(currentY>dragOutDis/2)
                    startTransYAnimation(currentY, dragOutDis);
                return true;
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

    private void scaleHeadView() {
        if(currentY<=-dragToListDis) {
            if(callback!=null) {
                Bitmap bmp=null;
                if(!setDetailImg) {
                    scaleImg.buildDrawingCache(true);
                    scaleImg.buildDrawingCache();
                    Bitmap bmpTmp = scaleImg.getDrawingCache();
                    bmp = Bitmap.createBitmap(bmpTmp);
                    scaleImg.buildDrawingCache(false);
                    setDetailImg = true;
                }
                callback.showTopImage(bmp);
            }
            setAlpha(0);
        }else if(currentY<0){
            float scaleX=-currentY/dragToListDis*expandScaleX+1;
            float scaleY=-currentY/dragToListDis*expandScaleY+1;

            contentLayout.setTranslationX((scaleLayout.getWidth()-scaleX*scaleLayout.getWidth())/2);
            topInfoLayout.setTranslationY((scaleLayout.getHeight()-scaleY*scaleLayout.getHeight())+2);

            Log.i("DragInfo", "scaleX: " + scaleX);
            scaleLayout.setPivotY(scaleLayout.getHeight());
            scaleLayout.setScaleX(scaleX);
            scaleLayout.setScaleY(scaleY);

            float ImageScaleY=-currentY/dragToListDis*expandImageScaleY/2+1;
            scaleImg.setPivotY(0);
            scaleImg.setScaleY(ImageScaleY);

            listLayout.setAlpha(-currentY/dragToListDis);
            listLayout.setTranslationY(listStartY+currentY/dragToListDis*listStartY);
        }else if(currentY<dragOutDis){
            setAlpha(1-currentY/dragOutDis);
        }

    }

    public void startTransYAnimation(float start, float end) {
        // 开启移动动画
        ValueAnimator animator=new ValueAnimator().ofFloat(start, end).setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // TODO Auto-generated method stub
                currentY = (float) valueAnimator.getAnimatedValue();
                scaleHeadView();
                setTranslationY(currentY);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(currentY==dragOutDis){
                    callback.onFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private DragViewCallback callback;

    public interface DragViewCallback{
        void showTopImage(Bitmap bmp);
        void hideTopImage();
        void onFinish();
    }
}
