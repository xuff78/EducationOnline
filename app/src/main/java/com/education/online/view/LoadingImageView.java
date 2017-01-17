package com.education.online.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.education.online.R;

/**
 * Created by Administrator on 2017/1/17.
 */
public class LoadingImageView extends ImageView{

    private boolean showProgress=false;
    private RectF oval=new RectF();                     //RectF对象
    private Paint mPaint;
    private int current, total;

    public LoadingImageView(Context context) {
        super(context);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPaint = new Paint();
        mPaint.setColor(getContext().getResources().getColor(R.color.chat_common_blue));
        mPaint.setAntiAlias(true);      //设置画笔颜色
        mPaint.setStrokeWidth((float) 6.0);              //线宽
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(showProgress){
            oval.left=getWidth()/2-getHeight()/6;                              //左边
            oval.top=getHeight()/2-getHeight()/6;                                   //上边
            oval.right=getWidth()/2+getHeight()/6;                             //右边
            oval.bottom=getHeight()/2+getHeight()/6;
            canvas.drawArc(oval, 0, current/Float.valueOf(total)*360, false, mPaint);
        }
    }

    public void setProgress(int current, int total){
        if(current!=total) {
            showProgress=true;
            this.current = current;
            this.total = total;
        }else
            showProgress=false;
        invalidate();
    }
}
