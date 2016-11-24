package com.education.online.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14.
 */
public class UserInfo implements Serializable{

    public UserInfo() {
    }

    private String name = ""; // 否 用户真实姓名 老张 无
    private String gender =""; // 否 性别 male-男 female-女 male 无
    private String avatar =""; //否 头像地址  无
    private String nickname =""; // 否 昵称  无
    private String birthday =""; // 否 生日
    private String phone="";
    private Double longitude=0d;
    private Double latitude=0d;
    private String user_identity="";
    private String usercode="";

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getUser_identity() {
        return user_identity;
    }

    public void setUser_identity(String user_identity) {
        this.user_identity = user_identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
