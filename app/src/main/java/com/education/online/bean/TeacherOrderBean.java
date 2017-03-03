package com.education.online.bean;

/**
 * Created by Administrator on 2017/3/3.
 */

public class TeacherOrderBean {

    private String course_id=""; // 课程ID
    private String course_name=""; // 课程名称
    private String follow=""; // 报名人数
    private String plan=""; // 教学计划
    private String paid_number=""; // 订单已支付人数
    private String unpaid_number=""; // 订单未支付人数

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

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPaid_number() {
        return paid_number;
    }

    public void setPaid_number(String paid_number) {
        this.paid_number = paid_number;
    }

    public String getUnpaid_number() {
        return unpaid_number;
    }

    public void setUnpaid_number(String unpaid_number) {
        this.unpaid_number = unpaid_number;
    }
}
