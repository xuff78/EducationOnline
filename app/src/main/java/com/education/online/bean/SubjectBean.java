package com.education.online.bean;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/10/16.
 */
public class SubjectBean {

    private String subject_name = ""; //科目名称
    private String ssubject_id = ""; // 科目ID
    private ArrayList<SubjectBean> subList=new ArrayList<>();

    public SubjectBean(){}

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSsubject_id() {
        return ssubject_id;
    }

    public void setSsubject_id(String ssubject_id) {
        this.ssubject_id = ssubject_id;
    }

    public ArrayList<SubjectBean> getSubList() {
        return subList;
    }

    public void setSubList(ArrayList<SubjectBean> subList) {
        this.subList = subList;
    }
}
