package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class AdvertsBean {

    private String advert_id=""; // 广告ID

    public String getIs_arrange() {
        return is_arrange;
    }

    public void setIs_arrange(String is_arrange) {
        this.is_arrange = is_arrange;
    }

    private String is_arrange = "";//是否需要排序
    private String title=""; // 标题
    private String introduction=""; // 介绍
    private String img=""; // 图片地址
    private String advert_type=""; // 类型 0-课程，1-教师
    private String details_count="";//关联的数据详情
    private List<CourseBean> courseInfos = new ArrayList<>();
    private List<TeacherBean> teachetInfos = new ArrayList<>();

    public List<CourseBean> getCourseInfos() {
        return courseInfos;
    }

    public void setCourseInfos(List<CourseBean> courseInfos) {
        this.courseInfos = courseInfos;
    }

    public List<TeacherBean> getTeachetInfos() {
        return teachetInfos;
    }

    public void setTeachetInfos(List<TeacherBean> teachetInfos) {
        this.teachetInfos = teachetInfos;
    }

    public String getRelation_ids() {
        return relation_ids;
    }

    public void setRelation_ids(String relation_ids) {
        this.relation_ids = relation_ids;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getDetails_count() {
        return details_count;
    }

    public void setDetails_count(String details_count) {
        this.details_count = details_count;
    }

    private String relation_ids="";//关联数据id
    private String course_type = "";//课程类型

    public String getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAdvert_type() {
        return advert_type;
    }

    public void setAdvert_type(String advert_type) {
        this.advert_type = advert_type;
    }
}
