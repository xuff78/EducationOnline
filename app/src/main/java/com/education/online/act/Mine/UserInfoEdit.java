package com.education.online.act.Mine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.upyun.UploadTask;
import com.education.online.bean.TeacherBean;
import com.education.online.bean.UserInfo;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.File;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class UserInfoEdit extends BaseFrameAct {


    String phoneTxtName="";
    private ImageView headIcon;
    private TextView nickName, userBirthday, userPhone, userName;
    private Dialog progressDialog;
    private ImageView SexFemale;
    private ImageView SexMale;
    private LinearLayout LayoutMale;
    private LinearLayout LayoutFemale;
    private DatePickerDialog datePickerDialog;
    private String avatar="";
    private ImageLoader imageloader;
    private UserInfo userinfo;
    private HttpHandler mHandler;
    private String gender="";

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getUserInfo)){
                    userinfo= JSON.parseObject(jsonData, UserInfo.class);
                    setFormData();
                }else if(method.equals(Method.Update)){
                    DialogUtil.showInfoDialog(UserInfoEdit.this, "提示", "修改成功", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_edit);

        imageloader=ImageLoader.getInstance();
        _setHeaderTitle("个人资料");
        _setRightHomeText("修改", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.update(gender, avatar, nickName.getText().toString(), userBirthday.getText().toString());
            }
        });
        initHandler();
        initView();
        String usercode= SharedPreferencesUtil.getUsercode(this);
        mHandler.getUserInfo(usercode);
    }

    private void initView() {
        headIcon= (ImageView) findViewById(R.id.userIcon);
        nickName= (TextView) findViewById(R.id.nickName);
        userBirthday= (TextView) findViewById(R.id.userBirthday);
        userPhone= (TextView) findViewById(R.id.userPhone);
        userName=(TextView) findViewById(R.id.userName);

        headIcon.setOnClickListener(listener);
        findViewById(R.id.birthdayLayout).setOnClickListener(listener);

        SexFemale = (ImageView) findViewById(R.id.SexFemale);
        SexMale = (ImageView) findViewById(R.id.SexMale);
        LayoutMale = (LinearLayout) findViewById(R.id.LayoutMale);
        LayoutFemale = (LinearLayout) findViewById(R.id.LayoutFemale);
        LayoutFemale.setOnClickListener(listener);
        LayoutMale.setOnClickListener(listener);
    }

    private void setFormData() {
        nickName.setText(userinfo.getNickname());
        userBirthday.setText(userinfo.getBirthday());
        imageloader.displayImage(ImageUtil.getImageUrl(userinfo.getAvatar()), headIcon);
        userPhone.setText(userinfo.getPhone());
        userName.setText(userinfo.getName());
        gender=userinfo.getGender();
        if(gender.equals("2")){
            gender="female";
            SexFemale.setImageResource(R.mipmap.icon_round_right);
        }else if(gender.equals("1")){
            gender="male";
            SexMale.setImageResource(R.mipmap.icon_round_right);
        }
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.userIcon:
                    new SelectPicDialog(UserInfoEdit.this).show();
                    break;
                case R.id.birthdayLayout:
                    String date=userBirthday.getText().toString();
                    if(!date.contains("-"))
                        date= ActUtil.getDate();
                    String[] dateStart = date.split("-");
                    datePickerDialog=new DatePickerDialog(UserInfoEdit.this, mDateSetListener, Integer.valueOf(dateStart[0]),
                            Integer.valueOf(dateStart[1])-1, Integer.valueOf(dateStart[2]));
                    datePickerDialog.show();
                    break;
                case R.id.LayoutFemale:
                    gender="female";
                    SexFemale.setImageResource(R.mipmap.icon_round_right);
                    SexMale.setImageResource(R.mipmap.icon_round);
                    break;
                case R.id.LayoutMale:
                    gender="male";
                    SexFemale.setImageResource(R.mipmap.icon_round);
                    SexMale.setImageResource(R.mipmap.icon_round_right);
                    break;

            }
        }
    };

    DatePickerDialog.OnDateSetListener mDateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("---> 设置后: year="+year+", month="+monthOfYear+",day="+dayOfMonth);
            String dateStr=year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            userBirthday.setText(dateStr);
        }
    };

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file= FileUtil.getFile(phoneTxtName+".png", UserInfoEdit.this);
            progressDialog.dismiss();
            if(file.exists())
                startPhotoZoom(Uri.fromFile(file));
            else
                ToastUtils.displayTextShort(UserInfoEdit.this, "找不到文件");
        }
    };

    private static final String IMAGE_UNSPECIFIED = "image/*";
    private String path="";
    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Bitmap bmp=null;
        try{
            bmp= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            path= MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "", "");
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");// 调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(Uri.parse(path), IMAGE_UNSPECIFIED);
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
                progressDialog.dismiss();
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
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, UserInfoEdit.this);
                    if(newResizeBmp!=null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }

            }.start();
        }else if(requestCode == 0x22){
            File file = FileUtil.getFile(phoneTxtName + ".png", UserInfoEdit.this);
            Bitmap photo=null;
            if(data!=null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                }
            }
            if(photo==null){
                photo = BitmapFactory.decodeFile(SelectPicDialog.getPath(this, Uri.parse(path)));
            }
            if(photo!=null) {
//                progressDialog.show();
                final Bitmap output = ImageUtil.createCircleImage(photo, Math.min(photo.getHeight(), photo.getWidth()));
                FileUtil.saveBitmap(output, phoneTxtName, UserInfoEdit.this, 100);
                headIcon.setImageBitmap(output);

                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        avatar = result.substring(1);
                        LogUtil.d("Img", avatar);
//                        imageloader.displayImage(ImageUtil.getImageUrl(avatar), headIcon);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(UserInfoEdit.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "avatar/"+phoneTxtName + ".png");
            }
        }
    }
}
