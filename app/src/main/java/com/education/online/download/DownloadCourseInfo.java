package com.education.online.download;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/12/7.
 */
public class DownloadCourseInfo {
    private String courseId="";
    private String courseName="";
    private String courseImg="";
    private int fileCount=0;
    private List<ThreadInfo> courses=new ArrayList<>();

    public DownloadCourseInfo(String courseId, String courseName, String courseImg, int fileCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseImg = courseImg;
        this.fileCount = fileCount;
    }

    public List<ThreadInfo> getCourses() {
        return courses;
    }

    public void addCourse(ThreadInfo course) {
        this.courses.add(course);
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }
}
