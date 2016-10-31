package com.education.online.bean;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ImageInfo {

    private String pic_type = ""; //图片类型 1:身份证正面2:身份证反面 3:教师证4学历证5专业资质6工作单位
    private String pic_urls = ""; // 图片地址,多张用逗号“，”分隔

    public String getPic_type() {
        return pic_type;
    }

    public void setPic_type(String pic_type) {
        this.pic_type = pic_type;
    }

    public String getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(String pic_urls) {
        this.pic_urls = pic_urls;
    }
}
