package com.education.online.inter;

import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;

/**
 * Created by Administrator on 2017/3/3.
 */

public class CourseUpdateListener implements UpCompleteListener, UpProgressListener {

    private int pos = -1;

    public CourseUpdateListener(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public void onComplete(boolean b, String s) {

    }

    @Override
    public void onRequestProgress(long l, long l1) {

    }
}
