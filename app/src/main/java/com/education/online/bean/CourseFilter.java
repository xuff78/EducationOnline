package com.education.online.bean;

/**
 * Created by Administrator on 2016/11/3.
 */
public class CourseFilter {

    private String status="underway"; // 否 课程状态 （over-已完结 underway-未完结）  无
    private String course_type="live"; // 否 课程类型（courseware-课件 video-视频 live-直播）  无
    private String subject_id=null; // 否 科目ID  无
    private String key_word=null; // 否 搜索关键字  无
    private String is_free=null; // 否 是否免费 yes-是 no-不是  无
    private String usercode=null; // 否 教师用户代码，教师查看自己的课程可以看到所有状态的课程，查看其他人只能看到审核通过的  无
    private String sort=null; // 否 排序（hot-热度 course_id-课程ID sort_order-教师设置排序值正序 evaluate-评价 price-价格）  无
    private String start_date=null; // 否 开始日期 YYYY-MM-DD ,开课时间的筛选只有直播课程才有  无
    private String end_date=null; // 否 结束日期 YYYY-MM-DD ,开课时间的筛选只有直播课程才有  无
    private String query_type="course"; // 否 查询类别 teacher-教师 course-课程  course
    private String is_tc_validate=null; // 否 知否只显示通过教师认证的 yes-是 no-否  无
    private String is_specialty_validate=null; // 否 是否只显示通过专业资质认证的 yes-是 no-否  无
    private String work_time=null; // 否 工作时间下限，工作时间上限 work_time=1,5 无
    private String gender=null; // 否 性别 male-男 female-女  无
    private String course_validate_status="pass"; // 否 课程审核状态，不传取所有 pass-审核通过 waitOrRefuse-待审核与审核未通过  无
    private String page_size="20"; // 否 页容量  10
    private String page="1"; // 否 页码

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getQuery_type() {
        return query_type;
    }

    public void setQuery_type(String query_type) {
        this.query_type = query_type;
    }

    public String getIs_tc_validate() {
        return is_tc_validate;
    }

    public void setIs_tc_validate(String is_tc_validate) {
        this.is_tc_validate = is_tc_validate;
    }

    public String getIs_specialty_validate() {
        return is_specialty_validate;
    }

    public void setIs_specialty_validate(String is_specialty_validate) {
        this.is_specialty_validate = is_specialty_validate;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse_validate_status() {
        return course_validate_status;
    }

    public void setCourse_validate_status(String course_validate_status) {
        this.course_validate_status = course_validate_status;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
