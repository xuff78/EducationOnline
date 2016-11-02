package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class TeacherWithCourse {

    private String avatar=""; // 头像
    private String user_name=""; // 用户姓名
    private String usercode=""; // 用户代码
    private String average=""; // 评价平均分数
    private String evaluate_count=""; // 评价总人数
    private String about_teacher=""; // 教师介绍
    private String tags=""; // 标签
    private List<CourseBean> course_detail=new ArrayList<>();

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getEvaluate_count() {
        return evaluate_count;
    }

    public void setEvaluate_count(String evaluate_count) {
        this.evaluate_count = evaluate_count;
    }

    public String getAbout_teacher() {
        return about_teacher;
    }

    public void setAbout_teacher(String about_teacher) {
        this.about_teacher = about_teacher;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<CourseBean> getCourse_detail() {
        return course_detail;
    }

    public void setCourse_detail(List<CourseBean> course_detail) {
        this.course_detail = course_detail;
    }
}
