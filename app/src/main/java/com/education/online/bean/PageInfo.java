package com.education.online.bean;

/**
 * Created by Administrator on 2016/12/2.
 */
public class PageInfo {

    private int total=0; // 评价总数
    private int page_total=0; //总页数（按照每页10条计算的）
    private int current_page=0; // 当前页

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }
}
