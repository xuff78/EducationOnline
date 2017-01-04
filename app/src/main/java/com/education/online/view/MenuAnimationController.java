package com.education.online.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

/**
 * Created by Administrator on 2017/1/4.
 */
public class MenuAnimationController extends LayoutAnimationController {

    // 7 just lucky number
    public static final int ORDER_CUSTOM  = 3;

    private Callback onIndexListener;

    public void setOnIndexListener(Callback onIndexListener) {
        this.onIndexListener = onIndexListener;
    }

    public MenuAnimationController(Animation anim) {
        super(anim);
    }

    public MenuAnimationController(Animation anim, float delay) {
        super(anim, delay);
    }

    public MenuAnimationController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * override method for custom play child view animation order
     */
    protected int getTransformedIndex(AnimationParameters params) {
        if(getOrder() == ORDER_CUSTOM &&onIndexListener != null) {
            return onIndexListener.onIndex(this, params.count, params.index);
        } else {
            return super.getTransformedIndex(params);
        }
    }

    /**
     * callback for get play animation order
     *
     */
    public static interface Callback{

        public int onIndex(MenuAnimationController controller, int count, int index);
    }
}