package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class TeacherBean {

    public TeacherBean() {
    }

    private String usercode = "";  //用户代码
    private String user_type = "";  // 1-学生 2-教师
    private String name = "";  // 姓名
    private String gender = "";  // 性别 1-男 2-女
    private String avatar = "";  // 头像地址
    private String work_time = "";  // 工作时间
    private String specialty = "";
    private String subject_id="";
    private String subject = "";  // 主营科目
    private String edu_bg = "";  // 学历
    private String school = "";  // 院校
    private String unit = "";  // 单位
    private String about_teacher = "";  // 老师介绍
    private String introduction = "";  // 简介
    private String experience = "";  // 经历
    private String tags = "";  // 标签
    private String message="";
    private String attention_count = "";  // 粉丝数量
    private String student_count = "";  // 学生数量
    private String good_evaluate_ratio = "";  // 好评率（自行转成百分数）
    private String is_validate="";
    private String is_ext_validate=""; // 用户资料验证 0-待审核,1-通过,2-拒绝,3-未提交
    private String is_id_validate=""; // 身份证验证0-待审核,1-通过,2-拒绝,3-未提交
    private String is_tc_validate=""; // 教师证验证0-待审核,1-通过,2-拒绝,3-未提交
    private String is_edu_bg_validate=""; // 学历验证0-待审核,1-通过,2-拒绝,3-未提交
    private String is_specialty_validate=""; // 是否专业资质验证0-待审核,1-通过,2-拒绝,3-未提交
    private String is_unit_validate=""; // 是否单位验证0-待审核,1-通过,2-拒绝,3-未提交

    private ArrayList<CourseBean> course_info=new ArrayList<>();

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getIs_ext_validate() {
        return is_ext_validate;
    }

    public void setIs_ext_validate(String is_ext_validate) {
        this.is_ext_validate = is_ext_validate;
    }

    public String getIs_id_validate() {
        return is_id_validate;
    }

    public void setIs_id_validate(String is_id_validate) {
        this.is_id_validate = is_id_validate;
    }

    public String getIs_tc_validate() {
        return is_tc_validate;
    }

    public void setIs_tc_validate(String is_tc_validate) {
        this.is_tc_validate = is_tc_validate;
    }

    public String getIs_edu_bg_validate() {
        return is_edu_bg_validate;
    }

    public void setIs_edu_bg_validate(String is_edu_bg_validate) {
        this.is_edu_bg_validate = is_edu_bg_validate;
    }

    public String getIs_specialty_validate() {
        return is_specialty_validate;
    }

    public void setIs_specialty_validate(String is_specialty_validate) {
        this.is_specialty_validate = is_specialty_validate;
    }

    public String getIs_unit_validate() {
        return is_unit_validate;
    }

    public void setIs_unit_validate(String is_unit_validate) {
        this.is_unit_validate = is_unit_validate;
    }

    public ArrayList<CourseBean> getCourse_info() {
        return course_info;
    }

    public void setCourse_info(ArrayList<CourseBean> course_info) {
        this.course_info = course_info;
    }

    public String getIs_validate() {
        return is_validate;
    }

    public void setIs_validate(String is_validate) {
        this.is_validate = is_validate;
    }

    public String getAttention_count() {
        return attention_count;
    }

    public void setAttention_count(String attention_count) {
        this.attention_count = attention_count;
    }

    public String getStudent_count() {
        return student_count;
    }

    public void setStudent_count(String student_count) {
        this.student_count = student_count;
    }

    public String getGood_evaluate_ratio() {
        return good_evaluate_ratio;
    }

    public void setGood_evaluate_ratio(String good_evaluate_ratio) {
        this.good_evaluate_ratio = good_evaluate_ratio;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEdu_bg() {
        return edu_bg;
    }

    public void setEdu_bg(String edu_bg) {
        this.edu_bg = edu_bg;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAbout_teacher() {
        return about_teacher;
    }

    public void setAbout_teacher(String about_teacher) {
        this.about_teacher = about_teacher;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
