package com.education.online.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Great Gao on 2016/10/24.
 */
public class CourseDetailBean implements Serializable{
    private String course_id = "";
    private String course_name = "";
    private String subject_name = "";
    private String img = "";
    private String original_price = "";
    private String price = "";
    private String follow = "";
    private String min_follow = "";
    private String max_follow = "";
    private String plan = "";
    private String refund = "";
    private String transfer = "";
    private String introduction = "";
    private String hot = "";
    private String course_type = "";
    private String usercode = "";
    private String is_collection = "";
    private String average="";

    private String is_buy = "";
    private CreatUserInfo user_info = new CreatUserInfo();
    private List<CourseExtm> course_extm = new ArrayList<>();
    private CourseEvaluate course_evaluate =new CourseEvaluate();

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public CourseEvaluate getCourse_evaluate() {
        return course_evaluate;
    }

    public void setCourse_evaluate(CourseEvaluate course_evaluate) {
        this.course_evaluate = course_evaluate;
    }

    public List<CourseExtm> getCourse_extm() {
        return course_extm;
    }

    public void setCourse_extm(List<CourseExtm> course_extm) {
        this.course_extm = course_extm;
    }

    public CreatUserInfo getUser_info() {
        return user_info;
    }

    public void setUser_info(CreatUserInfo user_info) {
        this.user_info = user_info;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getMax_follow() {
        return max_follow;
    }

    public void setMax_follow(String max_follow) {
        this.max_follow = max_follow;
    }

    public String getMin_follow() {
        return min_follow;
    }

    public void setMin_follow(String min_follow) {
        this.min_follow = min_follow;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
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

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
