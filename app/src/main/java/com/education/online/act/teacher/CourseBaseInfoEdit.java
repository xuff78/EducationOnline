package com.education.online.act.teacher;

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
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.login.SubjectSelector;
import com.education.online.act.upyun.UploadTask;
import com.education.online.bean.AddClassBean;
import com.education.online.bean.SubjectBean;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.http.HttpHandler;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectCourseTypeDialog;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CourseBaseInfoEdit extends BaseFrameAct implements View.OnClickListener {


    String phoneTxtName = "";
    private String course="";
    private Dialog progressDialog;
    private TextView submitCourseBtn, uploadBtn, subjectTxt, priceTxt, joinNum;
    private ImageView courseImg;
    private EditText courseName, courseDesc;
    private int type = 0; // 0课件， 1视频, 2直播
    private View uploadLayout, joinNumLayout;
    private SelectPicDialog selectPicDialog;
    private ImageLoader imageloader;

    private AddClassBean addClassBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_course_edit);

        type = getIntent().getIntExtra("Type", 0);
        _setRightHomeGone();
        initView();
    }

    private void initView() {

        subjectTxt = (TextView) findViewById(R.id.subjectTxt);
        priceTxt = (TextView) findViewById(R.id.priceTxt);
        courseImg = (ImageView) findViewById(R.id.courseImg);
        courseName = (EditText) findViewById(R.id.courseName);
        courseDesc = (EditText) findViewById(R.id.courseDesc);
        joinNum = (TextView) findViewById(R.id.joinNum);
        submitCourseBtn = (TextView) findViewById(R.id.submitCourseBtn);
        submitCourseBtn.setOnClickListener(this);
        uploadBtn = (TextView) findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(this);
        uploadLayout = findViewById(R.id.uploadLayout);
        joinNumLayout = findViewById(R.id.joinNumLayout);
        joinNumLayout.setOnClickListener(this);
        findViewById(R.id.subjectLayout).setOnClickListener(this);
        findViewById(R.id.priceLayout).setOnClickListener(this);
        findViewById(R.id.courseImgLayout).setOnClickListener(this);
        addClassBean = new AddClassBean();
        selectPicDialog = new SelectPicDialog(this);


        if (type == 0) {
            uploadBtn.setText("上传课件");
            _setHeaderTitle("课件");
            addClassBean.setCourse_type("courseware");
        } else if (type == 1) {
            uploadBtn.setText("上传视频");
            _setHeaderTitle("视频课");
            addClassBean.setCourse_type("video");
            uploadBtn.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            uploadLayout.setVisibility(View.GONE);
            joinNumLayout.setVisibility(View.VISIBLE);
            submitCourseBtn.setText("下一步");
            _setHeaderTitle("开设直播课");
            addClassBean.setCourse_type("live");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.joinNumLayout:
                startActivityForResult(new Intent(CourseBaseInfoEdit.this, CourseMemberEdit.class), 0x10);
                break;
            case R.id.subjectLayout:
                startActivityForResult(new Intent(CourseBaseInfoEdit.this, SubjectSelector.class), 0x10);
                break;
            case R.id.priceLayout:
                startActivityForResult(new Intent(CourseBaseInfoEdit.this, CoursePriceEdit.class), 0x10);
                break;
            case R.id.courseImgLayout://选择图片层
                selectPicDialog.show();
                break;
            case R.id.submitCourseBtn:
                addClassBean.setIntroduction(courseDesc.getText().toString().trim());
                addClassBean.setName(subjectTxt.getText().toString());
                if (addClassBean.getName().length() == 0 || addClassBean.getIntroduction().length() == 0 || addClassBean.getCourse_type().length() == 0 || addClassBean.getSubject_id().length() == 0
                        || addClassBean.getOriginal_price().length() == 0 || addClassBean.getPrice().length() == 0 || addClassBean.getMin_follow().length() == 0
                        || addClassBean.getMax_follow().length() == 0 || addClassBean.getImg().length() == 0) {
                    Toast.makeText(CourseBaseInfoEdit.this, "请填写完整信息", Toast.LENGTH_SHORT).show();

                } else {
                    addClassBean.setName(courseName.getText().toString());
                    Intent intent = new Intent();
                    intent.setClass(CourseBaseInfoEdit.this, CourseBaseInfoEdit2.class);
                    intent.putExtra("addClassBean", addClassBean);
                    intent.putExtra("type",type);
                    startActivityForResult(intent,0x10);
                }
                break;
            case R.id.uploadBtn:
                selectPicDialog.show();
                break;
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file = FileUtil.getFile(phoneTxtName + ".png", CourseBaseInfoEdit.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        course = result.substring(1);
                        LogUtil.d("Img", course);
                       // imageloader.displayImage(ImageUtil.getImageUrl(course), courseImg);
                        Toast.makeText(CourseBaseInfoEdit.this,"图片上传成功！",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(CourseBaseInfoEdit.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "course/"+phoneTxtName + ".png");
            }
            Bitmap photo = BitmapFactory.decodeFile(file.toString());
            if (photo != null) {
                courseImg.setImageBitmap(photo);
                addClassBean.setImg("course/"+phoneTxtName + ".png");

            } else
                ToastUtils.displayTextShort(CourseBaseInfoEdit.this, "找不到文件");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x11) {
            SubjectBean subjectBean = (SubjectBean) data.getSerializableExtra(SubjectBean.Name);
           // addClassBean.setName(subjectBean.getSubject_name());
            addClassBean.setSubject_id(subjectBean.getSubject_id());
            subjectTxt.setText(subjectBean.getSubject_name());
        }else if (resultCode==0x14){

            finish();
        } else if (resultCode == 0x12) {
            priceTxt.setText("现价 " + data.getStringExtra("cp") + "  原价 " + data.getStringExtra("op"));
            addClassBean.setPrice(data.getStringExtra("cp"));
            addClassBean.setOriginal_price(data.getStringExtra("op"));
        } else if (resultCode == 0x13) {
            joinNum.setText("最少 " + data.getStringExtra("min") + "/最多 " + data.getStringExtra("max"));
            addClassBean.setMin_follow(data.getStringExtra("min"));
            addClassBean.setMax_follow(data.getStringExtra("max"));
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
                    phoneTxtName = "head" + System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, CourseBaseInfoEdit.this);
                    if (newResizeBmp != null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
    }
}
