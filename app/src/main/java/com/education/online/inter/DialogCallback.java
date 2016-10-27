package com.education.online.inter;

import android.support.v4.app.Fragment;

import com.education.online.bean.FilterInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22.
 */
public interface DialogCallback {
    void closeDialog();
    void onfinish(ArrayList<FilterInfo> filters);
    void onSelected(int pos);
}
