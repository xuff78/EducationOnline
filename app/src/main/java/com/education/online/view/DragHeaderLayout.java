package com.education.online.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.education.online.R;
import com.education.online.util.LogUtil;

/**
 * Created by 可爱的蘑菇 on 2017/1/7.
 */
public class DragHeaderLayout extends LinearLayout {

    private int dragToListDis=0; //dialog转换成list移动距离
    private int dragOutDis=300; //dialog划出屏幕
    private int listStartY=0;  //list层初始位置
    private int width=0, height=0, top=0; //滑动的view距离顶部初始位置
    private int topHeadHeight, topHeadWidth;
    private LScrollView listLayout;
    private boolean init=false;
    private float lastMoveY=0, pressDown=0;
    private float currentY=0;
    private float expandScaleX,expandScaleY, expandImageScaleY;
    private ImageView scaleImg;
    private float mTouchSlop;
    private ViewPager viewpager;
    private View scaleLayout, contentLayout, topInfoLayout, blankLayout;
    private int imgHeight;
    private boolean setDetailImg=false;
    private boolean clickOutside=false;
    private enum State { DIALOG, LIST, DRAG, AUTOSCROLL};
    private State viewtype=State.DIALOG;
    private GestureDetector detector;

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
    public void setListLayoutInfo(LScrollView listLayout, int listStartY, int topHeadHeight, int topHeadWidth, int imgHeight, DragViewCallback callback){
        this.listLayout=listLayout;
        this.topHeadHeight=topHeadHeight;
        this.topHeadWidth=topHeadWidth;
        this.imgHeight=imgHeight;
        this.callback=callback;
        this.listStartY=listStartY;
        detector=new GestureDetector(getContext(), onGestureListener);
        listLayout.setScrollViewListener(new LScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
                currentY=-dragToListDis-y;
                setTranslationY(currentY);
//                if(y==0) {
//                    setAlpha(1);
//                    DragHeaderLayout.this.callback.hideTopImage();
//                    LogUtil.i("Scroll", "从最大开始变小的时候");
//                }
//                LogUtil.i("Scroll", "scrollView Y:  "+y);
            }
        });
    }

    private GestureDetector.OnGestureListener onGestureListener=new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if(clickOutside&&viewtype==State.DIALOG)
                startTransYAnimation(currentY, dragOutDis);
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//            LogUtil.d("Srcoll", "onScrollY: " + v1);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//            Log.d("Srcoll", "onFlingY: " + v1);
            return false;
        }
    };

    View.OnClickListener listener=new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.blankLayout:
                    startTransYAnimation(currentY, dragOutDis);
                    break;
            }
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(!init) {
            blankLayout=findViewById(R.id.blankLayout);
            blankLayout.setOnClickListener(listener);
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
//            Log.i("DragInfo", "width: " + width + "  height: " + height + "  top: " + top + "  dragToListDis: " + dragToListDis);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastMoveY= event.getRawY ();
                clickOutside=true;   //点viewpager时候down传进去了，不会走这
//                if(viewtype==State.DIALOG)
                    return true;
//                break;
            case MotionEvent.ACTION_MOVE:
                if(clickOutside&&viewtype==State.DIALOG)
                    break;
//                currentY+=listLayout.getScrollY();
                LogUtil.d("Srcoll", "drag");
                viewtype=State.DRAG;
                float moveY= event.getRawY ()-lastMoveY;
                lastMoveY= event.getRawY ();
                float lastY = currentY;
                if(moveY>0&&currentY>=0)
                    moveY=moveY/3;
                currentY = currentY + moveY;
                scaleHeadView();
                if (lastY>-dragToListDis&&currentY <= -dragToListDis&&moveY<0) {  //从小变大重合的时候
                    blankLayout.setVisibility(View.GONE);
                    currentY=-dragToListDis;
                    listLayout.setTranslationY(0);
                    listLayout.setAlpha(1);
                    LogUtil.i("Scroll", "从小变大重合的时候");
                }else if(lastY<0&&currentY >= 0&&moveY>0){  //由大变成初始状态的时候
                    blankLayout.setVisibility(View.VISIBLE);
                    contentLayout.setTranslationX(0);
                    topInfoLayout.setTranslationY(0);
                    listLayout.setAlpha(0);
                    scaleLayout.setScaleX(1);
                    scaleLayout.setScaleY(1);
                    LogUtil.i("Scroll", "由大变成初始状态的时候");
                }else if(moveY>0&&-dragToListDis<=currentY&&lastY<=-dragToListDis){  //从最大开始变小的时候
                    setAlpha(1);
                    callback.hideTopImage();
                    LogUtil.i("Scroll", "从最大开始变小的时候");
                }

                setTranslationY(currentY);
                if(lastY<-dragToListDis||(lastY==-dragToListDis&&moveY<0))
                    listLayout.scrollBy(0, -(int) moveY);
                break;
            case MotionEvent.ACTION_UP:
                if(viewtype!=State.LIST){
                    if (currentY <= -dragToListDis / 2 && currentY > -dragToListDis) {
                        startTransYAnimation(currentY, -dragToListDis);
                    } else if (currentY > -dragToListDis / 2 && currentY < 0) {
                        startTransYAnimation(currentY, 0);
                    } else if (currentY < dragOutDis / 2 && currentY > 0) {
                        startTransYAnimation(currentY, 0);
                    } else if (currentY > dragOutDis / 2)
                        startTransYAnimation(currentY, dragOutDis);
                    else {
                        setState();
                    }
                }else
                    setState();
        }
        return super.onTouchEvent(event);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    pressDown = (int) ev.getRawY();
                    lastMoveY = pressDown;
                    clickOutside = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int) ev.getRawY();
                    if (Math.abs(moveY - pressDown) > mTouchSlop||viewtype!=State.DIALOG) {
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
                LogUtil.i("scroll", "show menu!!");
            }
            setAlpha(0);
        }else if(currentY<0){
            float scaleX=-currentY/dragToListDis*expandScaleX+1;
            float scaleY=-currentY/dragToListDis*expandScaleY+1;

            contentLayout.setTranslationX((scaleLayout.getWidth()-scaleX*scaleLayout.getWidth())/2);
            topInfoLayout.setTranslationY((scaleLayout.getHeight()-scaleY*scaleLayout.getHeight())+2);

            Log.i("DragInfo", "scaleX: " + scaleX+"    currentY: "+currentY);
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
        viewtype=State.AUTOSCROLL;
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
                setState();
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

    private void setState(){
        if(currentY<=-dragOutDis){
            viewtype=State.LIST;
        }else if(currentY==0){
            viewtype=State.DIALOG;
        }
    }

    private DragViewCallback callback;

    public interface DragViewCallback{
        void showTopImage(Bitmap bmp);
        void hideTopImage();
        void onFinish();
    }
}
