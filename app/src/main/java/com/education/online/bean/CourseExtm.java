package com.education.online.bean;

/**
 * Created by Great Gao on 2016/10/24.
 */
public class CourseExtm {
    public CourseExtm() {
    }
    private String courseware_date = "";
    private String time_len = "";
    private String url = "";
    private String state = "";
    private String name ="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseware_date() {
        return courseware_date;
    }

    public void setCourseware_date(String courseware_date) {
        this.courseware_date = courseware_date;
    }

    public String getTime_len() {
        return time_len;
    }

    public void setTime_len(String time_len) {
        this.time_len = time_len;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
