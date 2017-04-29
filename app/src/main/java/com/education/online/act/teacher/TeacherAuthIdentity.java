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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.upyun.UploadTask;
import com.education.online.bean.ImageInfo;
import com.education.online.bean.TeacherAuth;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */
public class TeacherAuthIdentity extends BaseFrameAct implements View.OnClickListener{

    private EditText nameEdt, idEdt;
    private ImageView imgAdd1,imgAdd2;
    private String pic1="", pic2="";
    private Dialog progressDialog;
    private String phoneTxtName = "";
    private HttpHandler mHandler;
    private ImageLoader imageLoader;
    private int idImgType=1;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                ToastUtils.displayTextShort(TeacherAuthIdentity.this, "提交成功");
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_identity_layout);

        imageLoader=ImageLoader.getInstance();
        _setHeaderTitle("身份认证");
        _setRightHomeText("提交认证  ", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameEdt.getText().toString();
                String id=idEdt.getText().toString();
                String hint="";
                if(pic1.length()==0||pic2.length()==0)
                    hint="请上传照片";
                if(id.length()==0)
                    hint="请填写身份证";
                if(name.length()==0)
                    hint="请填写姓名";
                if(hint.length()==0)
                    mHandler.updateValidate(pic1+"_1,"+pic2+"_2", name, id);
                else
                    ToastUtils.displayTextShort(TeacherAuthIdentity.this, hint);
            }
        });

        initHandler();
        initView();
    }

    private void initView() {
        nameEdt=(EditText)findViewById(R.id.nameEdt);
        idEdt=(EditText)findViewById(R.id.idEdt);
        imgAdd1=(ImageView) findViewById(R.id.imgAdd1);
        imgAdd2=(ImageView)findViewById(R.id.imgAdd2);
        imgAdd1.setOnClickListener(this);
        imgAdd2.setOnClickListener(this);
        String jsonData=getIntent().getStringExtra("jsonData");
        String name=JsonUtil.getString(jsonData, "user_name");
        if(name.length()>0) {
            nameEdt.setText(name);
            nameEdt.setEnabled(false);
        }
        idEdt.setText(JsonUtil.getString(jsonData, "id_number"));
        List<ImageInfo> imgs= JSON.parseObject(JsonUtil.getString(jsonData, "pic_info"), new TypeReference<List<ImageInfo>>(){});
        for (ImageInfo img:imgs){
            if(img.getPic_type().equals("1")) {
                pic1 = img.getPic_urls();
                imageLoader.displayImage(ImageUtil.getImageUrl(pic1), imgAdd1);
            } else if(img.getPic_type().equals("2")) {
                pic2 = img.getPic_urls();
                imageLoader.displayImage(ImageUtil.getImageUrl(pic2), imgAdd2);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view==imgAdd1)
            idImgType=1;
        if(view==imgAdd2)
            idImgType=2;
        new SelectPicDialog(TeacherAuthIdentity.this).show();
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
                    phoneTxtName = "head" + System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, TeacherAuthIdentity.this);
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
            final File file = FileUtil.getFile(phoneTxtName + ".png", TeacherAuthIdentity.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        String url = result.substring(1);
                        LogUtil.d("validate", url);
                        Toast.makeText(TeacherAuthIdentity.this,"上传成功！",Toast.LENGTH_SHORT).show();
                        if(idImgType==1){
                            pic1=url;
                            imgAdd1.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        }else if(idImgType==2){
                            pic2=url;
                            imgAdd2.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        }
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(TeacherAuthIdentity.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "validate/"+phoneTxtName + ".png");
            }
        }
    };
}
