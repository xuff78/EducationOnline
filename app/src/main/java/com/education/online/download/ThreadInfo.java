package com.education.online.download;

import java.io.Serializable;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public class ThreadInfo implements Serializable {
    private int complete; //0未完成， 1已完成
    private String url;
    private int start;
    private int end;//文件长度
    private int finished;//任务中断时的进度
    private String courseid;
    private String courseimg;
    private String coursename;
    private String subname;
    private String filetype;

    public ThreadInfo() {
        super();
    }

    public ThreadInfo(int complete, String url, int start, int end, int finished, String courseid, String courseimg) {
        this.complete = complete;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
        this.courseid = courseid;
        this.courseimg = courseimg;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCourseimg() {
        return courseimg;
    }

    public void setCourseimg(String courseimg) {
        this.courseimg = courseimg;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo [complete=" + complete + ", url=" + url + ", start=" + start
                + ", end=" + end + ", finished=" + finished + "]";
    }

}
