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
    private String reply_info="";
    private String evaluate_id="";

    //老师信息
    private String introduction="";
    private String img="";
    private String usercode="";

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

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getReply_info() {
        return reply_info;
    }

    public void setReply_info(String reply_info) {
        this.reply_info = reply_info;
    }

    public String getEvaluate_id() {
        return evaluate_id;
    }

    public void setEvaluate_id(String evaluate_id) {
        this.evaluate_id = evaluate_id;
    }

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
