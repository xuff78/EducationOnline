package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class HomePageInfo {

    public HomePageInfo() {}

    List<LiveCourse> live =new ArrayList<>();
    List<VideoCourse> video =new ArrayList<>();
    List<WareCourse> ware =new ArrayList<>();
    List<SubjectBean> subject_list=new ArrayList<>();

    public List<SubjectBean> getSubject_list() {
        return subject_list;
    }

    public void setSubject_list(List<SubjectBean> subject_list) {
        this.subject_list = subject_list;
    }

    public List<LiveCourse> getLive() {
        return live;
    }

    public void setLive(List<LiveCourse> live) {
        this.live = live;
    }

    public List<VideoCourse> getVideo() {
        return video;
    }

    public void setVideo(List<VideoCourse> video) {
        this.video = video;
    }

    public List<WareCourse> getWare() {
        return ware;
    }

    public void setWare(List<WareCourse> ware) {
        this.ware = ware;
    }
}
