package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Great Gao on 2016/11/16.
 */
public class QuestionListHolder {
    private String total="0";
    private String page_total="0";
    private String current_page="0";
    private List<QuestionInfoBean> question_infos = new ArrayList();

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage_total() {
        return page_total;
    }

    public void setPage_total(String page_total) {
        this.page_total = page_total;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<QuestionInfoBean> getQuestion_infos() {
        return question_infos;
    }

    public void setQuestion_infos(List<QuestionInfoBean> question_infos) {
        this.question_infos = question_infos;
    }
}
