package com.education.online.bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/8/30.
 */
public class VideoImgItem {

    private String imgUrl="";
    private String videoUrl="";
    private Bitmap bmp=null;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
