package com.education.online.bean;

/**
 * Created by Administrator on 2017/3/6.
 */

public class OrderPayInfo {
    private String order_number=""; // 订单号
    private String state=""; // 订单状态（0:已取消 1：待支付 2：完成 3：申诉 4：退款 5：待评价6:评价完成）
    private String price=""; // 订单价格
    private String course_name=""; // 课程名称
    private String img=""; // 课程简图
    private String user_name=""; // 课程创建人
    private String subject_name=""; // 科目名称
    private String course_type=""; // 课程类型(1：课件 2：视频 3：直播课)
    private String introduction=""; // 课程简介
    private String course_id=""; // 课程ID
    private String original_price=""; // 原价
    private String buy_time=""; // 购买时间
    private String course_count=""; // 购买课程时所余课节数
    private String usercode=""; // 1.查看人为老师，会看到购买人的usercode 2.查看人为学生，会返回课程创建人的usercode
    private String avatar="";

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
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

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public String getCourse_count() {
        return course_count;
    }

    public void setCourse_count(String course_count) {
        this.course_count = course_count;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
