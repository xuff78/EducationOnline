package com.education.online.act.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.MainPage;
import com.education.online.act.upyun.UploadTask;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.education.online.view.SelectWeekdayDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.File;

public class CompleteDataPage extends BaseFrameAct {

    private EditText RealName;
    private ImageView headIcon;
    private TextView Subject;
    private Dialog progressDialog;
    private ImageView SexFemale;
    private ImageView SexMale;
    private LinearLayout LayoutMale;
    private LinearLayout LayoutFemale;
    private Intent intent;
    private HttpHandler httphandler;
    private String phone;
    private String password;
    private String identity;
    private String username;
    private String sexual;
    private String phoneTxtName="";
    private String sessionid;
    private String name="";
    private String gender="male";
    private String avatar="";
    private ImageLoader imageloader;
    private String uploadImgUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullfilldata_page);
        imageloader=ImageLoader.getInstance();
        initView();
    }

    private void initView() {
        headIcon= (ImageView) findViewById(R.id.headIcon);
        Subject= (TextView) findViewById(R.id.Subject);
        headIcon.setOnClickListener(listener);
        Subject.setOnClickListener(listener);
        findViewById(R.id.BackToHone).setOnClickListener(listener);

        SexFemale = (ImageView) findViewById(R.id.SexFemale);
        SexMale = (ImageView) findViewById(R.id.SexMale);
        SexFemale.setImageResource(R.mipmap.icon_round_right);
        SexMale.setImageResource(R.mipmap.icon_round);
        LayoutMale = (LinearLayout) findViewById(R.id.LayoutMale);
        LayoutFemale = (LinearLayout) findViewById(R.id.LayoutFemale);
        RealName= (EditText) findViewById(R.id.RealName);
        LayoutFemale.setOnClickListener(listener);
        LayoutMale.setOnClickListener(listener);
        intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        initHandler();
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.headIcon:
                    new SelectPicDialog(CompleteDataPage.this).show();
                    break;
                case R.id.Subject:
                    startActivityForResult(new Intent(CompleteDataPage.this, SubjectSelector.class), 0x33);
                    break;
                case R.id.LayoutFemale:
                    SexFemale.setImageResource(R.mipmap.icon_round_right);
                    SexMale.setImageResource(R.mipmap.icon_round);
                    gender="male";
                    break;
                case R.id.LayoutMale:
                    SexFemale.setImageResource(R.mipmap.icon_round);
                    SexMale.setImageResource(R.mipmap.icon_round_right);
                    gender = "female";
                    break;
                case R.id.BackToHone:
                    name = RealName.getText().toString();
                    httphandler.update(sessionid,name,gender,avatar);
                    //httphandler.regist(phone, password,identity );
                    break;

            }
        }
    };

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file= FileUtil.getFile(phoneTxtName+".png", CompleteDataPage.this);
            progressDialog.dismiss();
            if(file.exists())
                startPhotoZoom(Uri.fromFile(file));
        }
    };

    private void initHandler() {
        httphandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
//
                Toast.makeText(CompleteDataPage.this,"updatesuccess",Toast.LENGTH_SHORT);
                intent.setClass(CompleteDataPage.this,MainPage.class);
                startActivity(intent);

            }
        });
    }


    private static final String IMAGE_UNSPECIFIED = "image/*";

    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Bitmap bmp=null;
        String str="";
        try{
            bmp= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            str= MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "", "");
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");// 调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(Uri.parse(str), IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");// 进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 0x22);
    }

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
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, CompleteDataPage.this);
                    if(newResizeBmp!=null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }

            }.start();
        }else if(requestCode == 0x22){
            File file = FileUtil.getFile(phoneTxtName + ".png", CompleteDataPage.this);
            Bitmap photo= BitmapFactory.decodeFile(file.toString());
            if(photo!=null) {
//                progressDialog.show();
                final Bitmap output = ImageUtil.createCircleImage(photo, Math.min(photo.getHeight(), photo.getWidth()));
                FileUtil.saveBitmap(output, phoneTxtName, CompleteDataPage.this, 100);
                headIcon.setImageBitmap(output);

                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        uploadImgUrl = UploadTask.UPLOAD_URL + result;
                        LogUtil.d("Img", uploadImgUrl);
                        imageloader.displayImage(uploadImgUrl, headIcon);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(CompleteDataPage.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, phoneTxtName + ".png");
            }
        }
    }


}
