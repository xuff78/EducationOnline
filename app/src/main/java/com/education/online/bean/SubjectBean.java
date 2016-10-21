package com.education.online.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/10/16.
 */
public class SubjectBean implements Serializable{

    public static String Name="SubjectBean";
    private String subject_name = ""; //科目名称
    private String subject_id = ""; // 科目ID
    private boolean isSelected=false;
    private ArrayList<SubjectBean> child_subject=new ArrayList<>();
    private ArrayList<SubjectBean> child_subject_details=new ArrayList<>();

    public SubjectBean(){}

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public ArrayList<SubjectBean> getChild_subject() {
        return child_subject;
    }

    public void setChild_subject(ArrayList<SubjectBean> child_subject) {
        this.child_subject = child_subject;
    }

    public ArrayList<SubjectBean> getChild_subject_details() {
        return child_subject_details;
    }

    public void setChild_subject_details(ArrayList<SubjectBean> child_subject_details) {
        this.child_subject_details = child_subject_details;
    }
}
