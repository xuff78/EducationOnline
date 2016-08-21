package com.education.online.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/8/21.
 */
public class FilterAll implements Serializable{

    public static final String Name="FilterAll";

    private ArrayList<FilterInfo> list=new ArrayList<>();

    public ArrayList<FilterInfo> getList() {
        return list;
    }

    public void setList(ArrayList<FilterInfo> list) {
        this.list = list;
    }
}
