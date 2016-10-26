package com.education.online.bean;

/**
 * Created by Great Gao on 2016/10/24.
 */
public class EvaluateBean {
    public EvaluateBean() {
    }
    private String info = "";
    private String star = "";
    private String evaluate_date = "";
    private String user_name = "";
    private String avatar = "";
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
}
