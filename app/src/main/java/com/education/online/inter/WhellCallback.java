package com.education.online.inter;

import com.education.online.bean.CourseTimeBean;

/**
 * Created by Administrator on 2016/9/6.
 */
public interface WhellCallback {
    void onFinish(CourseTimeBean bean);
    void onChanged(CourseTimeBean bean);
}
