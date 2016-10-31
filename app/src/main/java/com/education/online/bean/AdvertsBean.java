package com.education.online.bean;

/**
 * Created by Administrator on 2016/10/31.
 */
public class AdvertsBean {

    private String advert_id=""; // 广告ID
    private String title=""; // 标题
    private String introduction=""; // 介绍
    private String img=""; // 图片地址
    private String advert_type=""; // 类型 0-课程，1-教师

    public String getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAdvert_type() {
        return advert_type;
    }

    public void setAdvert_type(String advert_type) {
        this.advert_type = advert_type;
    }
}
