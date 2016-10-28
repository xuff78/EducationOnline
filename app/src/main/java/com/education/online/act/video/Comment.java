package com.education.online.act.video;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.FirstPage;
import com.education.online.act.upyun.UploadTask;
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
import com.education.online.view.RatingBar;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Great Gao on 2016/10/27.
 */
public class Comment extends BaseFrameAct implements View.OnClickListener, RatingBar.OnRatingChangeListener {

    private ImageView imageview1, imageview2, imageview3, courseImage,anonymousimage;
    private TextView courseName, courseBrief, submitbtn;
    private LinearLayout anonymous;
    private EditText comment;
    private RatingBar ratingBar;
    private HttpHandler httphandler;
    private ImageLoader imageloader;
    private String phoneTxtName = "";
    private Dialog progressDialog;
    private String avatar = "";
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private static int i = 0;
    private boolean flag = false;
    private Intent intent;
    private String star = "";
    private String course_id="";
    private String is_secret = "0";//1-是 0-否
    private String evaluate_info="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycomments);
        _setHeaderTitle("评价");
        initView();
    }

    public void initView() {
        courseImage = (ImageView) findViewById(R.id.courseImg);
        courseName = (TextView) findViewById(R.id.courseName);
        courseBrief = (TextView) findViewById(R.id.courseBrief);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        imageview1.setOnClickListener(this);
        imageview2 = (ImageView) findViewById(R.id.imageview2);
        imageview2.setOnClickListener(this);
        imageview3 = (ImageView) findViewById(R.id.imageview3);
        imageview3.setOnClickListener(this);
        anonymous = (LinearLayout) findViewById(R.id.anonymous);
        anonymous.setOnClickListener(this);
        comment = (EditText) findViewById(R.id.comment);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        ratingBar.setOnRatingChangeListener(this);
        submitbtn = (TextView) findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(this);
        anonymousimage= (ImageView) findViewById(R.id.anonymousimage);
        imageViews.add(imageview1);
        imageViews.add(imageview2);
        imageViews.add(imageview3);
        intent=getIntent();
        imageloader = ImageLoader.getInstance();
        String courseImg = intent.getStringExtra("courseImg");
        imageloader.displayImage(ImageUtil.getImageUrl(courseImg),courseImage);
        String temp=intent.getStringExtra("courseName");
        courseName.setText(temp);
        temp = intent.getStringExtra("courseIntroduction");
        courseBrief.setText(temp);
        course_id=intent.getStringExtra("course_id");
        initHandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview1:
                new SelectPicDialog(Comment.this).show();
                break;
            case R.id.imageview2:
                new SelectPicDialog(Comment.this).show();
                break;
            case R.id.imageview3:
                new SelectPicDialog(Comment.this).show();
                break;
            case R.id.anonymous:
                if(flag) {
                    flag = false;
                    is_secret="0";
                    anonymousimage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_round));
                }else{
                        flag = true;
                    is_secret="1";
                anonymousimage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_round_right));}
                break;
            case R.id.submitbtn:
                evaluate_info=comment.getText().toString();
                if (star.length() == 0 || comment.getText().length() == 0)
                    Toast.makeText(Comment.this, "请输入完整的评价哦~", Toast.LENGTH_SHORT).show();
                else
               httphandler.evaluate(course_id,star,evaluate_info,is_secret);
                    break;
        }
    }

    @Override
    public void onRatingChange(float RatingCount) {
        star = Integer.toString((int)RatingCount);
    }
    private void initHandler() {
        httphandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                Toast.makeText(Comment.this,"评论成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file = FileUtil.getFile(phoneTxtName + ".png", Comment.this);
            Bitmap photo = BitmapFactory.decodeFile(file.toString());
            progressDialog.dismiss();
            if (photo != null) {
//                progressDialog.show();
                final Bitmap output = ImageUtil.createCircleImage(photo, Math.min(photo.getHeight(), photo.getWidth()));
                FileUtil.saveBitmap(output, phoneTxtName, Comment.this, 100);
                imageViews.get(i).setImageBitmap(photo);
                i++;
                if (i < 3) {
                    imageViews.get(i).setVisibility(View.VISIBLE);
                    imageViews.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_redcross1));
                }
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == SelectPicDialog.SELECT_PIC_BY_TACK_PHOTO||
                requestCode == SelectPicDialog.SELECT_PIC_BY_PICK_PHOTO)) {

            progressDialog= ProgressDialog.show(this, "", "处理中。。");
            final String picPath = SelectPicDialog.doPhoto(this, requestCode, data);
            Log.i("Upload", "最终选择的图片=" + picPath);
            if(picPath==null) {
                ToastUtils.displayTextShort(this, "获取图片失败");
                return;
            }
            final Bitmap newResizeBmp= ImageUtil.getSmallBitmap(picPath);
            if(newResizeBmp==null||newResizeBmp.isRecycled()){
                progressDialog.dismiss();
                return;
            }

            new Thread() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    phoneTxtName="head"+System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, Comment.this);
                    if(newResizeBmp!=null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }

            }.start();
        }else if(requestCode == 0x22){
            File file = FileUtil.getFile(phoneTxtName + ".png", Comment.this);
            Bitmap photo= BitmapFactory.decodeFile(file.toString());
            if(photo!=null) {
//                progressDialog.show();
                final Bitmap output = ImageUtil.createCircleImage(photo, Math.min(photo.getHeight(), photo.getWidth()));
                FileUtil.saveBitmap(output, phoneTxtName, Comment.this, 100);
                new UploadTask(new UploadTask.UploadCallBack() {//后台上传照片

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        avatar = result.substring(1);
                        LogUtil.d("Img", avatar);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(Comment.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file,"avtar/"+ phoneTxtName + ".png");
            }
        }
    }
}

