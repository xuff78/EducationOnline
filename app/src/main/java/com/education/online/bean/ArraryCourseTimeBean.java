package com.education.online.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/10.
 */

public class ArraryCourseTimeBean implements Serializable {

    public final static String Name = "CourseTimeBean";
    private ArrayList<CourseTimeBean> timelist = new ArrayList<>();
    public void setTimelist(ArrayList<CourseTimeBean> timelist) {
        this.timelist = timelist;
    }
    public ArrayList<CourseTimeBean> getTimelist() {
        return timelist;
    }
}
