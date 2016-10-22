package com.education.online.bean;

import java.io.Serializable;

/**
 * Created by Great Gao on 2016/10/20.
 */
public class AddClassBean implements Serializable{
    public final static String Name ="AddClassBean";

    private String name = "";//课程名称
    private String course_type = ""; //课程类型
    private String subject_id = ""; //科目ID  无
    private String original_price = ""; //原价  无
    private String price = ""; //价格
    private String img = ""; //简图
    private String introduction = ""; // 课程介绍
    private String min_follow = ""; //最低开课人数
    private String max_follow = ""; // 班级最高人数
    private String course_url="";
    private String refund="always"; // 退款设置（always-随时退 non-不可退 defore-开课前一小时可退 after-开课十分钟后不可退）  无
    private String transfer="non"; // 插班类型（always-随时插班 non-不可插班）  无
    private String plan = ""; // 教学计划  无
    private String  courseware_time = ""; // 具体上课时间，多个时间用逗号','隔开  无
    private String  time_len ="";//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMin_follow() {
        return min_follow;
    }

    public void setMin_follow(String min_follow) {
        this.min_follow = min_follow;
    }

    public String getMax_follow() {
        return max_follow;
    }

    public void setMax_follow(String max_follow) {
        this.max_follow = max_follow;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getCourse_url() {
        return course_url;
    }

    public void setCourse_url(String course_url) {
        this.course_url = course_url;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getCourseware_time() {
        return courseware_time;
    }

    public void setCourseware_time(String courseware_time) {
        this.courseware_time = courseware_time;
    }

    public String getTime_len() {
        return time_len;
    }

    public void setTime_len(String time_len) {
        this.time_len = time_len;
    }
}
