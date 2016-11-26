package com.education.online.act.Mine;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.upyun.UploadTask;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.MenuPopup;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.File;


/**
 * Created by Administrator on 2016/9/13.
 */

public class IllegalReport extends BaseFrameAct implements View.OnClickListener {


    private EditText courseName,  teacher_orgnization;
    private TextView setPhoneNum,complaintreason, submitbtn;
    private EditText complaintdetail;
    private RelativeLayout complaintReasonDrop;
    private LinearLayout teacherandorg, addpictures;
    private ImageView addpicture1, addpicture2, addpicture3;
    private MenuPopup popup;
    private String[] reportTpe = {"课程价格虚高", "老师/机构信息不实", "机构私下交易", "其他"};
    private int type = 0;
    private View searchlayout;
    private ImageLoader imageloader;
    private HttpHandler httpHandler;
    private String phoneTxtName = "";
    private SelectPicDialog selectPicDialog;
    private Dialog progressDialog;
    //参数列表
    private String user_name = "";
    private String courses_name = "";
    private String reason = "";
    private String introduction = "";
    private String url = "";
    private int whichimage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.illegalreport);
        _setHeaderTitle("违规举报");
        init();
        initHandler();
    }

    public void init() {

        selectPicDialog = new SelectPicDialog(this);
        progressDialog = new ProgressDialog(this);
        teacherandorg = (LinearLayout) findViewById(R.id.teacherandorg);
        courseName = (EditText) findViewById(R.id.courseName);
        setPhoneNum = (TextView) findViewById(R.id.setPhoneNum);
        String phone = SharedPreferencesUtil.getString(this, Constant.UserName);
        setPhoneNum.setText(phone);
        submitbtn = (TextView) findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(this);

        complaintdetail = (EditText) findViewById(R.id.complaintdetail);
        complaintReasonDrop = (RelativeLayout) findViewById(R.id.complaintReasonDrop);
        complaintreason = (TextView) findViewById(R.id.complaintreason);
        addpictures = (LinearLayout) findViewById(R.id.addpictures);
        teacher_orgnization = (EditText) findViewById(R.id.teacher_orgnization);
        searchlayout = findViewById(R.id.searchlayout);
        addpicture1 = (ImageView) findViewById(R.id.addpicture1);
        addpicture1.setOnClickListener(this);
        addpicture2 = (ImageView) findViewById(R.id.addpicture2);
        addpicture2.setOnClickListener(this);
        addpicture3 = (ImageView) findViewById(R.id.addpicture3);
        addpicture3.setOnClickListener(this);
        popup = new MenuPopup(IllegalReport.this, reportTpe, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                complaintreason.setText(reportTpe[i]);
                type = i;
                reason=String.valueOf(type);
                popup.dismiss();
            }
        });


        complaintReasonDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.showPopupWindow(searchlayout);
            }
        });
    }

    private void initHandler() {
        httpHandler = new HttpHandler(IllegalReport.this, new CallBack(IllegalReport.this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                ToastUtils.displayTextShort(IllegalReport.this, "举报成功！");
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addpicture1:
                whichimage = 0;
                selectPicDialog.show();
                break;
            case R.id.addpicture2:
                whichimage = 1;
                selectPicDialog.show();
                break;
            case R.id.addpicture3:
                whichimage = 2;
                selectPicDialog.show();
                break;
            case R.id.submitbtn:
                courses_name = courseName.getText().toString().trim();
                user_name = teacher_orgnization.getText().toString().trim();
                introduction=complaintdetail.getText().toString().trim();

                if(user_name.length()==0) {
                    ToastUtils.displayTextShort(IllegalReport.this, "请填写举报的老师或机构");
                }
                else if(reason.length()==0){
                    ToastUtils.displayTextShort(IllegalReport.this, "请选择举报的原因");
                }else{
                    if(reason.equals("3"))
                    {
                        if(introduction.length()==0)
                        {
                            ToastUtils.displayTextShort(IllegalReport.this, "请填写具体举报原因");
                        }else
                            httpHandler.complain(user_name, courses_name, reason, introduction, url);
                    }else
                        httpHandler.complain(user_name, courses_name, reason, introduction, url);
                }
                break;
        }
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
                    phoneTxtName = "compose" + System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, IllegalReport.this);
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
            File file = FileUtil.getFile(phoneTxtName + ".png", IllegalReport.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        String temp;
                        temp = result.substring(1);
                        LogUtil.d("Img", temp);
                        if (url.equals(""))
                            url = url + temp;
                        else
                            url = url + "," + temp;
                        Toast.makeText(IllegalReport.this, "图片上传成功！", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(IllegalReport.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "compose/" + phoneTxtName + ".png");
            }
            Bitmap photo = BitmapFactory.decodeFile(file.toString());
            if (photo != null) {
                //// TODO: 2016/11/26
                ImageView imageview = getCurrentImageview(whichimage);
                imageview.setImageBitmap(photo);
                imageview.setBackground(null);
            } else
                ToastUtils.displayTextShort(IllegalReport.this, "找不到文件");
        }
    };
    private ImageView getCurrentImageview(int i){
        if(i==0) {
            addpicture1.setClickable(false);
            addpicture2.setVisibility(View.VISIBLE);
            return addpicture1;
        }
            else if (i==1) {

            addpicture2.setClickable(false);
            addpicture3.setVisibility(View.VISIBLE);
            return addpicture2;
        }
        else if (i==2) {

            addpicture3.setClickable(false);
            return addpicture3;
        }
        return null;
    }
}



