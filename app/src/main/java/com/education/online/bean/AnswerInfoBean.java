package com.education.online.bean;

/**
 * Created by Great Gao on 2016/11/16.
 */
public class AnswerInfoBean {
    private String answer_id = "";
    private String introduction = "";
    private String created_at = "";
    private String img = "";
    private String is_correct = "";
    private String user_name = "";
    private String avatar = "";
    private String usercode = "";
    private String user_identity = "";// 1-学生 2-老师


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(String is_correct) {
        this.is_correct = is_correct;
    }

    public String getUser_identity() {
        return user_identity;
    }

    public void setUser_identity(String user_identity) {
        this.user_identity = user_identity;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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




}
