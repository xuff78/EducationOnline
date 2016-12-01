package com.education.online.act.teacher;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.SearchResultAct;
import com.education.online.act.login.SubjectSelector;
import com.education.online.adapter.MainAdapter;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.TeacherBean;
import com.education.online.fragment.dialog.SelectorPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

/**
 * Created by 可爱的蘑菇 on 2016/8/26.
 */
public class TeacherInfoEdit extends BaseFrameAct implements View.OnClickListener{

    private String[] degree={"高中","中专","大专","本科","硕士","博士","博士后"};
    private EditText teachingTime, highSchool, companyName, introduce, descTxt, experienceTxt, labelTxt, professionEdt;
    private TextView teachSubjectTxt, educationTxt, teacherName;
    private HttpHandler handler;
    private SubjectBean subject=new SubjectBean();
    private TeacherBean teacher;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getUserInfo)){
                    teacher= JSON.parseObject(jsonData, TeacherBean.class);
                    subject.setSubject_name(teacher.getSubject());
                    subject.setSubject_id(teacher.getSubject_id());
                    setFormData();
                }else if(method.equals(Method.updateTeacher)){
                    SharedPreferencesUtil.setString(TeacherInfoEdit.this, Constant.SubjectName, teachSubjectTxt.getText().toString());
                    SharedPreferencesUtil.setString(TeacherInfoEdit.this, Constant.SubjectId, subject.getSubject_id());
                    DialogUtil.showInfoDialog(TeacherInfoEdit.this, "提示", "修改成功", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onBackPressed();
                        }
                    });
                }
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
        _setRightHomeText("完成", this);
        String usercode= SharedPreferencesUtil.getUsercode(this);
        handler.getUserInfo(usercode);
    }

    private void initView() {
        teacherName= (TextView) findViewById(R.id.teacherName);
        teachingTime= (EditText) findViewById(R.id.teachingTime);
        highSchool= (EditText) findViewById(R.id.highSchool);
        companyName= (EditText) findViewById(R.id.companyName);
        introduce= (EditText) findViewById(R.id.introduce);
        descTxt= (EditText) findViewById(R.id.descTxt);
        experienceTxt= (EditText) findViewById(R.id.experienceTxt);
        labelTxt= (EditText) findViewById(R.id.labelTxt);
        professionEdt= (EditText) findViewById(R.id.professionEdt);

        teachSubjectTxt= (TextView) findViewById(R.id.teachSubjectTxt);
        educationTxt= (TextView) findViewById(R.id.educationTxt);

        findViewById(R.id.subjectLayout).setOnClickListener(this);
        findViewById(R.id.educationLayout).setOnClickListener(this);

    }

    private void setFormData() {
        if(teacher.getName().length()>0)
            teacherName.setText(teacher.getName());
        teachingTime.setText(teacher.getWork_time());
        highSchool.setText(teacher.getSchool());
        companyName.setText(teacher.getUnit());
        introduce.setText(teacher.getAbout_teacher());
        descTxt.setText(teacher.getIntroduction());
        experienceTxt.setText(teacher.getExperience());
        labelTxt.setText(teacher.getTags());
        professionEdt.setText(teacher.getSpecialty());

        teachSubjectTxt.setText(teacher.getSubject());
        educationTxt.setText(teacher.getEdu_bg());
        subject.setSubject_id(teacher.getSubject_id());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.subjectLayout:
                Intent i = new Intent(TeacherInfoEdit.this, SubjectSelector.class);
                i.putExtra(Constant.SearchSubject, subject.getSubject_id());
                i.putExtra(Constant.SearchWords, subject.getSubject_name());
                startActivityForResult(i, 0x10);
                break;
            case R.id.educationLayout:
                DialogUtil.showSelectDialog(TeacherInfoEdit.this, "学历", degree, new Dialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        educationTxt.setText(degree[i]);
                    }
                });
                break;
            case R.id.right_text:
                String subjectTxt=teachSubjectTxt.getText().toString().trim();
                if(subjectTxt.length()==0)
                    ToastUtils.displayTextShort(TeacherInfoEdit.this, "请填写学科");
                handler.updateTeacher(subject.getSubject_id(), teachingTime.getText().toString(), professionEdt.getText().toString(),
                        educationTxt.getText().toString(), highSchool.getText().toString(), companyName.getText().toString(),
                        introduce.getText().toString(), descTxt.getText().toString(), experienceTxt.getText().toString(),
                        labelTxt.getText().toString());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x11){
            subject= (SubjectBean) data.getSerializableExtra(SubjectBean.Name);
            teachSubjectTxt.setText(subject.getSubject_name());
        }
    }
}
