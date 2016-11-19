package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Great Gao on 2016/11/16.
 */
public class AnswerListHolder {
    private String total = "0";
    private String page_total = "0";
    private String current_page = "0";
    private List<AnswerInfoBean> answer_infos = new ArrayList();

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

    public List<AnswerInfoBean> getAnswer_infos() {
        return answer_infos;
    }

    public void setAnswer_infos(List<AnswerInfoBean> answer_infos) {
        this.answer_infos = answer_infos;
    }
}
