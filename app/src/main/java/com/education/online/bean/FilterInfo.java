package com.education.online.bean;

import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/8/21.
 */
public class FilterInfo implements Serializable{

    private String typeName="";
    private String[] itemInfo;
    private int pos=0;
    private int selection=-1;
    private boolean singleChoice=false;

    public boolean isSingleChoice() {
        return singleChoice;
    }

    public void setSingleChoice(boolean singleChoice) {
        this.singleChoice = singleChoice;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String[] getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String[] itemInfo) {
        this.itemInfo = itemInfo;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
}
