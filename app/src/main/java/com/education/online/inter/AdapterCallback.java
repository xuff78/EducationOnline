package com.education.online.inter;

import android.view.View;

/**
 * Created by Administrator on 2016/8/30.
 */
public interface AdapterCallback {
    void onClick(View v, int i);
    void additem();
    void delitem(View v, int i);
}
