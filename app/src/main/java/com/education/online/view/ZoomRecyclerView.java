package com.education.online.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.util.LogUtil;

/**
 * Created by 可爱的蘑菇 on 2016/12/22.
 */
public class ZoomRecyclerView extends RecyclerView{

    private enum STATUS {
        DragDown, AnimaBack, Scroll
    }
    private float preY;// 点击时y坐标
    private float totalOffsetY=0;
    private ImageView courseImg;
    private STATUS status = STATUS.Scroll;
    private int imghHeight=0;
    private int listScrollY=0;

    public ZoomRecyclerView(Context context) {
        super(context);
    }

    public ZoomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preY=e.getY();
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    /***
     * 触摸事件
     *
     * @param ev
     */
    public boolean onTouchEvent(MotionEvent ev) {
        if(status==STATUS.AnimaBack)
            return false;
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                // 手指松开.
                if (totalOffsetY!=0) {
                    status=STATUS.AnimaBack;
                    animation();
                }else {
                    status = STATUS.Scroll;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();// 时时y坐标
                int deltaY = (int) (preY - nowY);// 滑动距离
                LogUtil.i("test", "scroll move: "+deltaY);
                if(listScrollY!=0){
                    totalOffsetY=0;
                    preY=nowY;
                }else{
                    if(deltaY<0){
                        totalOffsetY=deltaY;
                        status=STATUS.DragDown;
                        setChildView((int) (totalOffsetY/3));
                        return true;
                    }
                    LogUtil.i("totp", "totalOffsetY: "+totalOffsetY+"   deltaY: "+deltaY);
                }

                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                listScrollY+=dy;
            }
        });
    }

    private void setChildView(int offsetY){
        if(getChildCount()>0){
            if(courseImg==null) {
                View childView=getChildAt(0);
                courseImg = (ImageView) childView.findViewById(R.id.courseImg);
                imghHeight=courseImg.getHeight();
            }
            LinearLayout.LayoutParams rlp = (LinearLayout.LayoutParams) courseImg.getLayoutParams();
            rlp.height=imghHeight-offsetY;
            courseImg.setLayoutParams(rlp);
//          childView.requestLayout();
        }
    }

    /***
     * 回缩动画
     */
    public void animation() {
        // 开启移动动画
        ValueAnimator animator=new ValueAnimator().ofInt((int)totalOffsetY/3, 0).setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // TODO Auto-generated method stub
                int y = (Integer) valueAnimator.getAnimatedValue();
                setChildView(y);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub
                totalOffsetY=0;
                status=STATUS.Scroll;
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        animator.start();
    }
}
