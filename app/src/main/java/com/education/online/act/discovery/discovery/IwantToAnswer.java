package com.education.online.act.discovery.discovery;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.FirstPage;
import com.education.online.act.upyun.UploadTask;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.LoginInfo;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2016/9/29.
 */
public class IwantToAnswer extends BaseFrameAct implements View.OnClickListener {

    private ImageView icon,picture,answerpicture;
    private  TextView classname, myquestion, subject,time;
    private RelativeLayout addpicturelayout,submit;
    private EditText answer;
    private Intent intent;
    private  ImageLoader imageLoader;
    private  QuestionInfoBean questionInfoBean = new QuestionInfoBean();
    private SelectPicDialog selectPicDialog;
    private String phoneTxtName ="";
    private String course = "";
    private Dialog progressDialog;

    private String introduction = "";
    private String qa_type= "answer";
    private String name = "";
    private String subject_id="";
    private String question_id = "";
    private int integral = 0;


    private HttpHandler httpHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iwanttoanswer);
        _setHeaderTitle("我要回答");
        init();
        initHandler();
        name=SharedPreferencesUtil.getString(this, Constant.NickName);
    }
    private void init(){
        intent = getIntent();
  //      layoutManager = new LinearLayoutManager(this);
        questionInfoBean = (QuestionInfoBean) intent.getSerializableExtra("questionInfoBean");
        imageLoader = ImageLoader.getInstance();


        icon = (ImageView) findViewById(R.id.icon);
        if (questionInfoBean.getAvatar().length()>0)
            imageLoader.displayImage(ImageUtil.getImageUrl(questionInfoBean.getAvatar()),icon);

        picture = (ImageView) findViewById(R.id.picture);

        if(questionInfoBean.getImg().length()>0)
            imageLoader.displayImage(ImageUtil.getImageUrl(questionInfoBean.getImg()),picture);


        classname = (TextView) findViewById(R.id.classname);
        classname.setText(questionInfoBean.getName());
        myquestion = (TextView) findViewById(R.id.myquestion);
        myquestion.setText(questionInfoBean.getIntroduction());

        subject = (TextView) findViewById(R.id.subject);
        subject.setText(questionInfoBean.getSubject_name());
        time = (TextView) findViewById(R.id.createTime);
        time.setText(questionInfoBean.getCreated_at());
        addpicturelayout = (RelativeLayout) findViewById(R.id.addpicturelayout);
        addpicturelayout.setOnClickListener(this);
        answer = (EditText) findViewById(R.id.answer);
        answerpicture= (ImageView) findViewById(R.id.answerpicture);
        submit = (RelativeLayout) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        selectPicDialog = new SelectPicDialog(this);

        subject_id=questionInfoBean.getSubject_id();
        question_id = questionInfoBean.getQuestion_id();




    }

    private void initHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method == Method.askOrAnswer) {
                    setResult(0x10);
                    ToastUtils.displayTextShort(IwantToAnswer.this, "回答成功！");
                    finish();
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                if(method == Method.askOrAnswer) {
                    ToastUtils.displayTextShort(IwantToAnswer.this, "回答失败！");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && (requestCode == SelectPicDialog.SELECT_PIC_BY_TACK_PHOTO ||
                requestCode == SelectPicDialog.SELECT_PIC_BY_PICK_PHOTO)) {

            progressDialog = ProgressDialog.show(this, "", "处理中。。");
            final String picPath = SelectPicDialog.doPhoto(this, requestCode, data);
            Log.i("Upload", "最终选择的图片=" + picPath);
            if (picPath == null) {
                ToastUtils.displayTextShort(this, "获取图片失败");
                progressDialog.dismiss();
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
                    phoneTxtName = "answer" + System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, IwantToAnswer.this);
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
            File file = FileUtil.getFile(phoneTxtName + ".png", IwantToAnswer.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        course = result.substring(1);
                        LogUtil.d("Img", course);
                        // imageloader.displayImage(ImageUtil.getImageUrl(course), courseImg);
                        Toast.makeText(IwantToAnswer.this, "图片上传成功！", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(IwantToAnswer.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "question/" + phoneTxtName + ".png");
            }
            Bitmap photo = BitmapFactory.decodeFile(file.toString());
            if (photo != null) {
                answerpicture.setImageBitmap(photo);
            } else
                ToastUtils.displayTextShort(IwantToAnswer.this, "找不到文件");
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addpicturelayout:
                selectPicDialog.show();
                break;
            case R.id.submit:
                introduction = answer.getText().toString();
                if(introduction.length()>0) {
                    httpHandler.AskOrAnswer(qa_type, subject_id, name, introduction, course, String.valueOf(integral), question_id);
                }else
                {ToastUtils.displayTextShort(IwantToAnswer.this,"请给出您的回答哦~");}
                break;

        }
    }
}
