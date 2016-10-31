package com.education.online.act.teacher;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.upyun.UploadTask;
import com.education.online.bean.TeacherAuth;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;

import org.json.JSONException;

import java.io.File;

/**
 * Created by Administrator on 2016/9/1.
 */
public class TeacherAuthPage extends BaseFrameAct implements View.OnClickListener{

    private TextView idStatus, teacherStatus, eduStatus, zyzzStatus, gzdwStatus;
    private String updateStr="validate";
    private TeacherAuth status;
    private Dialog progressDialog;
    private String phoneTxtName = "";
    private String pic1="", pic2="", pic3="", pic4="", pic5="";
    private int clickViewId=-1;
    private HttpHandler mHandler;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                Intent intent=new Intent();
                intent.putExtra("ResId", clickViewId);
                intent.putExtra("jsonData", jsonData);
                switch (clickViewId){
                    case R.id.idStatus:
                        intent.setClass(TeacherAuthPage.this, TeacherAuthIdentity.class);
                        break;
                    case R.id.teacherStatus:
                    case R.id.eduStatus:
                    case R.id.zyzzStatus:
                    case R.id.gzdwStatus:
                        intent.setClass(TeacherAuthPage.this, TeacherAuthOthers.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_auth);

        _setHeaderTitle("认证设置");
        status= JSON.parseObject(getIntent().getStringExtra("jsonData"), TeacherAuth.class);
        initHandler();
        initView();
    }

    private void initView() {
        idStatus=(TextView)findViewById(R.id.idStatus);
        teacherStatus=(TextView)findViewById(R.id.teacherStatus);
        eduStatus=(TextView)findViewById(R.id.eduStatus);
        zyzzStatus=(TextView)findViewById(R.id.zyzzStatus);
        gzdwStatus=(TextView)findViewById(R.id.gzdwStatus);
        setInfo(idStatus, status.getIs_id_validate());
        setInfo(teacherStatus, status.getIs_tc_validate());
        setInfo(eduStatus, status.getIs_edu_bg_validate());
        setInfo(zyzzStatus, status.getIs_specialty_validate());
        setInfo(gzdwStatus, status.getIs_unit_validate());
    }

    private void setInfo(TextView txt, String status) {
        if(status.equals("3")){

        }else {
            txt.setTextColor(getResources().getColor(R.color.normal_red));
            txt.setBackgroundResource(R.drawable.shape_normalredline_with_corner);
            if (status.equals("0")) {
                txt.setText("待审核");
            }else if (status.equals("1")) {
                txt.setText("通过");
            }else if (status.equals("2")) {
                txt.setText("拒绝");
            }
        }
        if(!status.equals("1"))
            txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        clickViewId=view.getId();
        String validate_type="";
        switch (clickViewId){
            case R.id.idStatus:
                validate_type="id";
                break;
            case R.id.teacherStatus:
                validate_type="tc";
                break;
            case R.id.eduStatus:
                validate_type="edu_bg";
                break;
            case R.id.zyzzStatus:
                validate_type="specialty";
                break;
            case R.id.gzdwStatus:
                validate_type="unit";
                break;
        }
        mHandler.getValidateDetails(validate_type);
    }

}
