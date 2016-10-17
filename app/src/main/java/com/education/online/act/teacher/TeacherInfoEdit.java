package com.education.online.act.teacher;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.MainAdapter;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.SharedPreferencesUtil;

import org.json.JSONException;

/**
 * Created by 可爱的蘑菇 on 2016/8/26.
 */
public class TeacherInfoEdit extends BaseFrameAct implements View.OnClickListener{

    private String[] degree={"高中","中专","大专","本科","硕士","博士","博士后"};
    private EditText teacherName, teachingTime, highSchool, companyName, introduce, descTxt, experienceTxt, labelTxt;
    private TextView teachSubjectTxt, educationTxt, professionTxt;
    private HttpHandler handler;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_base_info_edit);

        _setHeaderTitle("个人资料");
        initHandler();
        initView();
        String usercode= SharedPreferencesUtil.getUsercode(this);
        handler.getUserInfo(usercode);
    }

    private void initView() {
        teacherName= (EditText) findViewById(R.id.teacherName);
        teachingTime= (EditText) findViewById(R.id.teachingTime);
        highSchool= (EditText) findViewById(R.id.highSchool);
        companyName= (EditText) findViewById(R.id.companyName);
        introduce= (EditText) findViewById(R.id.introduce);
        descTxt= (EditText) findViewById(R.id.descTxt);
        experienceTxt= (EditText) findViewById(R.id.experienceTxt);
        labelTxt= (EditText) findViewById(R.id.labelTxt);

        teachSubjectTxt= (TextView) findViewById(R.id.teachSubjectTxt);
        educationTxt= (TextView) findViewById(R.id.educationTxt);
        professionTxt= (TextView) findViewById(R.id.professionTxt);

        findViewById(R.id.subjectLayout).setOnClickListener(this);
        findViewById(R.id.educationLayout).setOnClickListener(this);
        findViewById(R.id.professionLayout).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.subjectLayout:
                break;
            case R.id.educationLayout:
                break;
            case R.id.professionLayout:
                break;
        }
    }
}
