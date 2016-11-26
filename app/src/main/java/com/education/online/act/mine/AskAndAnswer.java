package com.education.online.act.Mine;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.auxililary.TextMoveLayout;
import com.education.online.act.login.SubjectSelector;
import com.education.online.act.upyun.UploadTask;
import com.education.online.bean.IntegralInfo;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.SubjectBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.OpenfileUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectCourseTypeDialog;
import com.education.online.view.SelectPicDialog;

import org.json.JSONException;

import java.io.File;

/**
 * Created by Administrator on 2016/9/17.
 */

public class AskAndAnswer extends BaseFrameAct implements View.OnClickListener {
///marker
    String phoneTxtName = "";
    private String course = "";

    private String coursename="";
    private String introduction = "";
    private String qa_type= "question";
    private String img="";
    private String name = "";
    private String subject_id="";
    private String question_id = "";
    private int integral = 0;
    private Dialog progressDialog;
    private TextView subject, submitbtn,text,minintegral,maxintegral;
    private RelativeLayout selectsubjectlayout;
    private SeekBar seekbar;
    private EditText enterquestion;
    private ImageView questionImage;
    private LinearLayout addpicture;
    private HttpHandler httpHandler;
    private SelectPicDialog selectPicDialog;
    private TextMoveLayout textMoveLayout;
    private ViewGroup.LayoutParams layoutParams;
    private float moveStep = 0;
    private IntegralInfo integralInfo = new IntegralInfo();
    private int width;
    private  int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_and_answer);
        _setHeaderTitle("问答");
        _setRightHomeTextColor(getResources().getColor(R.color.dark_orange));
        _setRightHomeText("提交", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                introduction = enterquestion.getText().toString().trim();

                if (coursename.length() == 0 || introduction.length() == 0) {
                    ToastUtils.displayTextShort(AskAndAnswer.this, "请填写完整提问信息");

                } else {
                    httpHandler.AskOrAnswer(qa_type,subject_id,name,introduction,img,String.valueOf(integral),question_id);
                }
            }
        });
        initiHandler();
        initView();
       httpHandler.getIntegral();

    }

    public void initiHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonMessage) throws JSONException {
                if(method== Method.askOrAnswer) {
                    ToastUtils.displayTextShort(AskAndAnswer.this, "提交成功！");//////////////为什么不变蓝
                    setResult(0x10);
                    finish();
                } else if (method.equals(Method.getIntegral)){
                    integralInfo = JsonUtil.getIntegral(jsonMessage);
                    int residual_integral =Integer.parseInt(integralInfo.getIntegral()) ;
                    seekbar.setMax(residual_integral);
                    seekbar.setEnabled(true);
                    seekbar.setProgress(0);
                    text = new TextView(AskAndAnswer.this);
                    text.setTextColor(getResources().getColor(R.color.normal_blue));
                    text.setTextSize(12);
                    height = textMoveLayout.getHeight();
                     width = textMoveLayout.getWidth();
                    layoutParams = new ViewGroup.LayoutParams(width, height);
                    textMoveLayout.addView(text,layoutParams);
                    text.layout(35,0,width, height);
                    text.setText("0");
                    maxintegral.setText(integralInfo.getIntegral());
                    moveStep = (float) (((float) width / (float) residual_integral) * 0.77);
                    seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            integral = progress;
                            text.setText(String.valueOf(progress));
                            text.layout(35+(int) (progress*moveStep),0,width,height);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                }
            }
        });
    }

    void initView() {
        selectPicDialog = new SelectPicDialog(this);
        selectsubjectlayout = (RelativeLayout) findViewById(R.id.selectsubjectlayout);
        selectsubjectlayout.setOnClickListener(this);
        subject = (TextView) findViewById(R.id.subject);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        textMoveLayout = (TextMoveLayout) findViewById(R.id.textmovelayout);
        maxintegral = (TextView) findViewById(R.id.maxintegral);
        enterquestion = (EditText) findViewById(R.id.enterquestion);
        addpicture = (LinearLayout) findViewById(R.id.addpicture);
        addpicture.setOnClickListener(this);
        questionImage = (ImageView) findViewById(R.id.questionImage);


       // int residual_integral =Integer.parseInt(integralInfo.getIntegral()) ;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.selectsubjectlayout:
                startActivityForResult(new Intent(this, SubjectSelector.class), 0x10);
                break;
            case R.id.addpicture:
                selectPicDialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x11) {
            SubjectBean subjectBean = (SubjectBean) data.getSerializableExtra(SubjectBean.Name);
            // addClassBean.setName(subjectBean.getSubject_name());
            subject.setText(subjectBean.getSubject_name());
            coursename = subjectBean.getSubject_name();
            subject_id = subjectBean.getSubject_id();
        } else if (resultCode == RESULT_OK && (requestCode == SelectPicDialog.SELECT_PIC_BY_TACK_PHOTO ||
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
                    phoneTxtName = "question" + System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, AskAndAnswer.this);
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
            File file = FileUtil.getFile(phoneTxtName + ".png", AskAndAnswer.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                         img= result.substring(1);
                        LogUtil.d("Img", img);
                        // imageloader.displayImage(ImageUtil.getImageUrl(course), courseImg);
                        Toast.makeText(AskAndAnswer.this, "图片上传成功！", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(AskAndAnswer.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "question/" + phoneTxtName + ".png");
            }
            Bitmap photo = BitmapFactory.decodeFile(file.toString());
            if (photo != null) {
                questionImage.setImageBitmap(photo);
            } else
                ToastUtils.displayTextShort(AskAndAnswer.this, "找不到文件");
        }
    };
}

