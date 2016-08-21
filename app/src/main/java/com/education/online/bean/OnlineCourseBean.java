package com.education.online.bean;

/**
 * Created by Administrator on 2016/8/21.
 */

public class OnlineCourseBean {
    private String CourseName;
    private String StartTime;
    private String EndTime;
    private int ApplicantsNum;
    private double Price;

    public double getPrize() {
        return Price;
    }

    public String getCourseName() {
        return CourseName;
    }

    public int getApplicantsNum() {
        return ApplicantsNum;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setApplicantsNum(int applicantsNum) {
        this.ApplicantsNum = applicantsNum;
    }

    public void setCourseName(String courseName) {
        this.CourseName = courseName;
    }

    public void setStartTime(String startTime) {
        this.StartTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.EndTime = endTime;
    }

    public void setPrize(double prize) {
        this.Price =Price;
    }
}
