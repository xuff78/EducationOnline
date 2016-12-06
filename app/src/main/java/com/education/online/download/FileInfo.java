package com.education.online.download;

import java.io.Serializable;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public class FileInfo implements Serializable {

    private String url;
    private String fileName;
    private int length;
    private int finished;
    private String name="";
    private String courseid="";
    private String courseimg="";
    private int status=0; //0 没选择， 1已选择， 2下载中, 3已下载

    public FileInfo() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo [" + "url=" + url + ", fileName=" + fileName
                + ", length=" + length + ", finished=" + finished + "]";
    }

}
