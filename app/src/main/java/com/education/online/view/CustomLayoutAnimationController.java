package com.education.online.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

/**
 * Created by 可爱的蘑菇 on 2016/12/5.
 */
public class CustomLayoutAnimationController extends LayoutAnimationController {

    // 7 just lucky number
    public static final int ORDER_CUSTOM  = 7;

    private Callback onIndexListener;

    public void setOnIndexListener(Callback onIndexListener) {
        this.onIndexListener = onIndexListener;
    }

    public CustomLayoutAnimationController(Animation anim) {
        super(anim);
    }

    public CustomLayoutAnimationController(Animation anim, float delay) {
        super(anim, delay);
    }

    public CustomLayoutAnimationController(Context context, AttributeSet attrs) {
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

        public int onIndex(CustomLayoutAnimationController controller, int count, int index);
    }
}
