package com.education.online.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11.
 */
public class OrderDetailBean implements Serializable{

    private String order_number=""; // 订单号
    private String order_price=""; // 实付金额
    private String pay_type=""; // 支付方式（1：微信 2：支付宝 3：积分）
    private String remarks=""; // 备注
    private String buy_time=""; // 购买时间
    private String state=""; // 状态（1：待支付 2：完成 3：申诉 4：退款 5：待评价）
    private String appeal_info=""; // 申诉内容
    private String appeal_time=""; // 申诉时间
    private String course_id=""; // 课程ID
    private String course_name=""; // 课程名称
    private String subject_name=""; // 科目名称
    private String img=""; // 课程简图
    private String price=""; // 实际售价
    private String follow=""; // 报名人数
    private String min_follow=""; // 最低开课人数
    private String max_follow=""; // 最多报名人数
    private String plan=""; // 教学计划
    private String refund=""; // 退款类型（1：随时退 2：不可退 3：开课前一小时可退 4：开课十分钟后不可退）
    private String transfer=""; // 插班类型（1：随时插班 2：不可插班）
    private String introduction=""; // 课程介绍
    private String hot=""; // 热度
    private String course_type=""; // 类型(1：课件 2：视频 3：直播课)
    private String usercode=""; // 创建人数字账号
    private String is_collection=""; // 是否已收藏改课程
    private String user_name="";

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAppeal_info() {
        return appeal_info;
    }

    public void setAppeal_info(String appeal_info) {
        this.appeal_info = appeal_info;
    }

    public String getAppeal_time() {
        return appeal_time;
    }

    public void setAppeal_time(String appeal_time) {
        this.appeal_time = appeal_time;
    }

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

    public String getMin_follow() {
        return min_follow;
    }

    public void setMin_follow(String min_follow) {
        this.min_follow = min_follow;
    }

    public String getMax_follow() {
        return max_follow;
    }

    public void setMax_follow(String max_follow) {
        this.max_follow = max_follow;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }
}
