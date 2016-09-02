package com.education.online.act.teacher;

import android.os.Bundle;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/1.
 */
public class TeacherAuthPage extends BaseFrameAct {

    private TextView idStatus, teacherStatus, eduStatus, zyzzStatus, gzdwStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_auth);

        _setHeaderTitle("认证设置");

        initView();
    }

    private void initView() {
        idStatus=(TextView)findViewById(R.id.idStatus);
        teacherStatus=(TextView)findViewById(R.id.teacherStatus);
        eduStatus=(TextView)findViewById(R.id.eduStatus);
        zyzzStatus=(TextView)findViewById(R.id.zyzzStatus);
        gzdwStatus=(TextView)findViewById(R.id.gzdwStatus);
    }
}
