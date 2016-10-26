package com.education.online.bean;

import java.util.List;

/**
 * Created by Great Gao on 2016/10/26.
 */
public class EvaluateListBean {

    private String average = "";
    private String total = "";
    private String pagetotal = "";
    private String current_page = "";
    private List<EvaluateBean> evaluateList;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPagetotal() {
        return pagetotal;
    }

    public void setPagetotal(String pagetotal) {
        this.pagetotal = pagetotal;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<EvaluateBean> getEvaluateList() {
        return evaluateList;
    }

    public void setEvaluateList(List<EvaluateBean> evaluateList) {
        this.evaluateList = evaluateList;
    }

    public String getAverage() {
        return average;

    }

    public void setAverage(String average) {
        this.average = average;
    }
}
