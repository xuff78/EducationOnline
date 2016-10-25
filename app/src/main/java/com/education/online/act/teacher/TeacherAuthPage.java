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
                super.doSuccess(method, jsonData);
                ToastUtils.displayTextShort(TeacherAuthPage.this, "提交成功");
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_auth);

        _setHeaderTitle("认证设置");
        _setRightHomeText("提交认证  ", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ids="";
                if(pic1.length()>0)
                    ids=ids+pic1+"_1,";
                if(pic2.length()>0)
                    ids=ids+pic2+"_3,";
                if(pic3.length()>0)
                    ids=ids+pic3+"_4,";
                if(pic4.length()>0)
                    ids=ids+pic4+"_5,";
                if(pic5.length()>0)
                    ids=ids+pic5+"_6,";
                if(ids.length()>0)
                    mHandler.updateValidate(ids.substring(0, ids.length()-1));
                else
                    ToastUtils.displayTextShort(TeacherAuthPage.this, "没有上传任何认证");
            }
        });
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
            txt.setOnClickListener(this);
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
    }

    private void setPicUrl(String url){
        switch (clickViewId){
            case R.id.idStatus:
                pic1=url;
                break;
            case R.id.teacherStatus:
                pic2=url;
                break;
            case R.id.eduStatus:
                pic3=url;
                break;
            case R.id.zyzzStatus:
                pic4=url;
                break;
            case R.id.gzdwStatus:
                pic5=url;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        clickViewId=view.getId();
        new SelectPicDialog(TeacherAuthPage.this).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == SelectPicDialog.SELECT_PIC_BY_TACK_PHOTO ||
                requestCode == SelectPicDialog.SELECT_PIC_BY_PICK_PHOTO)) {

            progressDialog = ProgressDialog.show(this, "", "处理中。。");
            final String picPath = SelectPicDialog.doPhoto(this, requestCode, data);
            Log.i("Upload", "最终选择的图片=" + picPath);
            if (picPath == null) {
                ToastUtils.displayTextShort(this, "获取图片失败");
                return;
            }
            final Bitmap newResizeBmp = ImageUtil.getSmallBitmap(picPath);
            if (newResizeBmp == null || newResizeBmp.isRecycled()) {
                progressDialog.dismiss();
                return;
            }

            new Thread() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    phoneTxtName = "head" + System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, TeacherAuthPage.this);
                    if (newResizeBmp != null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file = FileUtil.getFile(phoneTxtName + ".png", TeacherAuthPage.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        String url = result.substring(1);
                        LogUtil.d("validate", url);
                        setPicUrl(url);
                        Toast.makeText(TeacherAuthPage.this,"上传成功！",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(TeacherAuthPage.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "validate/"+phoneTxtName + ".png");
            }
        }
    };
}
