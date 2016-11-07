package com.education.online.act.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.education.online.act.FirstPage;
import com.education.online.act.MainPage;
import com.education.online.act.upyun.UploadTask;
import com.education.online.bean.SubjectBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SHA;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.education.online.view.SelectWeekdayDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CompleteDataPage extends BaseFrameAct {

    private EditText RealName;
    private ImageView headIcon;
    private TextView Subject;
    private Dialog progressDialog;
    private ImageView SexFemale;
    private ImageView SexMale;
    private LinearLayout LayoutMale;
    private LinearLayout LayoutFemale;
    private LinearLayout setsubject;
    private Intent intent;
    private HttpHandler httphandler;
    private String phoneTxtName="";
    private String name="";
    private String gender="female";
    private String avatar="";
    private String nickname = "";
    private String subject_id ="";//temple value needmiodified
    private String password;
    private  String phone;
    private String identity;
    private ImageLoader imageloader;
    private View view2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullfilldata_page);
        imageloader=ImageLoader.getInstance();
        _setLeftBackGone();
        initView();
    }

    private void initView() {
        headIcon= (ImageView) findViewById(R.id.headIcon);
        Subject= (TextView) findViewById(R.id.Subject);
        headIcon.setOnClickListener(listener);
        Subject.setOnClickListener(listener);
        findViewById(R.id.BackToHone).setOnClickListener(listener);
        view2 = findViewById(R.id.view2);

        setsubject = (LinearLayout) findViewById(R.id.setsubject);
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
        password = intent.getStringExtra("password");
        phone = intent.getStringExtra("phone");
        identity = intent.getStringExtra("identity");
        if(identity.equals("student"))
        {
            setsubject.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
        }
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
                    gender="female";
                    break;
                case R.id.LayoutMale:
                    SexFemale.setImageResource(R.mipmap.icon_round);
                    SexMale.setImageResource(R.mipmap.icon_round_right);
                    gender = "male";
                    break;
                case R.id.BackToHone:
                    if (identity.equals("teacher") && subject_id.length()==0 ) {
                        Toast.makeText(CompleteDataPage.this, "请选择教学科目", Toast.LENGTH_SHORT).show();
                    }else {
                        name = RealName.getText().toString();
                        String SHApassword="";
                        try {
                            SHApassword = SHA.getSHA(password);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        httphandler.regist(phone,SHApassword,identity,name,gender,avatar,subject_id);

                    }



                   // httphandler.update(name,gender,avatar);
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
                Toast.makeText(CompleteDataPage.this,"注册成功！",Toast.LENGTH_SHORT);

                if(method.equals(Method.Regist)) {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String sessionid = jsonObject.getString("sessionid");
                    String user_identity = JsonUtil.getString(jsonData, "user_identity");
                    SharedPreferencesUtil.setString(CompleteDataPage.this, Constant.UserIdentity, user_identity);
                    SharedPreferencesUtil.setString(CompleteDataPage.this, Constant.UserInfo, jsonData);
                    SharedPreferencesUtil.setSessionid(CompleteDataPage.this, sessionid);}
                    Intent intent = new Intent(CompleteDataPage.this, FirstPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

                new UploadTask(new UploadTask.UploadCallBack() {//后台上传照片

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        avatar = result.substring(1);
                        LogUtil.d("Img", avatar);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(CompleteDataPage.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file,"avatar/"+ phoneTxtName + ".png");
            }
        }else if(requestCode == 0x33&&resultCode==0x11){
            SubjectBean subject= (SubjectBean) data.getSerializableExtra(SubjectBean.Name);
            subject_id=subject.getSubject_id();
            Subject.setText(subject.getSubject_name());
        }
    }

}
