package com.education.online.bean;

import java.io.Serializable;

/**
 * Created by Great Gao on 2016/10/24.
 */
public class CreatUserInfo implements Serializable{
    public CreatUserInfo(){}
    private String user_name = "";
    private String introduction = "";
    private String avatar = "";
    private String evaluate_count = "";
    private String average = "5";

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEvaluate_count() {
        return evaluate_count;
    }

    public void setEvaluate_count(String evaluate_count) {
        this.evaluate_count = evaluate_count;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }
}
