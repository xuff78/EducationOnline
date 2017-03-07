package com.education.online.bean;

/**
 * Created by Administrator on 2017/3/2.
 */

public class SystemMessage {

    private String content=""; // 内容
    private String push_time=""; // 推送时间
    private int message_type=1; // 信息类型 1-课程提醒 2-广告
    private String related_id="";
    private String course_type="";

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getRelated_id() {
        return related_id;
    }

    public void setRelated_id(String related_id) {
        this.related_id = related_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }
}
