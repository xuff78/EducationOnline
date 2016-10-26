package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Great Gao on 2016/10/24.
 */
public class CourseEvaluate {
    public CourseEvaluate() {
    }

    private String total = "";
    private String page_total = "";
    private String current_page = "";


    private List<EvaluateBean> evaluate = new ArrayList<>();

    public List<EvaluateBean> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<EvaluateBean> evaluate) {
        this.evaluate = evaluate;
    }

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

}
