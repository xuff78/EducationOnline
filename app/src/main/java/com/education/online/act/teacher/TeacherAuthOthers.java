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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.upyun.UploadTask;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/28.
 */
public class TeacherAuthOthers  extends BaseFrameAct implements View.OnClickListener{

    private TextView hintTxt;
    private ImageView uploadImg;
    private String imgUrl="";
    private Dialog progressDialog;
    private String phoneTxtName = "";
    private HttpHandler mHandler;
    private LinearLayout imgListLayout;
    private ArrayList<String> imgList=new ArrayList<>();
    private int ResId=-1;
    private ImageLoader imageLoader;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                ToastUtils.displayTextShort(TeacherAuthOthers.this, "提交成功");
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_identity_layout);

        _setHeaderTitle("身份认证");
        _setRightHomeText("提交认证  ", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ids="";
                if(ids.length()>0)
                    mHandler.updateValidate(ids.substring(0, ids.length()-1));
                else
                    ToastUtils.displayTextShort(TeacherAuthOthers.this, "没有上传任何认证");
            }
        });
        imageLoader=ImageLoader.getInstance();
        ResId=getIntent().getIntExtra("ResId", -1);
        initHandler();
        initView();
    }

    private void initView() {
        imgListLayout=(LinearLayout)findViewById(R.id.imgListLayout);
        hintTxt=(TextView)findViewById(R.id.hintTxt);
        uploadImg=(ImageView) findViewById(R.id.uploadImg);
        uploadImg.setOnClickListener(this);

        switch (ResId){
            case R.id.teacherStatus:
                _setHeaderTitle("教师认证");
                hintTxt.setText("请提交在有效期内的教师证照片，需保证头像及文字清晰可见。");
                break;
            case R.id.eduStatus:
                _setHeaderTitle("学历认证");
                hintTxt.setText("请提交证明您学历水平的证件照片，例如：学生证、在读证明、毕业证等（需包含照片、姓名、专业、校方印章等有效信息）。");
                break;
            case R.id.zyzzStatus:
                _setHeaderTitle("专业资历认证");
                hintTxt.setText("请提交您教学能力或者专业能力的证明，例如：\n•职称证书（建议中小学老师上传）\n•考级证书（建议艺术老师上传）");
                break;
            case R.id.gzdwStatus:
                _setHeaderTitle("工作资历");
                hintTxt.setText("请提交您工作的学校、机构等单位的证明，例如：\n•劳动合同（包含姓名、单位印章等信息）\n•工作证（包含照片、姓名、单位名称等信息）");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        new SelectPicDialog(TeacherAuthOthers.this).show();
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
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, TeacherAuthOthers.this);
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
            final File file = FileUtil.getFile(phoneTxtName + ".png", TeacherAuthOthers.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        addImage(result);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(TeacherAuthOthers.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "validate/"+phoneTxtName + ".png");
            }
        }
    };

    private void addImage(String result) {
        final String imgUrl = result.substring(1);
        LogUtil.d("validate", imgUrl);
        imgList.add(imgUrl);
        Toast.makeText(TeacherAuthOthers.this,"上传成功！",Toast.LENGTH_SHORT);
        final View v= LayoutInflater.from(this).inflate(R.layout.addimg_item_layout, null);
        ImageView uploadImg= (ImageView) v.findViewById(R.id.uploadImg);
        imageLoader.displayImage(ImageUtil.getImageUrl(imgUrl), uploadImg);
        v.findViewById(R.id.delBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgListLayout.removeView(v);
                imgList.remove(imgUrl);
            }
        });
        imgListLayout.addView(v);
    }
}
