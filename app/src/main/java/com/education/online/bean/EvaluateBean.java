package com.education.online.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/26.
 */
public class EvaluateBean implements Serializable{

    public EvaluateBean() {
    }

    private String info=""; // 评价内容
    private String star=""; // 星级
    private String evaluate_date=""; // 评价日期
    private String user_name=""; // 用户名
    private String avatar=""; // 头像
    private String course_name=""; // 课程名称

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getEvaluate_date() {
        return evaluate_date;
    }

    public void setEvaluate_date(String evaluate_date) {
        this.evaluate_date = evaluate_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
