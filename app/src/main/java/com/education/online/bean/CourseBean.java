package com.education.online.bean;

/**
 * Created by Administrator on 2016/10/24.
 */
public class CourseBean {

    public CourseBean() {
    }

    private String course_id = ""; // 课程ID
    private String course_name = ""; // 课程名称
    private String subject_name = ""; // 科目名称
    private String img = ""; // 简图
    private String original_price = ""; // 原价
    private String price = ""; // 实际售价
    private String follow = ""; // 报名人数
    private String plan = ""; // 教学计划
    private String user_name = ""; // 创建人姓名

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
