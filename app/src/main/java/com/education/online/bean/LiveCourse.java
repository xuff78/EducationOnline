package com.education.online.bean;

/**
 * Created by 可爱的蘑菇 on 2016/10/22.
 */
public class LiveCourse {

    public LiveCourse() {
    }

    private String state=""; // 状态 1-未开始 2-直播中
    private String courseware_date=""; // 课程开始时间
    private String course_img=""; // 课程图片
    private String course_name=""; // 课程名称
    private String price=""; // 价格
    private String follow=""; // 人数
    private String course_id=""; // 课程ID

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

    public String getCourse_img() {
        return course_img;
    }

    public void setCourse_img(String course_img) {
        this.course_img = course_img;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
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

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
