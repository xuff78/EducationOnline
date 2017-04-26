package com.education.online.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/24.
 */
public class CourseBean implements Serializable,Comparable<CourseBean>{

    public CourseBean() {
    }

    private String course_id = ""; // 课程ID
    private String course_name = ""; // 课程名称
    private String subject_name = ""; // 科目名称
    private String img = ""; // 简图
    private String original_price = ""; // 原价
    private String price = ""; // 实际售价
    private String follow = ""; // 报名人数
    private String plan = ""; // 教学计划
    private String user_name = ""; // 创建人姓名
    private String count="";
    private String course_type="";
    private String status="";
    private String course_count="";
    private String state ="";
    private int sort_order=0;
    private String courseware_date="";
    private String courseware_end_date="";
    private String has_course_during_halfhour="";
    private String is_buy="";
    private String usercode="";

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public String getCourseware_end_date() {
        return courseware_end_date;
    }

    public void setCourseware_end_date(String courseware_end_date) {
        this.courseware_end_date = courseware_end_date;
    }

    public String getHas_course_during_halfhour() {
        return has_course_during_halfhour;
    }

    public void setHas_course_during_halfhour(String has_course_during_halfhour) {
        this.has_course_during_halfhour = has_course_during_halfhour;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        conversationId = conversationId;
    }

    private String conversationId="";

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    private String introduction ="";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCourseware_date() {
        return courseware_date;
    }

    public void setCourseware_date(String courseware_date) {
        this.courseware_date = courseware_date;
    }

    public String getCourse_count() {
        return course_count;
    }

    public void setCourse_count(String course_count) {
        this.course_count = course_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
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

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public int compareTo(CourseBean another) {
        int course_a = Integer.parseInt(follow);
        int course_b= Integer.parseInt(another.follow);
            if(course_b<course_a)
            {
                return 1;
            }
            else if(course_b > course_a)
            {
                return -1;
            }
            else
            {
                return 0;
            }
    }
}
