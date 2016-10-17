package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class HomePageInfo {

    public HomePageInfo() {}

    List<SubjectBean> subject_list=new ArrayList<>();

    public List<SubjectBean> getSubject_list() {
        return subject_list;
    }

    public void setSubject_list(List<SubjectBean> subject_list) {
        this.subject_list = subject_list;
    }
}
